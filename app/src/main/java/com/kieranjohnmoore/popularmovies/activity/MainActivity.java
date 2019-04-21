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

    public static final String MOVIE = "movie_details";

    private final MovieListAdapter movieListAdapter = new MovieListAdapter();
    private ActivityMainBinding viewBinding;
    private List<Movie> favouriteMovies;

    public enum SORT_ORDER {
        FAV,
        POPULAR,
        RATED
    }
    private SORT_ORDER sortOrder = SORT_ORDER.POPULAR;

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
        viewModel.getMovies().observe(this, this::onMoviesDownloaded);
        viewModel.getFavouriteMovies().observe(this, this::onFavouriteMoviesChanged);

        showProgressBar();
    }

    private void showProgressBar() {
        Log.d(TAG, "Showing the progress bar, loading data");
        viewBinding.mainView.setVisibility(View.INVISIBLE);
        viewBinding.noData.setVisibility(View.INVISIBLE);
        viewBinding.progressBar.setVisibility(View.VISIBLE);
    }

    private void showMovies(List<Movie> movies) {
        if (movies != null) {
            movieListAdapter.updateMovies(movies);

            viewBinding.progressBar.setVisibility(View.INVISIBLE);
            if (movies.size() > 0) {
                viewBinding.mainView.setVisibility(View.VISIBLE);
            } else {
                viewBinding.noData.setVisibility(View.VISIBLE);
            }
        }
    }

    private void updateSortOrder() {
        showProgressBar();
        final MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        switch (sortOrder) {
            case FAV:
                showMovies(favouriteMovies);
                break;
            case POPULAR:
                viewModel.setSortOrderAndDownload(MovieDBApi.SortBy.POPULAR);
                break;
            case RATED:
                viewModel.setSortOrderAndDownload(MovieDBApi.SortBy.RATING);
                break;
        }
    }

    private void onMoviesDownloaded(List<Movie> movies) {
        Log.d(TAG, "Movies Changed");
        if (sortOrder == SORT_ORDER.POPULAR || sortOrder == SORT_ORDER.RATED) {
            showMovies(movies);
        }
    }

    private void onFavouriteMoviesChanged(List<Movie> movies) {
        Log.d(TAG, "Favourite Movies Changed");
        favouriteMovies = movies;
        if (sortOrder == SORT_ORDER.FAV) {
            showMovies(movies);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_reload:
                final String textToShow = getString(R.string.reload_text);
                Toast.makeText(MainActivity.this, textToShow, Toast.LENGTH_SHORT).show();
                final MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
                viewModel.refreshServerData();
                return true;
            case R.id.action_sort_popular:
                sortOrder = SORT_ORDER.POPULAR;
                updateSortOrder();
                return true;
            case R.id.action_sort_rating:
                sortOrder = SORT_ORDER.RATED;
                updateSortOrder();
                return true;
            case R.id.action_sort_favourites:
                sortOrder = SORT_ORDER.FAV;
                updateSortOrder();
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
