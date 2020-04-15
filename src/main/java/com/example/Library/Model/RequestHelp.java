package com.example.Library.Model;

import com.example.Library.Model.User;
import org.springframework.web.bind.annotation.RequestMapping;

public class RequestHelp {

    private Integer uid;
    private String content;

    public RequestHelp(String content, User user){
        this.content = content;
        this.uid = user.getUid();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId(){ return uid; }
}
