package com.example.Library.Util;

import com.example.Library.Model.HelpRequest;
import com.example.Library.Model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class UserHelpObservable implements Observable {

    private User user;
    private HelpRequest request;

    public UserHelpObservable() {

    }

    public UserHelpObservable(User user, HelpRequest request) {
        this.user = user;
        this.request = request;
    }

    @Override
    public void addObserver(LibrarianHelpObserver observer) {
        // TODO
        // insert relation into observer_relations table
    }

    @Override
    public void removeObserver(LibrarianHelpObserver observer) {
        // TODO
        // remove from observer_relations table
    }

    @Override
    public void notifyObservers() {
        // TODO
        // insert request into help_requests
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public HelpRequest getRequest() {
        return request;
    }

    public void setRequest(HelpRequest request) {
        this.request = request;
    }
}
