package com.netflixpp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieCatalogue extends RecyclerView.Adapter<MovieCatalogue.MyViewHolder> {


    Context context;
    ArrayList<Movie> movies;

    public MovieCatalogue(Context context, ArrayList<Movie> movies){
        this.context=context;
        this.movies=movies;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.catalogue_single_movie, parent, false);
        return new MovieCatalogue.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCatalogue.MyViewHolder holder, int position) {
        holder.title.setText(movies.get(position).getTitlemovie());
        holder.duration.setText(movies.get(position).getDuration());
        holder.genre.setText(movies.get(position).getGenero());
        //holder.description.setText(movies.get(position).getDescription());
        holder.imageview.setImageBitmap(movies.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageview;
        TextView title, duration, genre;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageview=itemView.findViewById(R.id.thumb);
            title=itemView.findViewById(R.id.titlemovie);
            duration=itemView.findViewById(R.id.duration);
            genre=itemView.findViewById(R.id.genero);

        }
    }
}
