package com.example.Library.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Entity
public class Movie {
    @Id
    private Integer itemId;
    private String title;
    private Date releaseDate;
    private Integer runtime;
    private String genre;
    private String summary;

    public Movie() {}

    public Movie(Integer itemId, String title, Date releaseDate, Integer runtime, String genre, String summary) {
        this.itemId = itemId;
        this.title = title;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
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
