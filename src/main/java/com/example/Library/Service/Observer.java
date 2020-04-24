package com.example.Library.Service;

import com.example.Library.Model.User;

public abstract class Observer {
    protected UserHelpService subject;
    public void update(LibrarianHelpService observer, String content, User user){};
}
