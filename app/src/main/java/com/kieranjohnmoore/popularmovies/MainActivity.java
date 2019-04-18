package com.kieranjohnmoore.popularmovies;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.kieranjohnmoore.popularmovies.databinding.ActivityMainBinding;
import com.kieranjohnmoore.popularmovies.moviedb.MovieDBApi;
import com.kieranjohnmoore.popularmovies.moviedb.MovieListDownloader;
import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Movie>> {
    private static final String TAG = MainActivity.class.getSimpleName();

    private final MovieListAdapter movieListAdapter = new MovieListAdapter();
    private MovieDBApi.SortBy sortBy = MovieDBApi.SortBy.POPULAR;

    ActivityMainBinding viewBinding;

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

        LoaderManager.getInstance(this).initLoader(
                MovieListDownloader.LOADER_ID, getDownloaderBundle(), this);

        showProgressBar();
    }

    private Bundle getDownloaderBundle() {
        final Bundle data = new Bundle();
        data.putSerializable(MovieListDownloader.BUNDLE_SORT_KEY, sortBy);
        data.putInt(MovieListDownloader.BUNDLE_PAGE_NUMBER, 1);
        return data;
    }

    private void showProgressBar() {
        viewBinding.mainView.setVisibility(View.INVISIBLE);
        viewBinding.noData.setVisibility(View.INVISIBLE);
        viewBinding.progressBar.setVisibility(View.VISIBLE);
    }

    private void startUIUpdate() {
        Log.d(TAG, "Requesting new data for the UI");

        LoaderManager.getInstance(this).restartLoader(
                MovieListDownloader.LOADER_ID, getDownloaderBundle(), this);

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
                startUIUpdate();
                return true;
            case R.id.action_sort_popular:
                sortBy = MovieDBApi.SortBy.POPULAR;
                startUIUpdate();
                return true;
            case R.id.action_sort_rating:
                sortBy = MovieDBApi.SortBy.RATING;
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

    @NonNull
    @Override
    public Loader<List<Movie>> onCreateLoader(int id, @Nullable Bundle args) {
        return new MovieListDownloader(this, args);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Movie>> loader, List<Movie> data) {
        finishUIUpdate(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Movie>> loader) {}
}
