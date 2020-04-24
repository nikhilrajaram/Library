package com.example.Library.Controller;

import com.example.Library.DAO.HelpRequestDAOImpl;
import com.example.Library.Model.HelpRequest;
import com.example.Library.Model.User;
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
    public String requestHelp(Authentication auth, Model model){
        User user = new User(auth.getName(), null, true);
        HelpRequest request = new HelpRequest(user.getEmail(), null);
        model.addAttribute("request" , request);
        model.addAttribute("observable", new UserHelpObservable(user, request));
        return "requestHelp";
    }

    @RequestMapping(value = "/requestHelp", method = RequestMethod.POST)
    public String handleRequestHelp(@ModelAttribute HelpRequest request,
                                    @ModelAttribute UserHelpObservable userHelpObservable) {
        userHelpObservable.notifyObservers();
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
            // TODO: handle logic for displaying requests sent by subjects/observables
            // return something
        }

        // user is not librarian
        // TODO: handle logic for displaying responses from observer(s)

        return "checkRequests";
    }
}
