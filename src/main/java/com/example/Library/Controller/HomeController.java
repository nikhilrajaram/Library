package com.example.Library.Controller;

import com.example.Library.DAO.UserDataDAO;
import com.example.Library.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    AuthenticationManager authenticationManager;

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
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        try {
            Authentication auth = authenticationManager.authenticate(authReq);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
        } catch (BadCredentialsException e) {
            model.addAttribute("loginAttempt", -1);
            return "login";
        }

        return "home-auth";
    }

    @RequestMapping(value = "/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("isEmailAvailable", true);
        return "register";
    }

    @RequestMapping(value = "/test")
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String handleRegistration(@ModelAttribute User user, Model model) {
        if (!userDataDAO.isEmailAvailable(user.getEmail())) {
            model.addAttribute("isEmailAvailable", false);
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userDataDAO.registerUser(user);
        return "login";
    }
}
