package com.example.Library.Controller;

import com.example.Library.Model.RequestHelp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RequestHelpController {

    @RequestMapping(value = "/requestHelp")
    public String requestHelp(Model model){
        model.addAttribute("request" , new RequestHelp());
        return "requestHelp";
    }

    @RequestMapping(value = "/requestHelp", method = RequestMethod.POST)
    public String handleRequestHelp(@ModelAttribute RequestHelp requestHelp) {
        return "home";
        // TODO: reroute successful request to good landing page
    }
}
