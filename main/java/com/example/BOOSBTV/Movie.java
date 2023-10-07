package com.example.BOOSBTV;

import android.graphics.Bitmap;

public class Movie {
    String titlemovie, duration, genero, description;
    Bitmap image;


    public Movie(String titlemovie, String duration, String genero, String description, Bitmap image) {
        this.titlemovie = titlemovie;
        this.duration = duration;
        this.genero = genero;
        this.description = description;
        this.image = image;
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

    public Bitmap getImage() {
        return image;
    }
}
