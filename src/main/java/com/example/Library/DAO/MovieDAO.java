package com.example.Library.DAO;

import com.example.Library.Model.Movie;

import java.util.List;

public interface MovieDAO {
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

    /**
     * Get n random movies from movies table
     * @param n number of random movies desired
     * @return n-long list of random movies
     */
    List<Movie> getRandomMovies(Integer n);

    /**
     * Get movie by item id
     * @param itemId item id of movie
     * @return movie corresponding to item id
     */
    Movie getMovieById(Integer itemId);

    /**
     * Get all movies on nth page of movies
     * @param nMoviesPerPage number of movies for page
     * @param nPage page number
     * @return list of movies on page n
     */
    List<Movie> getPageNMovies(Integer nMoviesPerPage, Integer nPage);
}
