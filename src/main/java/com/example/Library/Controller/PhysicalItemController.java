package com.example.Library.Controller;

import com.example.Library.DAO.ItemDataDAO;
import com.example.Library.DAO.ItemDataDAOImpl;
import com.example.Library.Model.Item;
import com.example.Library.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

@Controller
public class PhysicalItemController {


    @Autowired
    private ItemDataDAO itemDataDAO;

    @RequestMapping(value = "/checkOutPhysicalItem")
    public String checkOutPhysicalItem(@ModelAttribute Authentication auth, Model model){
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        if (authorities.contains((new SimpleGrantedAuthority("LIBRARIAN")))) {
            // User is librarian
            User librarian = new User(auth.getName(), null, true);
            return "checkOutPhysicalItem";
        }

        // if user is not librarian, do not redirect to checkOutPhysicalItem page

        return "/home";
    }

    @RequestMapping(value = "/checkOutPhysicalItem", method = RequestMethod.POST)
    public String handleCheckOutPhysicalItem(@ModelAttribute User user, @ModelAttribute Item item, Model model){
        model.addAttribute("user", new User(user.getEmail(), user.getPassword(), user.isEnabled()));
        model.addAttribute("item", new Item(item.getItemId(),item.getType(), item.getnAvailable(), item.getnCheckedOut(), item.getDigital()));
        model.addAttribute("email", user.getEmail());
        model.addAttribute("itemID", item.getItemId());

        if(itemDataDAO.isAvailable(item)){
            item.setnAvailable(item.getnAvailable() - 1);
            return "item-checked-out";
        }

        return "failed-check-out";

    }

}
