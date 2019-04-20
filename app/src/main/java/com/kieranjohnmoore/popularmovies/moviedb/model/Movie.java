package com.kieranjohnmoore.popularmovies.moviedb.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class Movie implements Parcelable {
    @SerializedName("poster_path")
    public String posterPath = "";
    public boolean adult = false;
    public String overview = "";
    @SerializedName("release_date")
    public String releaseDate = "";
    @Ignore
    @SerializedName("genre_ids")
    public List<Integer> genreIds = Collections.emptyList();
    @PrimaryKey
    public int id = 0;
    @SerializedName("original_title")
    public String originalTitle = "";
    @SerializedName("original_language")
    public String originalLanguage = "";
    public String title = "";
    @SerializedName("backdrop_path")
    public String backdropPath = "";
    public double popularity = 0;
    @SerializedName("vote_count")
    public int voteCount = 0;
    public boolean video = false;
    @SerializedName("vote_average")
    public double voteAverage = 0;

    @NonNull
    @Override
    @Ignore
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
                ", name='" + title + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", popularity=" + popularity +
                ", voteCount=" + voteCount +
                ", video=" + video +
                ", voteAverage=" + voteAverage +
                '}';
    }

    @Override
    @Ignore
    public int describeContents() {
        return 0;
    }

    @Override
    @Ignore
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

    @Ignore
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
