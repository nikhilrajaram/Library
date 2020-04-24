package com.example.Library.Model;

import javax.persistence.Entity;

@Entity
public class ObserverRelation {
    private String userEmail;
    private String librarianEmail;

    public ObserverRelation() {}

    public ObserverRelation(String userEmail, String librarianEmail) {
        this.userEmail = userEmail;
        this.librarianEmail = librarianEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getLibrarianEmail() {
        return librarianEmail;
    }

    public void setLibrarianEmail(String librarianEmail) {
        this.librarianEmail = librarianEmail;
    }
}
