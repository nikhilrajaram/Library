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

    /** Map requestHelp */
    @RequestMapping(value = "/requestHelp")
    public String requestHelp(Model model){
        /** Add attribute of HelpRequest */
        model.addAttribute("request" , new HelpRequest());
        return "requestHelp";
    }

    /** Handling Request Help */
    @RequestMapping(value = "/requestHelp", method = RequestMethod.POST)
    public String handleRequestHelp(Authentication auth, @ModelAttribute HelpRequest request) {

        /** Get user info
         * bind HelpRequest with user email */
        User user = new User(auth.getName(), null, true);
        request.setEmail(user.getEmail());

        /** Implementing Observer Pattern
         * Assign user for UserHelpObservable
         * Notify librarians of HelpRequest from user */
        (new UserHelpObservable(user, request)).notifyObservers();

        /** user's request submitted successfully */
        return "requestSubmitted";
    }


    /** Map checkRequests */
    @RequestMapping("/checkRequests")
    public String checkRequests(Authentication auth, Model model){
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        /** Check if user is a librarian */
        if (authorities.contains((new SimpleGrantedAuthority("LIBRARIAN")))) {
            /** User is librarian; authorized
             * Implementing Observer Pattern
             * Assign librarian for LibrarianHelpObserver */
            User librarian = new User(auth.getName(), null, true);
            LibrarianHelpObserver librarianHelpObserver = new LibrarianHelpObserver(librarian);

            /** Update model to display observable's request */
            librarianHelpObserver.update(model);
            return "checkRequests-librarian";
        }

        /** User is not librarian; unauthorized
         *  Display User's previous requests */
            User user = new User(auth.getName(), null, true);
            model.addAttribute("helpRequests", requestHelpDAO.getRequestsForUser(user));

        return "checkRequests";
    }
}
