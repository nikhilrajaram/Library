package com.example.Library.DAO;

import com.example.Library.Model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO implements BookDAOImpl {

    private final String INSERT_BOOK = "INSERT INTO books (item_id, title, author, pub_date, genre, summary " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private final Integer BATCH_SIZE = 50;

    @Autowired
    JdbcTemplate template;

    @Override
    public Boolean addBook(Book book) {
        Object[] args = new Object[6];
        args[0] = book.getItemId();
        args[1] = book.getTitle();
        args[2] = book.getAuthor();
        args[3] = book.getPubDate();
        args[4] = book.getGenre();
        args[5] = book.getSummary();

        try {
            return template.update(INSERT_BOOK, args) != 1;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Boolean> batchAddBooks(List<Book> books) {
        List<Boolean> statuses = new ArrayList<>();
        int[] rowsAffected = template.batchUpdate(INSERT_BOOK, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, books.get(i).getItemId());
                ps.setString(2, books.get(i).getTitle());
                ps.setString(3, books.get(i).getAuthor());
                ps.setDate(4, new java.sql.Date(books.get(i).getPubDate().getTime()));
                ps.setString(5, books.get(i).getGenre());
                ps.setString(6, books.get(i).getSummary());
            }

            @Override
            public int getBatchSize() {
                return BATCH_SIZE;
            }
        });

        for (int nAffected : rowsAffected) {
            statuses.add(nAffected == 1);
        }

        return statuses;
    }
}
