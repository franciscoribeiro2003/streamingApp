package org.netflixpp;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import static org.netflixpp.ConnectionDB.*;

@Path("/movies")
public class AccessMoviesDB {

    ObjectMapper map = new ObjectMapper();

    @Path("/link")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getLink() {


        openDB();
        String movies = getMovies();
        closeDB();
        return movies;
    }
}
