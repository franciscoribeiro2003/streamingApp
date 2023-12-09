package org.netflixpp;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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




    //Normal User
    //Post ..../movie/search/
    //Get .../movie





    //Admin
    //Post /movie/upload
    //sem prioridade
    //Post /movie/delete/
    //Post /user/delete
}
