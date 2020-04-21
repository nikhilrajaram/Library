package com.example.Library.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Book {
    @Id
    private Integer itemId;
    private String title;
    private String author;
    private Date pubDate;
    private String genre;
    private String summary;

    public Book() {}

    public Book(Integer itemId, String title, String author, Date pubDate, String genre, String summary) {
        this.itemId = itemId;
        this.title = title;
        this.author = author;
        this.pubDate = pubDate;
        this.genre = genre;
        this.summary = summary;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
