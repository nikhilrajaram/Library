package com.example.Library.DAO;

import com.example.Library.Model.Book;

import java.util.List;

public interface BookDAOImpl {
    /**
     * Add a book to books table
     * @param book to add to database
     * @return if adding is successful return true, else return false
     */
    Boolean addBook(Book book);

    /**
     * Add items to books table in batches
     * @param books list of books to add
     * @return list of statuses for each item
     */
    List<Boolean> batchAddBooks(List<Book> books);
}
