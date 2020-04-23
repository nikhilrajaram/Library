package com.example.Library.DAO;

import com.example.Library.Model.Book;
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
public class BookDAOImpl implements BookDAO {

    private final String INSERT_BOOK = "INSERT INTO books (item_id, title, author, pub_date, genre, summary) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private final String GET_N_RANDOM_BOOKS = "SELECT * FROM books ORDER BY RANDOM() LIMIT ?";
    private final String GET_BOOK = "SELECT * FROM books WHERE item_id = ?";
    private final String GET_PAGE_N_BOOKS = "SELECT * FROM books LIMIT ? OFFSET ?";

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
                return books.size();
            }
        });

        for (int nAffected : rowsAffected) {
            statuses.add(nAffected == 1);
        }

        return statuses;
    }

    @Override
    public List<Book> getRandomBooks(Integer n) {
        Object[] args = new Object[1];
        args[0] = n;

        return template.query(GET_N_RANDOM_BOOKS, args, (ResultSetExtractor<List<Book>>) rs -> {
            List<Book> randomBooks = new ArrayList<>();
            while (rs.next()) {
                randomBooks.add(
                        new Book(rs.getInt("item_id"),
                                 rs.getString("title"),
                                 rs.getString("author"),
                                 rs.getDate("pub_date"),
                                 rs.getString("genre"),
                                 rs.getString("summary"))
                );
            }
            return randomBooks;
        });
    }

    @Override
    public Book getBookById(Integer itemId) {
        Object[] args = new Object[1];
        args[0] = itemId;

        return template.query(GET_BOOK, args, (ResultSetExtractor<Book>) rs -> {
            if (!rs.next()) return null;

            return new Book(rs.getInt("item_id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getDate("pub_date"),
                            rs.getString("genre"),
                            rs.getString("summary"));
        });
    }

    @Override
    public List<Book> getPageNBooks(Integer nBooksPerPage, Integer nPage) {
        Object[] args = new Object[2];
        args[0] = nBooksPerPage;
        args[1] = nBooksPerPage*nPage;

        return template.query(GET_PAGE_N_BOOKS, args, (ResultSetExtractor<List<Book>>) rs -> {
            List<Book> books = new ArrayList<>();
            while (rs.next()) {
                books.add(new Book(rs.getInt("item_id"),
                                    rs.getString("title"),
                                    rs.getString("author"),
                                    rs.getDate("pub_date"),
                                    rs.getString("genre"),
                                    rs.getString("summary"))
                );
            }
            return books;
        });
    }
}
