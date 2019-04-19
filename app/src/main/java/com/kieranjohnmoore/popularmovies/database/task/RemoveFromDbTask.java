package com.kieranjohnmoore.popularmovies.database.task;

import android.os.AsyncTask;
import android.util.Log;

import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;

public class RemoveFromDbTask extends AsyncTask<Movie, Void, Void> {
    private static final String TAG = AddToDbTask.class.getSimpleName();

    @Override
    protected Void doInBackground(Movie... movies) {
        Log.d(TAG, "Removing Movie: " + movies[0]);
        return null;
    }
}