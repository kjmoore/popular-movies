package com.kieranjohnmoore.popularmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.kieranjohnmoore.popularmovies.R;
import com.kieranjohnmoore.popularmovies.databinding.ActivityReviewsBinding;
import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;
import com.kieranjohnmoore.popularmovies.moviedb.model.Review;
import com.kieranjohnmoore.popularmovies.viewmodel.ReviewsViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

public class ReviewsActivity extends AppCompatActivity {
    private static final String TAG = ReviewsActivity.class.getSimpleName();

    private final ReviewsListAdapter adapter = new ReviewsListAdapter();
    private ActivityReviewsBinding viewBinding;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reviews, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        final Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
            return;
        }

        final Movie movie = intent.getParcelableExtra(MainActivity.MOVIE);

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_reviews);

        viewBinding.reviewsList.setVisibility(View.INVISIBLE);

        final GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        viewBinding.reviewsList.setLayoutManager(layoutManager);
        viewBinding.reviewsList.setAdapter(adapter);

        final ReviewsViewModel viewModel = ViewModelProviders.of(this).get(ReviewsViewModel.class);
        viewModel.getReviews().observe(this, this::showReviews);
        viewModel.setMovieIdAndDownload(movie.id);

        showProgressBar();
    }

    private void showProgressBar() {
        Log.d(TAG, "Showing the progress bar, loading data");
        viewBinding.reviewsList.setVisibility(View.INVISIBLE);
        viewBinding.noData.setVisibility(View.INVISIBLE);
        viewBinding.progressBar.setVisibility(View.VISIBLE);
    }

    private void showReviews(List<Review> reviews) {
        if (reviews != null) {
            adapter.setReviews(reviews);

            viewBinding.progressBar.setVisibility(View.INVISIBLE);
            if (reviews.size() > 0) {
                viewBinding.reviewsList.setVisibility(View.VISIBLE);
            } else {
                viewBinding.noData.setVisibility(View.VISIBLE);
            }
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
