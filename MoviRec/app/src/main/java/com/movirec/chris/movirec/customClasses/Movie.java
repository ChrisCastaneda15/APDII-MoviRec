package com.movirec.chris.movirec.customClasses;

import com.movirec.chris.movirec.customClasses.Media;

import java.io.Serializable;
import java.util.Date;

public class Movie implements Serializable{
    String title;
    String plot;
    String poster;
    String date;
    String backDrop;

    public Movie(String title, String plot, String poster, String date, String backDrop) {
        this.title = title;
        this.plot = plot;
        this.poster = poster;
        this.date = date;
        this.backDrop = backDrop;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBackDrop() {
        return backDrop;
    }

    public void setBackDrop(String backDrop) {
        this.backDrop = backDrop;
    }
}
