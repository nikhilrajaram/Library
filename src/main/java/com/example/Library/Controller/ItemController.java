package com.example.Library.Controller;

import com.example.Library.DAO.BookDAOImpl;
import com.example.Library.DAO.ItemDataDAOImpl;
import com.example.Library.DAO.MovieDAOImpl;
import com.example.Library.DAO.UserDataDAOImpl;
import com.example.Library.Model.Book;
import com.example.Library.Model.Item;
import com.example.Library.Model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemController {
    private final static String MOVIE_TYPE = "movie";
    private final static String BOOK_TYPE = "book";
    private final static Integer ITEMS_PER_ROW = 4;
    private final static Integer ROWS_PER_PAGE = 3;

    @Autowired
    private UserDataDAOImpl userDataDAO;

    @Autowired
    private ItemDataDAOImpl itemDataDAO;

    @Autowired
    private BookDAOImpl bookDAO;

    @Autowired
    private MovieDAOImpl movieDAO;

    @RequestMapping("/item")
    public String handleItemPageVisit(@RequestParam(name = "id") Integer itemId, Model model) {
        Item item = itemDataDAO.getItemById(itemId);
        String itemType = item.getType();

        model.addAttribute("item", item);

        if (itemType.equals(MOVIE_TYPE)) {
            model.addAttribute("movie", movieDAO.getMovieById(itemId));
            return "movie";
        } else if (itemType.equals(BOOK_TYPE)) {
            model.addAttribute("book", bookDAO.getBookById(itemId));
            return "book";
        }

        return "/";
    }

    @RequestMapping("/books")
    public String handleBooksPageVisit(@RequestParam(name = "page", required = false) Integer pageNumber, Model model) {
        if (pageNumber == null) { pageNumber = 0; }

        List<Book> books = bookDAO.getPageNBooks(ITEMS_PER_ROW*ROWS_PER_PAGE, pageNumber);
        List<List<Book>> gridBookList = new ArrayList<>();

        for (int i = 0; i < ROWS_PER_PAGE; i++) {
            int fromIndex = Math.min(i*ITEMS_PER_ROW, books.size());
            int toIndex = Math.min((i+1)*ITEMS_PER_ROW, books.size());
            if (fromIndex == toIndex) {
                gridBookList.add(new ArrayList<>());
            } else {
                gridBookList.add(books.subList(i * ITEMS_PER_ROW, (i + 1) * ITEMS_PER_ROW));
            }
        }

        model.addAttribute("gridBookList", gridBookList);
        model.addAttribute("pageNumber", pageNumber);

        return "books";
    }

    @RequestMapping("/movies")
    public String handleMoviesPageVisit(@RequestParam(name = "page", required = false) Integer pageNumber, Model model) {
        if (pageNumber == null) { pageNumber = 0; }

        List<Movie> movies = movieDAO.getPageNMovies(ITEMS_PER_ROW*ROWS_PER_PAGE, pageNumber);
        List<List<Movie>> gridMovieList = new ArrayList<>();

        for (int i = 0; i < ROWS_PER_PAGE; i++) {
            int fromIndex = Math.min(i*ITEMS_PER_ROW, movies.size());
            int toIndex = Math.min((i+1)*ITEMS_PER_ROW, movies.size());
            if (fromIndex == toIndex) {
                gridMovieList.add(new ArrayList<>());
            } else {
                gridMovieList.add(movies.subList(i * ITEMS_PER_ROW, (i + 1) * ITEMS_PER_ROW));
            }
        }

        model.addAttribute("gridMovieList", gridMovieList);
        model.addAttribute("pageNumber", pageNumber);

        return "movies";
    }
}
