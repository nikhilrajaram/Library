package com.example.Library.Controller;

import com.example.Library.DAO.HelpRequestDAOImpl;
import com.example.Library.Model.HelpRequest;
import com.example.Library.Model.User;
import com.example.Library.Util.LibrarianHelpObserver;
import com.example.Library.Util.UserHelpObservable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class RequestHelpController {

    @Autowired
    private HelpRequestDAOImpl requestHelpDAO;

    @RequestMapping(value = "/requestHelp")
    public String requestHelp(Model model){
        model.addAttribute("request" , new HelpRequest());
        return "requestHelp";
    }

    @RequestMapping(value = "/requestHelp", method = RequestMethod.POST)
    public String handleRequestHelp(Authentication auth, @ModelAttribute HelpRequest request) {
        User user = new User(auth.getName(), null, true);
        request.setEmail(user.getEmail());
        (new UserHelpObservable(user, request)).notifyObservers();
        return "requestSubmitted";
    }

    @RequestMapping(value = "/requestSubmitted")
    public String submitRequest(@ModelAttribute HelpRequest requestHelp){
        // TODO: delete this it's unnecessary
        return "home";
    }

    @RequestMapping("/checkRequests")
    public String checkRequests(Authentication auth, Model model){
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        if (authorities.contains((new SimpleGrantedAuthority("LIBRARIAN")))) {
            // user is librarian
            User librarian = new User(auth.getName(), null, true);
            LibrarianHelpObserver librarianHelpObserver = new LibrarianHelpObserver(librarian);
            // update model
            librarianHelpObserver.update(model);
            return "checkRequests-librarian";
        }

        // user is not librarian
        // TODO: handle logic for displaying responses from observer(s)

        return "checkRequests";
    }
}
