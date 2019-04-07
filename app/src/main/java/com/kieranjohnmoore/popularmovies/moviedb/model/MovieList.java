package com.kieranjohnmoore.popularmovies.moviedb.model;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class MovieList {
    private int page = 0;
    @SerializedName("total_pages")
    private int totalPages = 0;
    private List<Movie> results = Collections.emptyList();

    public List<Movie> getResults() {
        return results;
    }
}
