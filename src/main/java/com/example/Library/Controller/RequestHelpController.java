package com.example.Library.Controller;

import com.example.Library.Model.RequestHelp;
import com.example.Library.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RequestHelpController {

    private String content;

    @RequestMapping(value = "/requestHelp")
    public String requestHelp(@ModelAttribute User user, Model model){
        model.addAttribute("request" , new RequestHelp(user, content));
        return "requestHelp";
    }

    @RequestMapping(value = "/requestHelp", method = RequestMethod.POST)
    public String handleRequestHelp(@ModelAttribute RequestHelp requestHelp) {
        return "requestSubmitted";
        // TODO: reroute successful request to good landing page
    }
}
