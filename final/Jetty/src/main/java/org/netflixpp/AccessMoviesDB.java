package org.netflixpp;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
//import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
//import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.*;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.netflixpp.ConnectionDB.*;

@Path("/movie")
public class AccessMoviesDB {

    ObjectMapper map = new ObjectMapper();

    @Path("/resources")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getLink() {
        openDB();
        String movies = getMovies();
        closeDB();
        return movies;
    }



    //Post ..../movie/search/







    //sem prioridade
    //Post /movie/delete/
    //Post /user/delete
}
