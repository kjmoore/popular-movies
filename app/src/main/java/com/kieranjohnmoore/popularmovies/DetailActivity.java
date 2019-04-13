package com.kieranjohnmoore.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kieranjohnmoore.popularmovies.moviedb.MovieDBApi;
import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    public static final String MOVIE = "movie_details";

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
            return;
        }

        movie = (Movie) intent.getSerializableExtra(MOVIE);

        Log.d(TAG, "Detail started with: " + movie.toString());

        populateUI();
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        final TextView title = findViewById(R.id.title);
        final ImageView poster = findViewById(R.id.poster);
        final TextView synopsis = findViewById(R.id.synopsis);
        final TextView userRating = findViewById(R.id.user_rating);
        final TextView releaseDate = findViewById(R.id.release_date);

        title.setText(movie.getTitle());
        setImage(poster, movie.getPosterPath());
        synopsis.setText(movie.getOverview());
        userRating.setText(String.format(Locale.getDefault(),"%.2f", movie.getVoteAverage()));
        releaseDate.setText(movie.getReleaseDate());
    }

    private void setImage(ImageView poster, final String posterPath) {
        final Picasso.Builder picassoBuilder = new Picasso.Builder(poster.getContext());

        picassoBuilder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                Log.e(TAG, uri.toString(), exception);
            }
        });

        picassoBuilder.build()
                .load(MovieDBApi.POSTER_URL + posterPath)
                .fit()
                .centerCrop()
                .error(android.R.drawable.stat_notify_error)
                .into(poster);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);

        Log.e("TIS", movie.toString());

        return true;
    }
}
