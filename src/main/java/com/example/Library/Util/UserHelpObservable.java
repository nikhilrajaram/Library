package com.example.Library.Util;

import com.example.Library.DAO.HelpRequestDAOImpl;
import com.example.Library.DAO.ObserverRelationDAOImpl;
import com.example.Library.Model.HelpRequest;
import com.example.Library.Model.ObserverRelation;
import com.example.Library.Model.User;


public class UserHelpObservable implements Observable {

    private User user;
    private HelpRequest request;

    // class not managed by spring => cannot inject => must instantiate by extracting bean from application context
    HelpRequestDAOImpl helpRequestDAO = (HelpRequestDAOImpl) ApplicationContextUtils
            .getApplicationContext()
            .getBean("helpRequestDAO");
    ObserverRelationDAOImpl observerRelationDAO = (ObserverRelationDAOImpl) ApplicationContextUtils
            .getApplicationContext()
            .getBean("observerRelationDAO");

    public UserHelpObservable(User user) {
        this.user = user;
    }

    public UserHelpObservable(User user, HelpRequest request) {
        this.user = user;
        this.request = request;
    }

    @Override
    public void addObserver(ObserverRelation relation) {
        observerRelationDAO.addObserverRelation(relation);
    }

    @Override
    public void removeObserver(ObserverRelation relation) {
        observerRelationDAO.removeObserverRelation(relation);
    }

    @Override
    public void notifyObservers() {
        if (!helpRequestDAO.addHelpRequest(request)) {
            System.err.println("add help request error");
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public HelpRequest getRequest() {
        return request;
    }

    public void setRequest(HelpRequest request) {
        this.request = request;
    }
}
