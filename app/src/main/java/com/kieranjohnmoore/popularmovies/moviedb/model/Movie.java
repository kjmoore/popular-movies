package com.kieranjohnmoore.popularmovies.moviedb.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;

public class Movie implements Parcelable {

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

    public String getPopularityString() {
        return String.format(Locale.getDefault(),"%.2f", getPopularity());
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

    public String getVoteAverageString() {
        return String.format(Locale.getDefault(),"%.2f", getVoteAverage());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //Warning, changing this requires changes to the CREATOR
        dest.writeString(posterPath);
        dest.writeInt(adult ? 1 : 0);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeList(genreIds);
        dest.writeInt(id);
        dest.writeString(originalTitle);
        dest.writeString(originalLanguage);
        dest.writeString(title);
        dest.writeString(backdropPath);
        dest.writeDouble(popularity);
        dest.writeInt(voteCount);
        dest.writeInt(video ? 1 : 0);
        dest.writeDouble(voteAverage);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            //Warning, any changes here need to be reflected in writeToParcel
            final Movie unpackedMovie = new Movie();
            unpackedMovie.posterPath = in.readString();
            unpackedMovie.adult = (in.readInt() == 1);
            unpackedMovie.overview = in.readString();
            unpackedMovie.releaseDate = in.readString();
            unpackedMovie.genreIds = new ArrayList<>();
            in.readList(unpackedMovie.genreIds, Integer.class.getClassLoader());
            unpackedMovie.id = in.readInt();
            unpackedMovie.originalTitle = in.readString();
            unpackedMovie.originalLanguage = in.readString();
            unpackedMovie.title = in.readString();
            unpackedMovie.backdropPath = in.readString();
            unpackedMovie.popularity = in.readDouble();
            unpackedMovie.voteCount = in.readInt();
            unpackedMovie.video = (in.readInt() == 1);
            unpackedMovie.voteAverage = in.readDouble();
            return unpackedMovie;
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
