package com.movirec.chris.movirec.customClasses;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class TVShow extends Media implements Serializable{

    String tvSeasons;
    String tvStatus;
    ArrayList<TVEpisode> tvEpisodes;


    public TVShow(Media media, String tvSeasons,
                  ArrayList<TVEpisode> tvEpisodes, String tvStatus) {
        super(media.isShow, media.getMediaID(), media.getMediaTitle(), media.getMediaYear(), media.getMediaRating(), media.getMediaReleased(),
                media.getMediaRuntime(), media.getMediaGenre(), media.getMediaDirector(), media.getMediaActors(), media.getMediaPlot(), media.getMediaPoster(),
                media.getMediaIMDBScore(), media.getDateAdded());
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
