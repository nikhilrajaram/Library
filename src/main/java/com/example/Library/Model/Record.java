package com.example.Library.Model;

import java.sql.Date;
import java.util.Calendar;

public class Record {

    private static final Integer CHECK_OUT_LENGTH = 14; // 2 weeks;

    private String userEmail;
    private Integer itemId;
    private Date checkOutDate;
    private Date returnByDate;

    public Record() {}

    public Record(User user, Item item) {
        this.userEmail = user.getEmail();
        this.itemId = item.getItemId();
        Calendar dateCalendar = Calendar.getInstance();
        this.checkOutDate = new java.sql.Date(dateCalendar.getTime().getTime());
        dateCalendar.add(Calendar.DAY_OF_YEAR, CHECK_OUT_LENGTH);
        this.returnByDate = new java.sql.Date(dateCalendar.getTime().getTime());
    }

    public Record(String userEmail, Integer itemId, Date checkOutDate, Date returnByDate) {
        this.userEmail = userEmail;
        this.itemId = itemId;
        this.checkOutDate = checkOutDate;
        this.returnByDate = returnByDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Date getReturnByDate() {
        return returnByDate;
    }

    public void setReturnByDate(Date returnByDate) {
        this.returnByDate = returnByDate;
    }
}
