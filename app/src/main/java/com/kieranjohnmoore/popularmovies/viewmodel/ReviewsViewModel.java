package com.kieranjohnmoore.popularmovies.viewmodel;

import android.app.Application;
import android.util.Log;

import com.kieranjohnmoore.popularmovies.database.AppDatabase;
import com.kieranjohnmoore.popularmovies.moviedb.MovieDBApi;
import com.kieranjohnmoore.popularmovies.moviedb.MovieListDownloader;
import com.kieranjohnmoore.popularmovies.moviedb.ReviewsDownloader;
import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;
import com.kieranjohnmoore.popularmovies.moviedb.model.Review;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ReviewsViewModel extends AndroidViewModel {
    private MutableLiveData<List<Review>> reviews = new MutableLiveData<>();

    public ReviewsViewModel(@NonNull Application application) {
        super(application);
    }

    public void setReviews(List<Review> reviews) {
        this.reviews.setValue(reviews);
    }

    public void setMovieIdAndDownload(int i) {
        final ReviewsDownloader downloader = new ReviewsDownloader(this);
        downloader.execute(i);
    }

    public LiveData<List<Review>> getReviews() {
        return reviews;
    }
}
