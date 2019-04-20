package com.kieranjohnmoore.popularmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.kieranjohnmoore.popularmovies.R;
import com.kieranjohnmoore.popularmovies.database.AppDatabase;
import com.kieranjohnmoore.popularmovies.databinding.ActivityDetailBinding;
import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;
import com.kieranjohnmoore.popularmovies.moviedb.model.Trailer;
import com.kieranjohnmoore.popularmovies.viewmodel.DetailViewModel;
import com.kieranjohnmoore.popularmovies.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    public static final String MOVIE = "movie_details";

    private final AppDatabase database = AppDatabase.getInstance(this.getApplication());

    private Movie movie;
    ActivityDetailBinding viewDataBinding;
    private boolean isFav = false;
    final TrailersListAdapter trailersListAdapter = new TrailersListAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
            return;
        }

        movie = intent.getParcelableExtra(MOVIE);

        Log.d(TAG, "Detail started with: " + movie.toString());

        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        viewDataBinding.setMovie(movie);

        AppDatabase.getExecutor().submit(()-> {
            Log.d(TAG, "Checking if in DB: " + movie.id);
            final Movie test =  AppDatabase.getInstance(this).moviesDao().getMovie(movie.id);
            runOnUiThread(() -> {
                setIsFav(test != null);
            });
        });

        final GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        viewDataBinding.trailersList.setLayoutManager(layoutManager);
        viewDataBinding.trailersList.setAdapter(trailersListAdapter);

        final DetailViewModel viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        viewModel.getTrailers().observe(this, this::onTrailersDownloaded);

        viewModel.setMovieIdAndDownload(movie.id);

        //TODO: Button for reviews
        viewDataBinding.saveToFav.setOnClickListener((view)-> this.toggleFavourite());
    }

    private void onTrailersDownloaded(List<Trailer> trailers) {
        if (trailers != null && trailers.size() > 0) {
            viewDataBinding.trailersGroup.setVisibility(View.VISIBLE);
            trailersListAdapter.setTrailers(trailers);
        }
    }

    private void setIsFav(boolean isFav) {
        this.isFav = isFav;
        if (isFav) {
            viewDataBinding.saveToFav.setText(R.string.remove_from_fav);
        } else {
            viewDataBinding.saveToFav.setText(R.string.add_to_fav);
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);

        return true;
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

    public void toggleFavourite() {
        if (isFav) {
            AppDatabase.getExecutor().submit(() -> {
                Log.d(TAG, "Deleting Movie: " + movie);
                database.moviesDao().deleteMovie(movie);
            });
            setIsFav(false);
        } else {
            AppDatabase.getExecutor().submit(() -> {
                Log.d(TAG, "Saving Movie: " + movie);
                database.moviesDao().addMovie(movie);
            });
            setIsFav(true);
        }
    }
}
