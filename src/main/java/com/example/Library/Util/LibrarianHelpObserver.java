package com.example.Library.Util;

import com.example.Library.Model.Librarian;
import com.example.Library.Model.User;

import java.util.ArrayList;

public class LibrarianHelpObserver extends Observer {

    private Librarian librarian;
    private ArrayList<UserHelpObservable> observable = new ArrayList<UserHelpObservable>();;

    public LibrarianHelpObserver() {}

    public LibrarianHelpObserver(Librarian librarian, UserHelpObservable subject){
        this.librarian = librarian;
        this.observable.add(subject);
    }

    public void update(LibrarianHelpObserver observer, String content, User user) {
        /** will do view instead of printing */
        System.out.println("User: " + user.getEmail() + " needs assistance with: " + content);
    }
}
