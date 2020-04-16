package com.example.Library.Controller;

import com.example.Library.Model.RequestHelp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RequestHelpController {

    @RequestMapping(value = "/requestHelp")
    public String requestHelp(Model model){
        model.addAttribute("request" , new RequestHelp());
        return "requestHelp";
    }

}
