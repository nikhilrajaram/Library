package com.example.Library.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import com.example.Library.Model.User;

@Entity
public class HelpRequest {

    @Id
    private String email;
    private String content;

    public HelpRequest(){}

    public HelpRequest(User user, String content) {
        this.email = user.getEmail();
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
