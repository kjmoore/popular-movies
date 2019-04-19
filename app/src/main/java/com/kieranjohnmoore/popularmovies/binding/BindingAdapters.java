package com.kieranjohnmoore.popularmovies.binding;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.kieranjohnmoore.popularmovies.moviedb.MovieDBApi;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import androidx.databinding.BindingAdapter;

public class BindingAdapters {
    private static final String TAG = BindingAdapters.class.getSimpleName();

    @BindingAdapter("imageUrl")
    public static void setImage(final ImageView poster, final String posterPath) {
        if (posterPath == null || posterPath.isEmpty()) {
            Log.w(TAG, "The image URL was empty, not setting the background");
            return;
        }

        final Picasso.Builder picassoBuilder = new Picasso.Builder(poster.getContext());

        picassoBuilder.listener((picasso, uri, exception) -> Log.e(TAG, uri.toString(), exception));

        picassoBuilder.build()
                .load(MovieDBApi.POSTER_URL + posterPath)
                .fit()
                .centerCrop()
                .error(android.R.drawable.stat_notify_error)
                .into(poster);
    }

    @BindingAdapter("android:text")
    public static void setText(final TextView text, final double toSet) {
        text.setText(String.format(Locale.getDefault(),"%.2f", toSet));
    }
}
