package org.netflixpp;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

import static org.netflixpp.ConnectionDB.*;

@Path("/user")
public class AccessUser{
	ObjectMapper map = new ObjectMapper();

    //Post .../user/login
	@Path("/login")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getLink(String credentialsJson) {
        openDB();
        String userDetails = getUser(credentialsJson);
        closeDB();
        return userDetails;
    }

    private String getUser(String credentialsJson) {
        try {
            // Parse JSON manually since it's constructed as a string
            String[] parts = credentialsJson.split(",");
            String username = parts[0].split(":")[1].replaceAll("\"", "").trim();
            String password = parts[1].split(":")[1].replaceAll("\"", "").trim();

            // Query database to check user existence and retrieve details
            String query = "SELECT * FROM USER WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            // Check if the user exists
            if (resultSet.next()) {
                // User found, construct JSON response with user parameters
                String userJson = "{\"id\": \"" + resultSet.getString("id") + "\", "
                        + "\"username\": \"" + resultSet.getString("username") + "\", "
                        + "\"fullname\": \"" + resultSet.getString("fullname") + "\", "+
                        "\"isCreator\": \"" + resultSet.getString("isCreator") + "\"}";
                return userJson;
            } else {
                // User not found, return an empty JSON object or an error message
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Return an error message as JSON in case of exception
            return null;
        }
    }



    /*
    
    String jsonfile=toJson(user,password);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.Companion.create(jsonfile,JSON);

    
		if response.code()==200 go to next activity

        return "{\n" + "\"username\"" + ":" + "\"" +  username + "\"" +  ",\n" +
                "\"password\"" +":"+ "\"" + password + "\"\n" + "}";
    */


    //Post /user/delete


    //Post .../user/create

}
