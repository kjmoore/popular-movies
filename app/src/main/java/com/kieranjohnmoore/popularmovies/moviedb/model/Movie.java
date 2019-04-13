package com.kieranjohnmoore.popularmovies.moviedb.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;

public class Movie implements Serializable {

    @SerializedName("poster_path")
    private String posterPath = "";
    private boolean adult = false;
    private String overview = "";
    @SerializedName("release_date")
    private String releaseDate = "";
    @SerializedName("genre_ids")
    private List<Integer> genreIds = Collections.emptyList();
    private int id = 0;
    @SerializedName("original_title")
    private String originalTitle = "";
    @SerializedName("original_language")
    private String originalLanguage = "";
    private String title = "";
    @SerializedName("backdrop_path")
    private String backdropPath = "";
    private double popularity = 0;
    @SerializedName("vote_count")
    private int voteCount = 0;
    private boolean video = false;
    @SerializedName("vote_average")
    private double voteAverage = 0;

    public String getPosterPath() {
        return posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public int getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    @NonNull
    @Override
    public String toString() {
        return "Movie{" +
                "posterPath='" + posterPath + '\'' +
                ", adult=" + adult +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", genreIds=" + genreIds +
                ", id=" + id +
                ", originalTitle='" + originalTitle + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", title='" + title + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", popularity=" + popularity +
                ", voteCount=" + voteCount +
                ", video=" + video +
                ", voteAverage=" + voteAverage +
                '}';
    }
}
