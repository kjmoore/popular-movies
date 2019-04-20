package com.kieranjohnmoore.popularmovies.viewmodel;

import android.app.Application;
import android.util.Log;

import com.kieranjohnmoore.popularmovies.database.AppDatabase;
import com.kieranjohnmoore.popularmovies.moviedb.MovieDBApi;
import com.kieranjohnmoore.popularmovies.moviedb.MovieListDownloader;
import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ReviewsViewModel extends AndroidViewModel  {
    private static String TAG = ReviewsViewModel.class.getSimpleName();

    private MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private LiveData<List<Movie>> favouriteMovies = new MutableLiveData<>();
    private MovieDBApi.SortBy sortBy = MovieDBApi.SortBy.POPULAR;

    private MovieListDownloader downloader;

    public ReviewsViewModel(@NonNull Application application) {
        super(application);

        Log.d(TAG, "Created new Main View Model");
//        downloader = new MovieListDownloader(this, sortBy);
        downloader.execute(1);
        favouriteMovies = AppDatabase.getInstance(this.getApplication()).moviesDao().loadAllMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<List<Movie>> getFavouriteMovies() {
        return favouriteMovies;
    }

    public void setSortOrderAndDownload(MovieDBApi.SortBy order) {
        if (order != sortBy) {
            sortBy = order;
            refreshServerData();
        } else {
            final List<Movie> oldData = movies.getValue();
            movies.postValue(oldData);
        }
    }

    public void refreshServerData() {
        Log.d(TAG, "Refreshing data");
        if (downloader != null) {
            downloader.cancel(true);
        }
//        downloader = new MovieListDownloader(this, sortBy);
        downloader.execute(1);
    }

    public void setDownloadedMovies(List<Movie> movies) {
        this.movies.setValue(movies);
    }
}
