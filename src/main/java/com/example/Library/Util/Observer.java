package com.example.Library.Util;

import com.example.Library.Model.User;

public abstract class Observer {
    protected UserHelpObservable subject;
    public void update(LibrarianHelpObserver observer, String content, User user){};
}
