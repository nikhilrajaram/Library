package com.example.Library.DAO;

import com.example.Library.Model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MovieDAOImpl implements MovieDAO {
    private final String INSERT_MOVIE = "INSERT INTO movies (item_id, title, release_date, runtime, genre, summary) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private final String GET_N_RANDOM_MOVIES = "SELECT * FROM movies ORDER BY RANDOM() LIMIT ?";

    @Autowired
    JdbcTemplate template;

    @Override
    public Boolean addMovie(Movie movie) {
        Object[] args = new Object[6];
        args[0] = movie.getItemId();
        args[1] = movie.getTitle();
        args[2] = movie.getReleaseDate();
        args[3] = movie.getRuntime();
        args[4] = movie.getGenre();
        args[5] = movie.getSummary();

        try {
            return template.update(INSERT_MOVIE, args) != 1;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Boolean> batchAddMovie(List<Movie> movies) {
        List<Boolean> statuses = new ArrayList<>();
        int[] rowsAffected = template.batchUpdate(INSERT_MOVIE, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, movies.get(i).getItemId());
                ps.setString(2, movies.get(i).getTitle());
                ps.setDate(3, new java.sql.Date(movies.get(i).getReleaseDate().getTime()));
                ps.setInt(4, movies.get(i).getRuntime());
                ps.setString(5, movies.get(i).getGenre());
                ps.setString(6, movies.get(i).getSummary());
            }

            @Override
            public int getBatchSize() {
                return movies.size();
            }
        });

        for (int nAffected : rowsAffected) {
            statuses.add(nAffected == 1);
        }

        return statuses;
    }

    @Override
    public List<Movie> getRandomMovies(Integer n) {
        Object[] args = new Object[1];
        args[0] = n;

        return template.query(GET_N_RANDOM_MOVIES, args, (ResultSetExtractor<List<Movie>>) rs -> {
            List<Movie> randomBooks = new ArrayList<>();
            while (rs.next()) {
                randomBooks.add(
                        new Movie(rs.getInt("item_id"),
                                  rs.getString("title"),
                                  rs.getDate("release_date"),
                                  rs.getInt("runtime"),
                                  rs.getString("genre"),
                                  rs.getString("summary"))
                );
            }
            return randomBooks;
        });
    }
}
