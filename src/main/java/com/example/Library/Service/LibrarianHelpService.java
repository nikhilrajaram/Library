package com.example.Library.Service;

import com.example.Library.Model.Librarian;
import com.example.Library.Model.User;

import java.util.ArrayList;

public class LibrarianHelpService extends Observer {

    private Librarian librarian;
    private ArrayList<UserHelpService> observable = new ArrayList<UserHelpService>();;

    public LibrarianHelpService() {}

    public LibrarianHelpService(Librarian librarian, UserHelpService subject){
        this.librarian = librarian;
        this.observable.add(subject);
    }

    public void update(LibrarianHelpService observer, String content, User user) {
        /** will do view instead of printing */
        System.out.println("User: " + user.getEmail() + " needs assistance with: " + content);
    }
}
