package com.kieranjohnmoore.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kieranjohnmoore.popularmovies.moviedb.MovieDBApi;
import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private class BackgroundTask extends AsyncTask<String, Void, List<Movie>> {
        @Override
        protected List<Movie> doInBackground(String... strings) {
            final List<Movie> movies = new MovieDBApi().getMovies(1);

            Log.d(TAG, Arrays.toString(movies.toArray()));
            return movies;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            final StringBuilder text = new StringBuilder();
            for (Movie movie : movies) {
                text.append(movie.getTitle());
                text.append("\n");
            }

            final TextView data = findViewById(R.id.hello);
            data.setText(text.toString());
            super.onPostExecute(movies);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new BackgroundTask().execute();
    }
}
