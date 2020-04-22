package com.example.Library.Controller;

import com.example.Library.DAO.BookDAOImpl;
import com.example.Library.DAO.ItemDataDAOImpl;
import com.example.Library.DAO.MovieDAOImpl;
import com.example.Library.DAO.UserDataDAOImpl;
import com.example.Library.Model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ItemController {
    private final static String MOVIE_TYPE = "movie";
    private final static String BOOK_TYPE = "book";

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
}
