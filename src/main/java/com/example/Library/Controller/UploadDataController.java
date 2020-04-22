package com.example.Library.Controller;

import com.example.Library.DAO.BookDAOImpl;
import com.example.Library.DAO.ItemDataDAOImpl;
import com.example.Library.DAO.MovieDAOImpl;
import com.example.Library.Util.ItemDataLoaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.FileNotFoundException;
import java.util.Map;

@Controller
public class UploadDataController {
    @Value("${DATA_ADMIN_PASSWORD}")
    private String adminKey;

    @Value("${fpath.books}")
    private String bookFpath;

    @Value("${fpath.movies}")
    private String movieFpath;

    @Autowired
    ItemDataDAOImpl itemDataDAO;

    @Autowired
    BookDAOImpl bookDAO;

    @Autowired
    MovieDAOImpl movieDAO;

    // NOTE: routes only accessible when csrf disabled by admin & routes are permitted to unauth users
    @RequestMapping(value = "/insertStaticMovies", method = RequestMethod.POST)
    public String insertStaticMovies(@RequestBody Map<String, String> keyMap) {
        if (!keyMap.get("key").equals(adminKey)) return "home";

        try {
            ItemDataLoaderUtil.readAndInsertMovies(movieFpath, itemDataDAO, movieDAO);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "home";
    }

    @RequestMapping(value = "/insertStaticBooks", method = RequestMethod.POST)
    public String insertStaticBooks(@RequestBody Map<String, String> keyMap) {
        if (!keyMap.get("key").equals(adminKey)) return "home";

        try {
            ItemDataLoaderUtil.readAndInsertBooks(bookFpath, itemDataDAO, bookDAO);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "home";
    }
}
