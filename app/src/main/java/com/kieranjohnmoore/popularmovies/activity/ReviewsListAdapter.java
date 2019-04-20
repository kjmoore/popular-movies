package com.kieranjohnmoore.popularmovies.activity;

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

public class ReviewsListAdapter extends RecyclerView.Adapter<ReviewsListAdapter.TrailersListHolder> {
    private static final String TAG = ReviewsListAdapter.class.getSimpleName();

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
            Log.d(TAG, "Clicked: " + trailers.get(getAdapterPosition()).name);
//            final Intent intent = new Intent(v.getContext(), DetailActivity.class);
//            intent.putExtra(DetailActivity.MOVIE, trailer);
//            v.getContext().startActivity(intent);
            //TODO: Launch browser or youtube
        }
    }
}
