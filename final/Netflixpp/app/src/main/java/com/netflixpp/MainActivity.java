package com.netflixpp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

        Log.d("Situation", "Hereee ");
        setMovies();

    }
    private void setUpRecyclerView(){
        setContentView(R.layout.moviecatalogue);

        RecyclerView recyclerView = findViewById(R.id.movies_rv);

        MovieCatalogue adapter = new MovieCatalogue(this,this.movies);
        // Set the click listener for the adapter
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void setMovies(){
        OkHttpClient client = new OkHttpClient();
    
        Request request = new Request.Builder()
            .url("http://192.168.0.173:8081/movie/resources")
            .build();
    
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("ConnectionStatus","failure");
                e.printStackTrace();
            }
    
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String responseData = response.body().string();
                    String[] movieData = responseData.split("\n");

                    for (String data : movieData) {
                        String[] attributes = data.split("; ");
                        if (attributes.length >= 10) {
                            String id = attributes[0];
                            String name = attributes[1];
                            String duration = attributes[2];
                            String genre = attributes[3];
                            String year = attributes[4];
                            String description = attributes[5];
                            String thumbnail = attributes[6];
                            String linkLow = attributes[7];
                            String linkHigh = attributes[8];
                            String uploadedBy = attributes[9];

                            Movie movie = new Movie(id, name, duration, genre, year, description, thumbnail, linkLow, linkHigh, uploadedBy);
                            movies.add(movie);
                            Log.d("MovieDetails", "Added movie: " + movie.getTitlemovie());
                        }
                        else {
                        // Log insufficient data for debugging
                        Log.e("MovieDetails", "Insufficient attributes for a movie: " + data);
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setUpRecyclerView();
                        }
                    });
                }
                else {
                    // Log unsuccessful response for debugging
                    Log.e("MovieDetails", "Response not successful: " + response.code());
                }
            }
        });
    }

    @Override
    public void onItemClick(Movie movie) {
        // Handle item click, start new activity and pass Movie object
        Intent intent = new Intent(this, VideoPlayerActivity.class);
        intent.putExtra("MOVIE_OBJECT", movie); // Pass the selected Movie object
        startActivity(intent);
    }
}