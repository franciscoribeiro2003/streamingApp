package com.netflixpp;

import java.io.Serializable;

public class Movie implements Serializable {
    String id,titlemovie, duration, genero, description, linklow, linkhigh, image, year,uploadby;
    //Bitmap image;


    public Movie(String id,String titlemovie, String duration, String genero,String year, String description, String image,String linkl,String linkh, String uploadby) {
        this.id = id;
        this.titlemovie = titlemovie;
        this.duration = duration;
        this.genero = genero;
        this.description = description;
        this.image = image;
        this.linklow=linkl;
        this.linkhigh=linkh;
        this.year = year;
        this.uploadby = uploadby;
        // select id,name,duration,genre,year,Descricao,Thumbnail,linklow,linkhigh,uploadedBy from MOVIE
    }

    public String getTitlemovie() {
        return titlemovie;
    }

    public String getDuration() {
        return duration;
    }

    public String getGenero() {
        return genero;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getLinklow() {return linklow;}

    public String getLinkhigh() {return linkhigh;}
}
