package com.kieranjohnmoore.popularmovies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;

import java.util.Collections;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {

    private List<Movie> movies = Collections.emptyList();

    @NonNull
    @Override
    public MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View movieView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_item, viewGroup, false);
        return new MovieListViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListViewHolder movieListViewHolder, int i) {
        final Movie movie = movies.get(i);
        movieListViewHolder.setBackground(movie.getBackdropPath());
        movieListViewHolder.setMovieName(movie.getTitle());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void updateMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    class MovieListViewHolder extends RecyclerView.ViewHolder {
        private final ImageView backgroundView;
        private final TextView movieNameView;

        MovieListViewHolder(@NonNull View itemView) {
            super(itemView);
            backgroundView = itemView.findViewById(R.id.background_poster);
            movieNameView = itemView.findViewById(R.id.movie_text);
        }

        void setBackground(@NonNull final String url) {
        }

        void setMovieName(@NonNull final String movieName) {
            movieNameView.setText(movieName);
        }
    }
}
