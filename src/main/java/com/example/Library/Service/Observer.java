package com.example.Library.Service;

import com.example.Library.Model.User;

public interface Observer {
    public void update(Observer observer, String content, User user);
}
