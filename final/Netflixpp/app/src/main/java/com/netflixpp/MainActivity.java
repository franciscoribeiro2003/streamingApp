package com.netflixpp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Movie> movies= new ArrayList<>();

    Bitmap bitmap;


    //int[] movie_images ={androidx.constraintlayout.widget.R.drawable.imgg.png };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moviecatalogue);

        RecyclerView recyclerView = findViewById(R.id.movies_rv);

        bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.imgg);
        setMovies();

        MovieCatalogue adapter = new MovieCatalogue(this,movies);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void setMovies(){
        String[] movienames;
        String[] durations;
        String[] generos;

        for (int i=0; i<20; i++){
            movies.add(new Movie("Barry Seal, a normal pilot",
                    "120 min",
                    "Action",
                    "Its a movie about how to build your drug empire, inspired on a real life story of Barry Seal, a pilot that transport by air the major productions of Colombia cocaine during the 80's.",
                    bitmap));
        }
    }
}