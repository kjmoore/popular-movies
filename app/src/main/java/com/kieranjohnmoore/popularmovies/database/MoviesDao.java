package com.kieranjohnmoore.popularmovies.database;

import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MoviesDao {
    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> loadAllMovies();

    @Insert
    void addMovie(Movie taskEntry);

    @Delete
    void deleteMovie(Movie taskEntry);

    @Query("SELECT * FROM movies WHERE id = :id")
    Movie getMovie(int id);
}
