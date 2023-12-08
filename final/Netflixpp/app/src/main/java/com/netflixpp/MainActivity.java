package com.netflixpp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieCatalogue.OnItemClickListener {

    ArrayList<Movie> movies= new ArrayList<>();

    Bitmap bitmap;
    String imagePath;


    //int[] movie_images ={androidx.constraintlayout.widget.R.drawable.imgg.png };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moviecatalogue);

        RecyclerView recyclerView = findViewById(R.id.movies_rv);

        //bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.imgg);
        imagePath = "android.resource://" + getPackageName() + "/" + R.mipmap.imgg;

        setMovies();

        MovieCatalogue adapter = new MovieCatalogue(this,movies);

        // Set the click listener for the adapter
        adapter.setOnItemClickListener(this);

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
                    imagePath,
                    "https://archive.org/download/PopeyeAliBaba/PopeyeAliBaba_512kb.mp4",
                    "https://archive.org/download/PopeyeAliBaba/PopeyeAliBaba_512kb.mp4"));
        }
    }

    @Override
    public void onItemClick(Movie movie) {
        // Handle item click, start new activity and pass Movie object
        Intent intent = new Intent(this, VideoPlayerActivity.class);
        intent.putExtra("MOVIE_OBJECT", movie); // Pass the selected Movie object
        startActivity(intent);
    }
}