package com.example.Library.Util;

import com.example.Library.Model.User;

import java.util.ArrayList;

public class LibrarianHelpObserver extends Observer {

    private User librarian;
    private ArrayList<UserHelpObservable> observable = new ArrayList<>();

    public LibrarianHelpObserver() {}

    public LibrarianHelpObserver(User librarian, UserHelpObservable subject){
        this.librarian = librarian;
        this.observable.add(subject);
    }

    public void update(LibrarianHelpObserver observer, String content, User user) {
        /** will do view instead of printing */
        System.out.println("User: " + user.getEmail() + " needs assistance with: " + content);
    }
}
