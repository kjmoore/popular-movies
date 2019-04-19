package com.kieranjohnmoore.popularmovies.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.kieranjohnmoore.popularmovies.R;
import com.kieranjohnmoore.popularmovies.databinding.ActivityMainBinding;
import com.kieranjohnmoore.popularmovies.moviedb.MovieDBApi;
import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;
import com.kieranjohnmoore.popularmovies.viewmodel.MainViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private final MovieListAdapter movieListAdapter = new MovieListAdapter();

    private ActivityMainBinding viewBinding;
    private MainViewModel.SORT_ORDER sortOrder;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewBinding.mainView.setVisibility(View.INVISIBLE);

        final GridLayoutManager layoutManager = new GridLayoutManager(this, getSpanCount());
        viewBinding.mainView.setLayoutManager(layoutManager);
        viewBinding.mainView.setAdapter(movieListAdapter);

        final MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getMovies().observe(this, this::finishUIUpdate);

        showProgressBar();
    }


    private void showProgressBar() {
        viewBinding.mainView.setVisibility(View.INVISIBLE);
        viewBinding.noData.setVisibility(View.INVISIBLE);
        viewBinding.progressBar.setVisibility(View.VISIBLE);
    }

    private void startUIUpdate() {
        Log.d(TAG, "Requesting new data for the UI");

        final MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.setSortOrder(sortOrder);

        showProgressBar();
    }

    private void finishUIUpdate(List<Movie> movies) {
        Log.d(TAG, "Updating UI with new data");
        movieListAdapter.updateMovies(movies);
        viewBinding.progressBar.setVisibility(View.INVISIBLE);
        if (movies.size() > 0) {
            viewBinding.mainView.setVisibility(View.VISIBLE);
        } else {
            viewBinding.noData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_reload:
                final String textToShow = getString(R.string.reload_text);
                Toast.makeText(MainActivity.this, textToShow, Toast.LENGTH_SHORT).show();
                final MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
                viewModel.refreshData();
                return true;
            case R.id.action_sort_popular:
                sortOrder = MainViewModel.SORT_ORDER.POPULAR;
                startUIUpdate();
                return true;
            case R.id.action_sort_rating:
                sortOrder = MainViewModel.SORT_ORDER.RATED;
                startUIUpdate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private int getSpanCount() {
        final DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        final float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;

        final float columnWidthDp = MovieDBApi.POSTER_IMAGE_WIDTH / displayMetrics.density;

        float total = screenWidthDp / columnWidthDp;
        return Math.round(total);
    }
}
