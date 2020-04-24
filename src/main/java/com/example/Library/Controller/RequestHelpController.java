package com.example.Library.Controller;

import com.example.Library.DAO.RequestHelpDAOImpl;
import com.example.Library.Model.HelpRequest;
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
    private RequestHelpDAOImpl requestHelpDAO;

    @RequestMapping(value = "/requestHelp")
    public String requestHelp(Authentication auth, Model model){
        model.addAttribute("request" , new HelpRequest(auth.getName(), null));
        return "requestHelp";
    }

    @RequestMapping(value = "/requestHelp", method = RequestMethod.POST)
    public String handleRequestHelp(Authentication auth, @ModelAttribute HelpRequest request) {
        request.setEmail(auth.getName());
        requestHelpDAO.addHelpRequest(request);
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
