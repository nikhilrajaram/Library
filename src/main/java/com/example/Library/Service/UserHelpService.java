package com.example.Library.Service;

import com.example.Library.Model.HelpRequest;
import com.example.Library.Model.User;

import java.util.ArrayList;


public class UserHelpService implements Subject {

    private User user;
    private String content;
    private ArrayList<LibrarianHelpService> observers = new ArrayList<LibrarianHelpService>();

    public UserHelpService(User user){
        this.user = user;

    }


    @Override
    public void registerObserver(LibrarianHelpService observer){
        observers.add(observer);
    }

    @Override
    public void removeObserver(LibrarianHelpService observer){
        observers.remove(observer);
    }


    @Override
    public void notifyObservers(){
        for (LibrarianHelpService obs: observers){
            obs.update(obs, this.content, this.user);
        }
    }

    public void requestHelp(String content){
        new HelpRequest(this.user, content);
        this.content = content;
        notifyObservers();
    }

}
