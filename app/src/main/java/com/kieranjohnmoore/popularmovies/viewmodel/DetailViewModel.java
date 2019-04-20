package com.kieranjohnmoore.popularmovies.viewmodel;

import android.app.Application;
import android.util.Log;

import com.kieranjohnmoore.popularmovies.moviedb.TrailersDownloader;
import com.kieranjohnmoore.popularmovies.moviedb.model.Trailer;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class DetailViewModel extends AndroidViewModel  {
    private static String TAG = DetailViewModel.class.getSimpleName();

    private MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>();

    public DetailViewModel(@NonNull Application application) {
        super(application);

        Log.d(TAG, "Created new Details Model");
    }

    public LiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

    public void setMovieIdAndDownload(int movieId) {
        final TrailersDownloader downloader = new TrailersDownloader(this);
        downloader.execute(movieId);
    }

    public void setDownloadedTrailers(List<Trailer> trailers) {
        this.trailers.setValue(trailers);
    }
}
