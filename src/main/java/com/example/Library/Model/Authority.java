package com.example.Library.Model;

import javax.persistence.Entity;

@Entity
public class Authority {
    private String email;
    private String authority;

    public Authority() {}

    public Authority(String email, String authority) {
        this.email = email;
        this.authority = authority;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
