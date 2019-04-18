package com.kieranjohnmoore.popularmovies;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kieranjohnmoore.popularmovies.databinding.MovieItemBinding;
import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder> {
    private static final String TAG = MovieListAdapter.class.getSimpleName();

    private List<Movie> movies = Collections.emptyList();

    @NonNull
    @Override
    public MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final MovieItemBinding itemBinding =
                MovieItemBinding.inflate(layoutInflater, viewGroup, false);
        return new MovieListViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListViewHolder movieListViewHolder, int i) {
        final Movie movie = movies.get(i);
        movieListViewHolder.bind(movie);
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
        private Movie movie;
        private MovieItemBinding binding;

        MovieListViewHolder(MovieItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        private void bind(@NonNull final Movie movie) {
            this.movie = movie;
            binding.setMovie(movie);
            binding.executePendingBindings();
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
