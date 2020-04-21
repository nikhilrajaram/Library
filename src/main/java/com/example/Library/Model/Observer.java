package com.example.Library.Model;

public interface Observer {
    public void update(Observer observer, String content, User user);
}
