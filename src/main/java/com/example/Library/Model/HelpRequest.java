package com.example.Library.Model;

public class HelpRequest {
    private String email;
    private String content;

    public HelpRequest() {}

    public HelpRequest(String email, String content){
        this.email = email;
        this.content = content;
    }

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
