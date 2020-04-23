package com.example.Library.Controller;

import com.example.Library.Model.Librarian;
import com.example.Library.Model.RequestHelp;
import com.example.Library.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RequestHelpController {

    private String content;

    @RequestMapping(value = "/requestHelp")
    public String requestHelp(@ModelAttribute User user, Model model){
        model.addAttribute("request" , new RequestHelp(user, content));
        return "requestHelp";
    }

    @RequestMapping(value = "/requestHelp", method = RequestMethod.POST)
    public String handleRequestHelp(@ModelAttribute User user, Model model) {
        model.addAttribute("request", new RequestHelp(user, content));
        return "requestSubmitted";

    }

    @RequestMapping(value = "/requestSubmitted", method = RequestMethod.POST)
    public String submitRequest(@ModelAttribute RequestHelp requestHelp){
        return "home";
    }

   @RequestMapping(value = "/home")
    public String handleSubmittedRequest(){
        return "home";
   }

   /** Map checkRequests page for a librarian */
   @RequestMapping(value = "/checkRequests")
    public String checkRequests(@ModelAttribute Librarian librarian, Model model){
        model.addAttribute("librarian", new Librarian(librarian.getEmail(), librarian.getPassword(), librarian.isEnabled()));
        return "checkRequests";
   }


}
