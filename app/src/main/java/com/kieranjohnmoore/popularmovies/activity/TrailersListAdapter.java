package com.kieranjohnmoore.popularmovies.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kieranjohnmoore.popularmovies.databinding.TrailerItemBinding;
import com.kieranjohnmoore.popularmovies.moviedb.model.Trailer;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrailersListAdapter extends RecyclerView.Adapter<TrailersListAdapter.TrailersListHolder> {
    private static final String TAG = TrailersListAdapter.class.getSimpleName();

    private List<Trailer> trailers = Collections.emptyList();

    @NonNull
    @Override
    public TrailersListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final TrailerItemBinding itemBinding =
                TrailerItemBinding.inflate(layoutInflater, viewGroup, false);
        return new TrailersListHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersListHolder trailersListHolder, int i) {
        final Trailer trailer = trailers.get(i);
        trailersListHolder.bind(trailer);
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    class TrailersListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Trailer trailer;
        private TrailerItemBinding binding;

        TrailersListHolder(TrailerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        private void bind(@NonNull final Trailer trailer) {
            this.trailer = trailer;
            binding.setTrailer(trailer);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "Clicked: " + trailer.name);

            if (trailer.source == null || trailer.source.isEmpty()) {
                Log.e(TAG, "Could not launch trailer: " + trailer);
            }

            final Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.source));
            final Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + trailer.source));
            try {
                v.getContext().startActivity(appIntent);
            } catch (ActivityNotFoundException ex) {
                v.getContext().startActivity(webIntent);
            }
        }
    }
}
