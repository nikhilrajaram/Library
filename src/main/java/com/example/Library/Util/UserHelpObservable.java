package com.example.Library.Util;

import com.example.Library.Model.HelpRequest;
import com.example.Library.Model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class UserHelpObservable implements Observable {

    private User user;
    private String content;
    private ArrayList<LibrarianHelpObserver> observers = new ArrayList<LibrarianHelpObserver>();

    public UserHelpObservable(){

    }
    public UserHelpObservable(User user){
        this.user = user;
    }

    @Override
    public void registerObserver(LibrarianHelpObserver observer){
        observers.add(observer);
    }

    @Override
    public void removeObserver(LibrarianHelpObserver observer){
        observers.remove(observer);
    }


    @Override
    public void notifyObservers(){
        for (LibrarianHelpObserver obs: observers){
            obs.update(obs, this.content, this.user);
        }
    }

    public void requestHelp(String content){
        new HelpRequest(this.user, content);
        this.content = content;
        notifyObservers();
    }

}
