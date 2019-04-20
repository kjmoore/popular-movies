package com.kieranjohnmoore.popularmovies.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.kieranjohnmoore.popularmovies.R;
import com.kieranjohnmoore.popularmovies.databinding.ActivityReviewsBinding;
import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;
import com.kieranjohnmoore.popularmovies.viewmodel.ReviewsViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

public class ReviewsActivity extends AppCompatActivity {
    private static final String TAG = ReviewsActivity.class.getSimpleName();

    private final MovieListAdapter movieListAdapter = new MovieListAdapter();
    private ActivityReviewsBinding viewBinding;
    private List<Movie> favouriteMovies;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reviews, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_reviews);

        viewBinding.reviewsList.setVisibility(View.INVISIBLE);

        final GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        viewBinding.reviewsList.setLayoutManager(layoutManager);
        viewBinding.reviewsList.setAdapter(movieListAdapter);

        final ReviewsViewModel viewModel = ViewModelProviders.of(this).get(ReviewsViewModel.class);
//        viewModel.getMovies().observe(this, this::onMoviesDownloaded);
//        viewModel.getFavouriteMovies().observe(this, this::onFavouriteMoviesChanged);

        showProgressBar();
    }

    private void showProgressBar() {
        Log.d(TAG, "Showing the progress bar, loading data");
        viewBinding.reviewsList.setVisibility(View.INVISIBLE);
        viewBinding.noData.setVisibility(View.INVISIBLE);
        viewBinding.progressBar.setVisibility(View.VISIBLE);
    }

    private void showMovies(List<Movie> movies) {
        if (movies != null) {
//            movieListAdapter.updateMovies(movies);

            viewBinding.progressBar.setVisibility(View.INVISIBLE);
            if (movies.size() > 0) {
                viewBinding.reviewsList.setVisibility(View.VISIBLE);
            } else {
                viewBinding.noData.setVisibility(View.VISIBLE);
            }
        }
    }
}
