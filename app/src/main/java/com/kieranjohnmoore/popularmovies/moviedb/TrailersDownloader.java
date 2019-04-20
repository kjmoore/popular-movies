package com.kieranjohnmoore.popularmovies.moviedb;

import android.os.AsyncTask;
import android.util.Log;

import com.kieranjohnmoore.popularmovies.moviedb.model.Trailer;
import com.kieranjohnmoore.popularmovies.viewmodel.DetailViewModel;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

public class TrailersDownloader extends AsyncTask<Integer, Void, List<Trailer>> {
    private static final String TAG = TrailersDownloader.class.getSimpleName();

    private WeakReference<DetailViewModel> adapterReference;

    public TrailersDownloader(DetailViewModel detailViewModel) {
        this.adapterReference = new WeakReference<>(detailViewModel);
    }

    @Override
    protected List<Trailer> doInBackground(Integer... movieId) {
        final List<Trailer> trailers = new MovieDBApi().getTrailers(movieId[0]);

        Log.d(TAG, Arrays.toString(trailers.toArray()));
        return trailers;
    }

    @Override
    protected void onPostExecute(List<Trailer> trailers) {
        final DetailViewModel app = adapterReference.get();
        if (app != null) {
            app.setDownloadedTrailers(trailers);
        }
        super.onPostExecute(trailers);
    }
}