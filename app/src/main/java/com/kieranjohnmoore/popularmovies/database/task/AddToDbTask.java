package com.kieranjohnmoore.popularmovies.database.task;

import android.os.AsyncTask;
import android.util.Log;

import com.kieranjohnmoore.popularmovies.database.AppDatabase;
import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;

public class AddToDbTask extends AsyncTask<Movie, Void, Void> {
    private static final String TAG = AddToDbTask.class.getSimpleName();

    private final AppDatabase database;

    public AddToDbTask(AppDatabase database) {
        this.database = database;
    }

    @Override
    protected Void doInBackground(Movie... movies) {
        Log.d(TAG, "Saving Movie: " + movies[0]);
        database.moviesDao().addMovie(movies[0]);
        return null;
    }
}