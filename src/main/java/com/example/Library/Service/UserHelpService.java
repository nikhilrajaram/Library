package com.example.Library.Service;

import com.example.Library.Model.RequestHelp;
import com.example.Library.Model.User;

import java.util.ArrayList;


public class UserHelpService implements Subject {

    private Observer observer;
    private User user;
    private String content;
    private ArrayList<Observer> observers = new ArrayList<Observer>();

    public UserHelpService(User user){
        this.user = user;
    }

    public void setObserver(Observer observer){
        this.observer = observer;
    }

    @Override
    public void registerObserver(Observer observer){
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer){
        observers.remove(observer);
    }


    @Override
    public void notifyObservers(){
        for (Observer obs: observers){
            obs.update(this.observer, this.content, this.user);
        }
    }

    public void requestHelp(String content){
        new RequestHelp(this.user, content);
        this.content = content;
        notifyObservers();
    }

}
