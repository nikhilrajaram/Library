package com.example.Library.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Item {
    @Id
    private Integer id;
    private String type;
    private Integer nAvailable;
    private Integer nCheckedOut;
    private Boolean isDigital;

    public Item() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getnAvailable() {
        return nAvailable;
    }

    public void setnAvailable(Integer nAvailable) {
        this.nAvailable = nAvailable;
    }

    public Integer getnCheckedOut() {
        return nCheckedOut;
    }

    public void setnCheckedOut(Integer nCheckedOut) {
        this.nCheckedOut = nCheckedOut;
    }

    public Boolean getDigital() {
        return isDigital;
    }

    public void setDigital(Boolean digital) {
        isDigital = digital;
    }
}
