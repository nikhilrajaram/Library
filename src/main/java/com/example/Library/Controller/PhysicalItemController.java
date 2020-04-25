package com.example.Library.Controller;

import com.example.Library.DAO.BookDAOImpl;
import com.example.Library.DAO.ItemDataDAOImpl;
import com.example.Library.DAO.MovieDAOImpl;
import com.example.Library.DAO.RecordDAOImpl;
import com.example.Library.Model.Item;
import com.example.Library.Model.Record;
import com.example.Library.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;
import java.util.List;

@Controller
public class PhysicalItemController {

    @Autowired
    private ItemDataDAOImpl itemDataDAO;

    @Autowired
    private RecordDAOImpl recordDAO;

    @Autowired
    private BookDAOImpl bookDAO;

    @Autowired
    private MovieDAOImpl movieDAO;

    @RequestMapping(value = "/checkOutPhysicalItem")
    public String checkOutPhysicalItem(Authentication auth, Model model){
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        if (authorities.contains((new SimpleGrantedAuthority("LIBRARIAN")))) {
            // User is librarian
            model.addAttribute("user", new User());
            model.addAttribute("item", new Item());
            return "checkOutPhysicalItem";
        }

        model.addAttribute("books", bookDAO.getRandomBooks(HomeController.N_CARDS_HOMEPAGE));
        model.addAttribute("movies", movieDAO.getRandomMovies(HomeController.N_CARDS_HOMEPAGE));

        return "home-auth";
    }

    @RequestMapping(value = "/checkOutPhysicalItem", method = RequestMethod.POST)
    public String handleCheckOutPhysicalItem(@ModelAttribute User user, @ModelAttribute Item item, Model model){
        Integer nAvailable = itemDataDAO.getnAvailable(item);
        List<Record> overdueItemRecords = recordDAO.getOverdueItemRecordsByUser(user);

        if (nAvailable == null) {
            model.addAttribute("reason", String.format("Item with associated itemId %d does not exist.",
                    item.getItemId()));
            return "failed-check-out";
        } else if (nAvailable == 0) {
            model.addAttribute("reason", "This item is out of stock.");
            return "failed-check-out";
        } else if (!overdueItemRecords.isEmpty()) {
            model.addAttribute("reason", "User has overdue books.");
            model.addAttribute("overdueItemRecords", overdueItemRecords);
            return "failed-check-out";
        }

        itemDataDAO.checkOutItem(item);
        Record record = new Record(user, item);
        model.addAttribute("record", record);
        recordDAO.insertRecord(record);
        return "item-checked-out";
    }

}
