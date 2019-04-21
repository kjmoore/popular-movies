package com.kieranjohnmoore.popularmovies.activity;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kieranjohnmoore.popularmovies.databinding.ReviewItemBinding;
import com.kieranjohnmoore.popularmovies.moviedb.model.Review;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewsListAdapter extends RecyclerView.Adapter<ReviewsListAdapter.ReviewListHolder> {
    private List<Review> reviews = Collections.emptyList();

    @NonNull
    @Override
    public ReviewListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final ReviewItemBinding itemBinding =
                ReviewItemBinding.inflate(layoutInflater, viewGroup, false);
        return new ReviewListHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewListHolder trailersListHolder, int i) {
        final Review review = reviews.get(i);
        trailersListHolder.bind(review);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    class ReviewListHolder extends RecyclerView.ViewHolder {
        private ReviewItemBinding binding;

        ReviewListHolder(ReviewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bind(@NonNull final Review review) {
            binding.setReview(review);
            binding.executePendingBindings();
        }
    }
}
