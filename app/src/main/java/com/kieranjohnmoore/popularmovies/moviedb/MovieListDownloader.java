package com.kieranjohnmoore.popularmovies.moviedb;

import android.os.Bundle;
import android.util.Log;

import com.kieranjohnmoore.popularmovies.MainActivity;
import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class MovieListDownloader extends AsyncTaskLoader<List<Movie>> {
    private static final String TAG = MovieListDownloader.class.getSimpleName();

    public static final int LOADER_ID = 22;
    public static final String BUNDLE_SORT_KEY = "sort_by";
    public static final String BUNDLE_PAGE_NUMBER = "page_number";

    private final Bundle bundle;
    private List<Movie> movies;


    public MovieListDownloader(MainActivity app, Bundle bundle) {
        super(app);
        this.bundle = bundle;
    }

    @Nullable
    @Override
    public List<Movie> loadInBackground() {
        Log.d(TAG, "Loading movies in background with bundle: " + bundle);
        if (bundle == null || !bundle.containsKey(BUNDLE_SORT_KEY)
                || !bundle.containsKey(BUNDLE_PAGE_NUMBER)) {
            return Collections.emptyList();
        }

        final MovieDBApi.SortBy sortBy =
                (MovieDBApi.SortBy) bundle.getSerializable(BUNDLE_SORT_KEY);
        final int pageNumber = bundle.getInt(BUNDLE_PAGE_NUMBER);

        movies = new MovieDBApi().getMovies(pageNumber, sortBy);

        Log.d(TAG, Arrays.toString(movies.toArray()));
        return movies;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        if (movies == null) {
            Log.d(TAG, "No movies downloaded, forcing load");
            forceLoad();
        } else {
            Log.d(TAG, "Returning movies from cache");
            deliverResult(movies);
        }
    }
}