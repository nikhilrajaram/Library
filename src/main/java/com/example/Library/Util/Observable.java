package com.example.Library.Util;

public interface Observable {
    void registerObserver(LibrarianHelpObserver observer);
    void removeObserver(LibrarianHelpObserver observer);
    void notifyObservers();
}
