package com.kieranjohnmoore.popularmovies;

import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

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
        movieListViewHolder.setMovie(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    void updateMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    class MovieListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.background_poster)
        ImageView backgroundView;
        @BindView(R.id.movie_text)
        TextView movieNameView;

        private Movie movie;

        MovieListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        private void setMovie(@NonNull final Movie movie) {
            this.movie = movie;

            movieNameView.setText(movie.getTitle());
            setBackground(movie.getPosterPath());
        }

        private void setBackground(final String url) {
            if (url == null || url.isEmpty()) {
                Log.w(TAG, "The image URL was empty, not setting the background");
                return;
            }
            backgroundView.setVisibility(View.INVISIBLE);
            final Picasso.Builder picassoBuilder = new Picasso.Builder(backgroundView.getContext());

            picassoBuilder.listener((picasso, uri, exception) -> {
                Log.e(TAG, uri.toString(), exception);
                backgroundView.setVisibility(View.INVISIBLE);
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
                        // The background will remain invisible so will show the text title behind
                        Log.e(TAG, "Could not load image");
                    }
                });
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "Clicked: " + movies.get(getAdapterPosition()).getTitle());
            final Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.MOVIE, movie);
            v.getContext().startActivity(intent);
        }
    }
}
