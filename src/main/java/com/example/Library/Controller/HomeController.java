package com.example.Library.Controller;

import com.example.Library.DAO.UserDataDAO;
import com.example.Library.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDataDAO userDataDAO;

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("loginAttempt", 0);
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String handleLogin(@ModelAttribute User user, Model model) {
        String bcryptPass = userDataDAO.getBcryptPassword(user);

        if (!passwordEncoder.matches(user.getPassword(), bcryptPass)) {
            model.addAttribute("user", new User());
            model.addAttribute("loginAttempt", -1);
            return "login";
        }

        return "home-auth";
    }

    @RequestMapping(value = "/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String handleRegistration(@ModelAttribute User user) {
        user.setUid(userDataDAO.getNewUid());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDataDAO.registerUser(user);
        return "login";
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
