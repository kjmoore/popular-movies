package com.kieranjohnmoore.popularmovies.moviedb;

import android.os.AsyncTask;
import android.util.Log;

import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;
import com.kieranjohnmoore.popularmovies.viewmodel.MainViewModel;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

public class MovieListDownloader extends AsyncTask<Integer, Void, List<Movie>> {
    private static final String TAG = MovieListDownloader.class.getSimpleName();

    private WeakReference<MainViewModel> adapterReference;
    private MovieDBApi.SortBy key;

    public MovieListDownloader(MainViewModel app, MovieDBApi.SortBy sortby) {
        this.adapterReference = new WeakReference<>(app);
        this.key = sortby;
    }

    @Override
    protected List<Movie> doInBackground(Integer... pageNumber) {
        final List<Movie> movies = new MovieDBApi().getMovies(pageNumber[0], key);

        Log.d(TAG, Arrays.toString(movies.toArray()));
        return movies;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        final MainViewModel app = adapterReference.get();
        if (app != null) {
            app.update(movies);
        }

        super.onPostExecute(movies);
    }
}