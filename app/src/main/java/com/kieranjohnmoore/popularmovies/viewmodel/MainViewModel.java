package com.kieranjohnmoore.popularmovies.viewmodel;

import android.app.Application;
import android.util.Log;

import com.kieranjohnmoore.popularmovies.moviedb.MovieDBApi;
import com.kieranjohnmoore.popularmovies.moviedb.MovieListDownloader;
import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class MainViewModel extends AndroidViewModel  {
    private static String TAG = MainViewModel.class.getSimpleName();

    public enum SORT_ORDER {
        FAV,
        POPULAR,
        RATED
    }

    private MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private MovieDBApi.SortBy sortBy = MovieDBApi.SortBy.POPULAR;

    private MovieListDownloader downloader;

    public MainViewModel(@NonNull Application application) {
        super(application);

        Log.d(TAG, "Created new Main View Model");
        downloader = new MovieListDownloader(this, sortBy);
        downloader.execute(1);
    }

    public MutableLiveData<List<Movie>> getMovies() {
        return movies;
    }

    public void setSortOrder(SORT_ORDER order) {
        switch (order) {
            case POPULAR: sortBy = MovieDBApi.SortBy.POPULAR; break;
            case RATED: sortBy = MovieDBApi.SortBy.RATING; break;
            case FAV: break;
        }
        refreshData();
    }

    public void refreshData() {
        Log.d(TAG, "Refreshing data");
        if (downloader != null) {
            downloader.cancel(true);
        }
        downloader = new MovieListDownloader(this, sortBy);
        downloader.execute(1);
    }

    public void update(List<Movie> movies) {
        this.movies.setValue(movies);
    }
}
