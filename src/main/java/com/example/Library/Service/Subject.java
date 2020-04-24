package com.example.Library.Service;


public interface Subject {


    public void registerObserver(LibrarianHelpService observer);
    public void removeObserver(LibrarianHelpService observer);
    public void notifyObservers();

}
