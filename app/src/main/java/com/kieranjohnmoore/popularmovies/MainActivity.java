package com.kieranjohnmoore.popularmovies;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kieranjohnmoore.popularmovies.moviedb.MovieDBApi;
import com.kieranjohnmoore.popularmovies.moviedb.MovieListDownloader;
import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;

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
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.no_data)
    TextView noData;

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

        mainView.setVisibility(View.INVISIBLE);

        final GridLayoutManager layoutManager = new GridLayoutManager(this, getSpanCount());
        mainView.setLayoutManager(layoutManager);
        mainView.setAdapter(movieListAdapter);

        new MovieListDownloader(this).execute(1);
    }

    public void startUIUpdate() {
        Log.d(TAG, "Loading new data");
        mainView.setVisibility(View.INVISIBLE);
        noData.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void finishUIUpdate(List<Movie> movies) {
        Log.d(TAG, "Updating UI with new data");
        movieListAdapter.updateMovies(movies);
        progressBar.setVisibility(View.INVISIBLE);
        if (movies.size() > 0) {
            mainView.setVisibility(View.VISIBLE);
        } else {
            noData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_reload == item.getItemId()) {
            final String textToShow = getString(R.string.reload_text);
            Toast.makeText(MainActivity.this, textToShow, Toast.LENGTH_SHORT).show();
            new MovieListDownloader(this).execute(1);
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
