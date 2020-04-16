package com.example.Library.Model;

public class RequestHelp {

    private String email;
    private String content;

    public RequestHelp(){}

    RequestHelp(String email, String content) {
        this.email = email;
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
