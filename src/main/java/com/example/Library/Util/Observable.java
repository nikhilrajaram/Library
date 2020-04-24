package com.example.Library.Util;

public interface Observable {
    void addObserver(LibrarianHelpObserver observer);
    void removeObserver(LibrarianHelpObserver observer);
    void notifyObservers();
}
