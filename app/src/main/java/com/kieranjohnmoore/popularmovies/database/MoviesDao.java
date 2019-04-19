package com.kieranjohnmoore.popularmovies.database;

import com.kieranjohnmoore.popularmovies.moviedb.model.Movie;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MoviesDao {
    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> loadAllMovies();

    @Insert
    void addMovie(Movie taskEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(Movie taskEntry);

    @Delete
    void deleteTask(Movie taskEntry);

    @Query("SELECT * FROM movies WHERE id = :id")
    LiveData<Movie> loadTaskById(int id);
}
