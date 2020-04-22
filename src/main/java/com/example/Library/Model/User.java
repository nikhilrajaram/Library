package com.example.Library.Model;

import javax.persistence.*;
import com.example.Library.Service.UserHelpService;


@Entity
public class User {

    @Id
    private String email;
    private String password;
    private boolean enabled;

    public User() {}

    public User(String email, String password, boolean enabled) {
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

    public void requestHelp(String content){
        UserHelpService helpService = new UserHelpService(this);
        helpService.requestHelp(content);
    }




}
