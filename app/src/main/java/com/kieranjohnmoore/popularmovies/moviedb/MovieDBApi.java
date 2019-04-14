package com.kieranjohnmoore.popularmovies.moviedb;

import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;
import com.kieranjohnmoore.popularmovies.moviedb.model.MovieList;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MovieDBApi {
    private static final String TAG = MovieDBApi.class.getSimpleName();

    //Older builds didn't support TLS1.2 as used with themoviedb - so use http for those
    //TODO: Override the TLS implementation and manually enable TLS1.2
    private static final String SCHEME = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) ? "https" : "http";
    public static final String POSTER_URL = SCHEME + "://image.tmdb.org/t/p/w342";

    private static final String API_KEY = "YOUR_API_KEY_HERE";
    private static final String BASE_URL = SCHEME + "://api.themoviedb.org/3/discover/movie";

    private static final String KEY_API_KEY = "api_key";
    private static final String KEY_SORT_BY = "sort_by";
    private static final String KEY_PAGE = "page";
    private static final String KEY_POPULAR = "popularity.desc";
    private static final String KEY_RATING = "vote_average.desc";

    public enum SortBy {
        POPULAR(KEY_POPULAR),
        RATING(KEY_RATING);

        private final String key;
        SortBy(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    public static final int POSTER_IMAGE_WIDTH = 342;

    List<Movie> getMovies(int page, SortBy key) {
        final Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(KEY_API_KEY, API_KEY)
                .appendQueryParameter(KEY_SORT_BY, key.getKey())
                .appendQueryParameter(KEY_PAGE, Integer.toString(page))
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Connecting to URL: " + url);

        try {
            final String data = getResponseFromHttpUrl(url);

            if (!data.isEmpty()) {
                MovieList list = new Gson().fromJson(data, MovieList.class);
                return list.getResults();
            }
        } catch (IOException e) {
            Log.e(TAG, "Could not download data", e);
        }

        return Collections.emptyList();
    }

    private static String getResponseFromHttpUrl(URL url) throws IOException {
        final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            final InputStream in = urlConnection.getInputStream();

            final Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            final boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return "";
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
