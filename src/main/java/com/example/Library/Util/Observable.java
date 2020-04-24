package com.example.Library.Util;

import com.example.Library.Model.ObserverRelation;

public interface Observable {
    void addObserver(ObserverRelation relation);
    void removeObserver(ObserverRelation relation);
    void notifyObservers();
}
