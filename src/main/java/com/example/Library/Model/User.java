package com.example.Library.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.example.Library.Model.Observer;


@Entity
public class User implements Subject {

    @Id
    private String email;
    private String password;
    private boolean enabled;
    private Observer observer;
    private String content;


    public User() {}

    public User(String email, String password, boolean enabled) {
        this.email = email;
        this.password = password;
        this.enabled = enabled;
    }

    @OneToMany
    private List<Observer> observers = new ArrayList<Observer>();

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
            obs.update(this.observer, this.content, this);
        }
    }

    public void requestHelp(String content){
        new RequestHelp(this, content);
        this.content = content;
        notifyObservers();
    }


    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }




}
