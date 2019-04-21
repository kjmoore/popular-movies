package com.kieranjohnmoore.popularmovies.moviedb;

import android.os.AsyncTask;
import android.util.Log;

import com.kieranjohnmoore.popularmovies.moviedb.model.Review;
import com.kieranjohnmoore.popularmovies.moviedb.model.Trailer;
import com.kieranjohnmoore.popularmovies.viewmodel.DetailViewModel;
import com.kieranjohnmoore.popularmovies.viewmodel.ReviewsViewModel;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

public class ReviewsDownloader extends AsyncTask<Integer, Void, List<Review>> {
    private static final String TAG = ReviewsDownloader.class.getSimpleName();

    private WeakReference<ReviewsViewModel> adapterReference;

    public ReviewsDownloader(ReviewsViewModel detailViewModel) {
        this.adapterReference = new WeakReference<>(detailViewModel);
    }

    @Override
    protected List<Review> doInBackground(Integer... movieId) {
        final List<Review> trailers = new MovieDBApi().getReviews(movieId[0]);

        Log.d(TAG, Arrays.toString(trailers.toArray()));
        return trailers;
    }

    @Override
    protected void onPostExecute(List<Review> trailers) {
        final ReviewsViewModel app = adapterReference.get();
        if (app != null) {
            app.setReviews(trailers);
        }
        super.onPostExecute(trailers);
    }
}