package org.netflixpp;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.sql.*;

@Path("/DB")
public class ConnectionDB {

    protected static Connection connection;
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String DB() {
        openDB();
        String movies = getMovies();
        closeDB();
        return movies;
    }

    public static void openDB() {
        System.out.println("Opening");
        String user = "rick";
        String password = "morty";
        String url = "jdbc:mysql://localhost:3306/netflixpp?serverTimezone=UTC";
        String driver = "com.mysql.cj.jdbc.Driver";

        try { // try import Mysql Driver
            Class.forName(driver);
        } catch (Exception ex) {
            System.out.println("Error  " +  ex);
        }

        try { // try to establish the connection to mariadb
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }

        try { // check of the connection is valid
            System.out.println("Connection: " + connection.isValid(5));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void closeDB() {
        try {
            connection.close();
            System.out.println("Connection: " + connection.isValid(5));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getMovies() {
        try {
            PreparedStatement stm = connection.prepareStatement(
                    "select name from movies"
                    );
            ResultSet rs = stm.executeQuery();
            StringBuilder movies = new StringBuilder("MOVIES:\n");
            while (rs.next()) {
                movies.append(rs.getString("name")).append("\n");
                System.out.println(rs.getString("name"));
            }
            return movies.toString();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
