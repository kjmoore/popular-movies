package com.kieranjohnmoore.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.kieranjohnmoore.popularmovies.moviedb.MovieDBApi;
import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;

import java.util.Arrays;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private final MovieListAdapter movieListAdapter = new MovieListAdapter();

    @BindView(R.id.main_view)
    RecyclerView mainView;

    private class BackgroundTask extends AsyncTask<String, Void, List<Movie>> {
        @Override
        protected List<Movie> doInBackground(String... strings) {
            final List<Movie> movies = new MovieDBApi().getMovies(1);

            //TODO: Handle no data
            //TODO: Handle more pages
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
        ButterKnife.bind(this);

        final GridLayoutManager layoutManager = new GridLayoutManager(this, getSpanCount());
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

    private int getSpanCount() {
        final DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        final float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;

        final float columnWidthDp = MovieDBApi.POSTER_IMAGE_WIDTH / displayMetrics.density;

        float total = screenWidthDp / columnWidthDp;
        return Math.round(total);
    }
}
