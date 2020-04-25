package com.example.Library.Controller;

import com.example.Library.Model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

@Controller
public class PhysicalItemController {

    @RequestMapping(value = "/checkOutPhysicalItem")
    public String checkOutPhysicalItem(@ModelAttribute Authentication auth, Model model){
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        if (authorities.contains((new SimpleGrantedAuthority("LIBRARIAN")))) {
            User librarian = new User(auth.getName(), null, true);
            return "checkOutPhysicalItem";
        }

        // if user not librarian, do not redirect to checkOutPhysicalItem page

        return "/home";
    }

}
