package com.example.Library.Controller;

import com.example.Library.DAO.AuthorityDAOImpl;
import com.example.Library.DAO.BookDAOImpl;
import com.example.Library.DAO.MovieDAOImpl;
import com.example.Library.DAO.UserDataDAOImpl;
import com.example.Library.Model.Authority;
import com.example.Library.Model.ObserverRelation;
import com.example.Library.Model.User;
import com.example.Library.Util.UserHelpObservable;
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

import java.util.List;

@Controller
public class HomeController {
    static final Integer N_CARDS_HOMEPAGE = 4;
    static final String LIBRARIAN_AUTHORY = "LIBRARIAN";
    static final Integer N_LIBRARIANS_PER_USER = 3;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDataDAOImpl userDataDAO;

    @Autowired
    private BookDAOImpl bookDAO;

    @Autowired
    private MovieDAOImpl movieDAO;

    @Autowired
    private AuthorityDAOImpl authorityDAO;

    @Autowired
    AuthenticationManager authenticationManager;

    @RequestMapping("/")
    public String home(Authentication auth, Model model) {
        if (auth == null) {
            return "home";
        }

        if (!auth.isAuthenticated()) {
            return "home";
        }

        model.addAttribute("books", bookDAO.getRandomBooks(N_CARDS_HOMEPAGE));
        model.addAttribute("movies", movieDAO.getRandomMovies(N_CARDS_HOMEPAGE));

        return "home-auth";
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

        model.addAttribute("books", bookDAO.getRandomBooks(N_CARDS_HOMEPAGE));
        model.addAttribute("movies", movieDAO.getRandomMovies(N_CARDS_HOMEPAGE));

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

        UserHelpObservable observable = new UserHelpObservable(user);

        for (Authority authority : authorityDAO.getnAuthoritiesByAuthorityType(LIBRARIAN_AUTHORY,
                N_LIBRARIANS_PER_USER)) {
            observable.addObserver(new ObserverRelation(user.getEmail(), authority.getEmail()));
        }

        return "login";
    }

    @RequestMapping("/contactUs")
    public String contactUs(){
      return "contactUs";
    }

    @RequestMapping("/AboutUs")
    public String aboutUs(){
        return "AboutUs";
    }
}
