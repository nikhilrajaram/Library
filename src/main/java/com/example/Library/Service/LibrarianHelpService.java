package com.example.Library.Service;

import com.example.Library.Model.Librarian;
import com.example.Library.Model.User;

import java.util.ArrayList;

public class LibrarianHelpService implements Observer {

    private Librarian librarian;
    private ArrayList<UserHelpService>  observable = new ArrayList<UserHelpService>();;

    public LibrarianHelpService(Librarian librarian){
        this.librarian = librarian;
    }

    public void update(Observer observer, String content, User user) {
        /** will do view instead of printing */
        System.out.println("User" + user.getEmail() + " needs assistance with: " + content);

    }
}
