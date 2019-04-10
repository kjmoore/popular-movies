package com.kieranjohnmoore.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.kieranjohnmoore.popularmovies.moviedb.MovieDBApi;
import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private final MovieListAdapter movieListAdapter = new MovieListAdapter();

    private class BackgroundTask extends AsyncTask<String, Void, List<Movie>> {
        @Override
        protected List<Movie> doInBackground(String... strings) {
            final List<Movie> movies = new MovieDBApi().getMovies(1);

            Log.d(TAG, Arrays.toString(movies.toArray()));
            return movies;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            movieListAdapter.updateMovies(movies);

            super.onPostExecute(movies);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView mainView = findViewById(R.id.main_view);

        final GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mainView.setLayoutManager(layoutManager);

        mainView.setAdapter(movieListAdapter);

        new BackgroundTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_reload == item.getItemId()) {
            final String textToShow = getString(R.string.reload_text);
            Toast.makeText(MainActivity.this, textToShow, Toast.LENGTH_SHORT).show();
            new BackgroundTask().execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
