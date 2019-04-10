package com.kieranjohnmoore.popularmovies;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kieranjohnmoore.popularmovies.moviedb.MovieDBApi;
import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {
    private static final String TAG = MovieListAdapter.class.getSimpleName();

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
        movieListViewHolder.setBackground(movie.getPosterPath());
        movieListViewHolder.setMovieName(movie.getTitle());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    void updateMovies(List<Movie> movies) {
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
            backgroundView.setVisibility(View.INVISIBLE);
            final Picasso.Builder picassoBuilder = new Picasso.Builder(backgroundView.getContext());

            picassoBuilder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    Log.e(TAG, uri.toString(), exception);
                    backgroundView.setVisibility(View.INVISIBLE);
                }
            });

            picassoBuilder.build()
                .load(MovieDBApi.POSTER_URL + url)
                .fit()
                .centerCrop()
                .into(backgroundView, new Callback() {
                    @Override
                    public void onSuccess() {
                        backgroundView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        Log.e(TAG, "Could not load image");
                    }
                });
        }

        void setMovieName(@NonNull final String movieName) {
            movieNameView.setText(movieName);
        }
    }
}
