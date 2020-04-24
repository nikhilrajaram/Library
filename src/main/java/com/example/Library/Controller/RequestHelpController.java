package com.example.Library.Controller;

import com.example.Library.DAO.RequestHelpDAOImpl;
import com.example.Library.Model.Librarian;
import com.example.Library.Model.RequestHelp;
import com.example.Library.Model.User;
import com.example.Library.Service.LibrarianHelpService;
import com.example.Library.Service.UserHelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RequestHelpController {

     @Autowired
    private RequestHelpDAOImpl requestHelp;

    @Autowired
    private UserHelpService userHelpService;

    @Autowired
    private LibrarianHelpService librarianHelpService;

    private String content;


    @RequestMapping(value = "/requestHelp")
    public String requestHelp(@ModelAttribute User user, Model model){
        model.addAttribute("user", new User(user.getEmail(),user.getPassword(),user.isEnabled()));
        model.addAttribute("request" , new RequestHelp(user.getEmail(), user.requestHelp(content)));
        model.addAttribute("email", user.getEmail());
        model.addAttribute("content", user.requestHelp(content));
        content = requestHelp.getRequest(user.getEmail()).getContent();
        return "requestHelp";
    }

    @RequestMapping(value = "/requestHelp", method = RequestMethod.POST)
    public String handleRequestHelp(@ModelAttribute User user, Model model) {
        model.addAttribute("userHelpService", userHelpService);



        return "requestSubmitted";

    }

    @RequestMapping(value = "/requestSubmitted")
    public String submitRequest(@ModelAttribute RequestHelp requestHelp){
        return "home";
    }

    @RequestMapping(value = "/checkRequests", method = RequestMethod.POST)
    public String checkRequests(@ModelAttribute Librarian librarian, Model model){
        model.addAttribute("librarianHelpService", new LibrarianHelpService(librarian, userHelpService));


        /** Implement observer */
        userHelpService.registerObserver(librarianHelpService);
        userHelpService.requestHelp(content); /** This also updates librarianHelpRequest */

        return "checkRequests";
    }



   @RequestMapping(value = "/home")
    public String handleSubmittedRequest(){
        return "home";
   }



}
