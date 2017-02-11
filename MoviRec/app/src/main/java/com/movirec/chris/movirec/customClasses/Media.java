package com.movirec.chris.movirec.customClasses;

import java.io.Serializable;

public class Media implements Serializable {

    //MOVIE
    //{"Title":"Daredevil","Year":"2003","Rated":"PG-13","Released":"14 Feb 2003",
    // "Runtime":"103 min","Genre":"Action, Crime, Drama","Director":"Mark Steven Johnson",
    // "Writer":"Mark Steven Johnson (screenplay)","Actors":"Ben Affleck, Jennifer Garner, Colin Farrell, Michael Clarke Duncan",
    // "Plot":"A man blinded by toxic waste which also enhanced his remaining senses fights crime as an acrobatic martial arts superhero.",
    // "Language":"English, Greek, Italian","Country":"USA","Awards":"5 wins & 16 nominations.",
    // "Poster":"https://images-na.ssl-images-amazon.com/images/M/MV5BMjYwZDNhMTgtNjEwNS00Y2Y0LTkxYjMtY2MyYTM0NDE1N2ZlXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg",
    // "Metascore":"42","imdbRating":"5.3","imdbVotes":"179,536","imdbID":"tt0287978","Type":"movie","Response":"True"}

    //TV SHOW
    //{"Title":"Stranger Things","Year":"2016–","Rated":"TV-14","Released":"15 Jul 2016",
    // "Runtime":"55 min","Genre":"Drama, Fantasy, Horror","Director":"N/A",
    // "Writer":"Matt Duffer, Ross Duffer","Actors":"Winona Ryder, David Harbour, Finn Wolfhard, Millie Bobby Brown",
    // "Plot":"When a young boy disappears, his mother, a police chief, and his friends must confront terrifying forces in order to get him back.",
    // "Language":"English","Country":"USA","Awards":"Nominated for 2 Golden Globes. Another 2 wins & 17 nominations.",
    // "Poster":"https://images-na.ssl-images-amazon.com/images/M/MV5BMjEzMDAxOTUyMV5BMl5BanBnXkFtZTgwNzAxMzYzOTE@._V1_SX300.jpg",
    // "Metascore":"N/A","imdbRating":"9.0","imdbVotes":"239,544","imdbID":"tt4574334","Type":"series","totalSeasons":"2","Response":"True"}

    Boolean isShow;
    String mediaID;
    String mediaTitle;
    String mediaYear;
    String mediaRating;
    String mediaReleased;
    String mediaRuntime;
    String mediaGenre;
    String mediaDirector;
    String mediaActors;
    String mediaPlot;
    String mediaPoster;
    String mediaIMDBScore;

    public Media(Boolean isShow, String mediaID, String mediaTitle, String mediaYear, String mediaRating, String mediaReleased, String mediaRuntime,
                 String mediaGenre, String mediaDirector, String mediaActors, String mediaPlot, String mediaPoster, String mediaIMDBScore) {
        this.isShow = isShow;
        this.mediaID = mediaID;
        this.mediaTitle = mediaTitle;
        this.mediaYear = mediaYear;
        this.mediaRating = mediaRating;
        this.mediaReleased = mediaReleased;
        this.mediaRuntime = mediaRuntime;
        this.mediaGenre = mediaGenre;
        this.mediaDirector = mediaDirector;
        this.mediaActors = mediaActors;
        this.mediaPlot = mediaPlot;
        this.mediaPoster = mediaPoster;
        this.mediaIMDBScore = mediaIMDBScore;
    }

    public Boolean getShow() {
        return isShow;
    }

    public void setShow(Boolean show) {
        isShow = show;
    }

    public String getMediaID() {
        return mediaID;
    }

    public void setMediaID(String mediaID) {
        this.mediaID = mediaID;
    }

    public String getMediaTitle() {
        return mediaTitle;
    }

    public void setMediaTitle(String mediaTitle) {
        this.mediaTitle = mediaTitle;
    }

    public String getMediaYear() {
        return mediaYear;
    }

    public void setMediaYear(String mediaYear) {
        this.mediaYear = mediaYear;
    }

    public String getMediaRating() {
        return mediaRating;
    }

    public void setMediaRating(String mediaRating) {
        this.mediaRating = mediaRating;
    }

    public String getMediaReleased() {
        return mediaReleased;
    }

    public void setMediaReleased(String mediaReleased) {
        this.mediaReleased = mediaReleased;
    }

    public String getMediaRuntime() {
        return mediaRuntime;
    }

    public void setMediaRuntime(String mediaRuntime) {
        this.mediaRuntime = mediaRuntime;
    }

    public String getMediaGenre() {
        return mediaGenre;
    }

    public void setMediaGenre(String mediaGenre) {
        this.mediaGenre = mediaGenre;
    }

    public String getMediaDirector() {
        return mediaDirector;
    }

    public void setMediaDirector(String mediaDirector) {
        this.mediaDirector = mediaDirector;
    }

    public String getMediaActors() {
        return mediaActors;
    }

    public void setMediaActors(String mediaActors) {
        this.mediaActors = mediaActors;
    }

    public String getMediaPlot() {
        return mediaPlot;
    }

    public void setMediaPlot(String mediaPlot) {
        this.mediaPlot = mediaPlot;
    }

    public String getMediaPoster() {
        return mediaPoster;
    }

    public void setMediaPoster(String mediaPoster) {
        this.mediaPoster = mediaPoster;
    }

    public String getMediaIMDBScore() {
        return mediaIMDBScore;
    }

    public void setMediaIMDBScore(String mediaIMDBScore) {
        this.mediaIMDBScore = mediaIMDBScore;
    }
}
