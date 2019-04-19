package com.kieranjohnmoore.popularmovies.database;

import android.content.Context;
import android.util.Log;

import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "movies";
    private static AppDatabase database;
    private static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static AppDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (database == null) {
                Log.d(TAG, "Creating new database instance");
                database = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(TAG, "Getting the database instance");
        return database;
    }

    public static ExecutorService getExecutor() {
        synchronized (LOCK) {
            return executor;
        }
    }

    public abstract MoviesDao moviesDao();
}
