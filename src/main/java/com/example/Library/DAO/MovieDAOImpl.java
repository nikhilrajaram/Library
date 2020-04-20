package com.example.Library.DAO;

import com.example.Library.Model.Movie;

import java.util.List;

public interface MovieDAOImpl {
    /**
     * Add a movie to movies table
     * @param movie to add to database
     * @return if adding is successful return true, else return false
     */
    Boolean addMovie(Movie movie);

    /**
     * Add items to movies table in batches
     * @param movies list of movies to add
     * @return list of statuses for each item
     */
    List<Boolean> batchAddMovie(List<Movie> movies);
}
