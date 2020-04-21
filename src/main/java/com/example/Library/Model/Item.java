package com.example.Library.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Item {
    @Id
    private Integer itemId;
    private String type;
    private Integer nAvailable;
    private Integer nCheckedOut;
    private Boolean isDigital;

    public Item() {}

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
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
