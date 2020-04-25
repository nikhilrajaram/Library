package com.example.Library.Util;

import com.example.Library.DAO.HelpRequestDAOImpl;
import com.example.Library.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.util.ArrayList;

public class LibrarianHelpObserver extends Observer {

    private User librarian;

    // class not managed by spring => cannot inject => must instantiate by extracting bean from application context
    HelpRequestDAOImpl helpRequestDAO = (HelpRequestDAOImpl) ApplicationContextUtils
            .getApplicationContext()
            .getBean("helpRequestDAO");

    public LibrarianHelpObserver() {}

    public LibrarianHelpObserver(User librarian) {
        this.librarian = librarian;
    }

    public void update(Model model) {
        model.addAttribute("helpRequests", helpRequestDAO.getRequestsForLibrarian(librarian));
    }
}
