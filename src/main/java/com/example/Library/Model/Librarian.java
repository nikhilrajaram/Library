package com.example.Library.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Librarian implements Observer{

    @Id
    private String email;
    private String password;
    private boolean enabled;
    private Subject observable;

    public Librarian() {}

    public Librarian(String email, String password, boolean enabled) {
        this.email = email;
        this.password = password;
        this.enabled = enabled;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void update(Observer observer, String content, User user) {
        /** will do view instead of printing */
        System.out.println("User" + user.getEmail() + " needs assistance with: " + content);

    }
}
