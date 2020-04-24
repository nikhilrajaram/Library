package com.example.Library.Controller;

import com.example.Library.DAO.RequestHelpDAOImpl;
import com.example.Library.Model.HelpRequest;
import com.example.Library.Model.Librarian;
import com.example.Library.Model.User;
import com.example.Library.Util.LibrarianHelpObserver;
import com.example.Library.Util.UserHelpObservable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RequestHelpController {

    @Autowired
    private RequestHelpDAOImpl requestHelpDAO;

    @RequestMapping(value = "/requestHelp")
    public String requestHelp(@ModelAttribute User user, Model model){
        model.addAttribute("request" , new HelpRequest(user, null));
        return "requestHelp";
    }

    @RequestMapping(value = "/requestHelp", method = RequestMethod.POST)
    public String handleRequestHelp(@ModelAttribute User user, @ModelAttribute HelpRequest request, Model model) {
        (new UserHelpObservable(user)).notifyObservers();
        return "requestSubmitted";
    }

    @RequestMapping(value = "/requestSubmitted")
    public String submitRequest(@ModelAttribute HelpRequest requestHelp){
        return "home";
    }

    @RequestMapping(value = "/checkRequests", method = RequestMethod.POST)
    public String checkRequests(@ModelAttribute Librarian librarian, Model model){
        model.addAttribute("librarianHelpService", new LibrarianHelpObserver(librarian, null));

//        userHelpService.registerObserver(librarianHelpService);
//        userHelpService.requestHelp(content);

        return "checkRequests";
    }
}
