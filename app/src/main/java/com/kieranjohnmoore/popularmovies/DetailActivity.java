package com.kieranjohnmoore.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.kieranjohnmoore.popularmovies.databinding.ActivityDetailBinding;
import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    public static final String MOVIE = "movie_details";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
            return;
        }

        final Movie movie = intent.getParcelableExtra(MOVIE);

        Log.d(TAG, "Detail started with: " + movie.toString());

        ActivityDetailBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        viewDataBinding.setMovie(movie);
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
}
