package com.kieranjohnmoore.popularmovies.moviedb.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewList {
    public int id;
    public int page;
    @SerializedName("results")
    public List<Review> reviews;
}
