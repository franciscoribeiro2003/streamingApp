package com.netflixpp;

import java.io.Serializable;

public class Movie implements Serializable {
    String titlemovie, duration, genero, description, linklow, linkhigh, image;
    //Bitmap image;


    public Movie(String titlemovie, String duration, String genero, String description, String image,String linkl,String linkh) {
        this.titlemovie = titlemovie;
        this.duration = duration;
        this.genero = genero;
        this.description = description;
        this.image = image;
        this.linklow=linkl;
        this.linkhigh=linkh;
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
