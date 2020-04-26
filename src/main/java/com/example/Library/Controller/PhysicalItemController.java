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

import java.util.Calendar;
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

    /** Map checking out a physical item */
    @RequestMapping(value = "/checkOutPhysicalItem")
    public String checkOutPhysicalItem(Authentication auth, Model model){
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        /** Check if user is a librarian */
        if (authorities.contains((new SimpleGrantedAuthority("LIBRARIAN")))) {

            /** User is librarian; authorized
             * Add model attributes
             * return checkOutPhysicalItem page */

            model.addAttribute("user", new User());
            model.addAttribute("item", new Item());
            return "checkOutPhysicalItem";
        }

        /** User is not librarian; unauthorized to check out a physical item
         *  return home page **/

        model.addAttribute("books", bookDAO.getRandomBooks(HomeController.N_CARDS_HOMEPAGE));
        model.addAttribute("movies", movieDAO.getRandomMovies(HomeController.N_CARDS_HOMEPAGE));

        return "home-auth";
    }

    /** Handling checking out a physical item */
    @RequestMapping(value = "/checkOutPhysicalItem", method = RequestMethod.POST)
    public String handleCheckOutPhysicalItem(@ModelAttribute User user, @ModelAttribute Item item, Model model){
        Integer nAvailable = itemDataDAO.getnAvailable(item); /** Quantity in stock for Item item */
        List<Record> overdueItemRecords = recordDAO.getOverdueItemRecordsByUser(user); /** List of overdue items for given user */

        if (nAvailable == null) {
            /** If quantity is null, there is no Item item
             * check out fail */
            model.addAttribute("reason", String.format("Item with associated itemId %d does not exist.",
                    item.getItemId()));
            return "failed-check-out";
        } else if (nAvailable == 0) {
            /** If quantity = 0, item is out of stock
             * check out fail */
            model.addAttribute("reason", "This item is out of stock.");
            return "failed-check-out";
        } else if (!overdueItemRecords.isEmpty()) {
            /** If user has overdue items, user is unable to checkout item
             * check out fail */
            model.addAttribute("reason", "User has overdue books.");
            model.addAttribute("overdueItemRecords", overdueItemRecords);
            return "failed-check-out";
        }

        /** If item is checked out, update itemDataDAO and decrement nAvailable for item by 1
         * Create a record for user who checked item and insert to recordDAO
         * check out successful */
        itemDataDAO.checkOutItem(item);
        Record record = new Record(user, item);
        model.addAttribute("record", record);
        recordDAO.insertRecord(record);
        return "item-checked-out";
    }

    /** Map returnPhysicalItem */
    @RequestMapping(value = "/returnPhysicalItem")
    public String returnPhysicalItem(Authentication auth, Model model){
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        /** Check if user is a librarian */
        if (authorities.contains((new SimpleGrantedAuthority("LIBRARIAN")))) {
            /** User is a librarian; authorized
             * return returnPhysicalItem page */

            model.addAttribute("user", new User());
            model.addAttribute("item", new Item());
            return "returnPhysicalItem";
        }

        /** User is not a librarian; unauthorized
         * return home page */

        model.addAttribute("books", bookDAO.getRandomBooks(HomeController.N_CARDS_HOMEPAGE));
        model.addAttribute("movies", movieDAO.getRandomMovies(HomeController.N_CARDS_HOMEPAGE));

        return "home-auth";
    }

    /** Handling return a physical item */
    @RequestMapping(value = "/returnPhysicalItem", method = RequestMethod.POST)
    public String handleReturnPhysicalItem(@ModelAttribute User user, @ModelAttribute Item item, Model model){
        Record correspondingRecord = recordDAO.getRecordByItem(item); /** Check record of Item item */
        itemDataDAO.returnItem(item); /** Return Item item */

        if (correspondingRecord == null) {
            /** if item record is null, alert librarian there is no record of this item being checked out */

            model.addAttribute("status", "checkNotes");
            model.addAttribute("notes", "There is no record of this item being checked out by this user");
            return "item-returned";
        } else if (Calendar.getInstance().getTimeInMillis() > correspondingRecord.getReturnByDate().getTime()) {
            /** if item is returned late, alert librarian */

            model.addAttribute("status", "overdue");
            model.addAttribute("notes", "This item is overdue. Collect a fine from the user.");
            return "item-returned";
        }

        /** if no conflicts, alert librarian the return is successful */

        model.addAttribute("status", "success");
        return "item-returned";
    }
}
