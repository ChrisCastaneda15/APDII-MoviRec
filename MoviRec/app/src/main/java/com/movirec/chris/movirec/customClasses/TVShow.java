package com.movirec.chris.movirec.customClasses;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class TVShow extends Media implements Serializable{

    String tvSeasons;
    String tvStatus;
    ArrayList<TVEpisode> tvEpisodes;


    public TVShow(Boolean isShow, String mediaID, String mediaTitle, String mediaYear, String mediaRating, String mediaReleased, String mediaRuntime,
                  String mediaGenre, String mediaDirector, String mediaActors, String mediaPlot, String mediaPoster, String mediaIMDBScore, Date date , String tvSeasons,
                  ArrayList<TVEpisode> tvEpisodes, String tvStatus) {
        super(isShow, mediaID, mediaTitle, mediaYear, mediaRating, mediaReleased, mediaRuntime, mediaGenre, mediaDirector, mediaActors, mediaPlot,
                mediaPoster, mediaIMDBScore, date);
        this.tvSeasons = tvSeasons;
        this.tvEpisodes = tvEpisodes;
        this.tvStatus = tvStatus;
    }

    public String getTvSeasons() {
        return tvSeasons;
    }

    public void setTvSeasons(String tvSeasons) {
        this.tvSeasons = tvSeasons;
    }

    public ArrayList<TVEpisode> getTvEpisodes() {
        return tvEpisodes;
    }

    public void setTvEpisodes(ArrayList<TVEpisode> tvEpisodes) {
        this.tvEpisodes = tvEpisodes;
    }

    public String getTvStatus() {
        return tvStatus;
    }

    public void setTvStatus(String tvStatus) {
        this.tvStatus = tvStatus;
    }
}
