package com.movirec.chris.movirec.customClasses;

import java.io.Serializable;

public class TVEpisode implements Serializable {

    String episodeTitle;
    int episodeSeason;
    int episodeNumber;
    String episodeImageURL;
    String episodeSummary;

    public TVEpisode(String episodeTitle, int episodeSeason, int episodeNumber, String episodeImageURL, String episodeSummary) {
        this.episodeTitle = episodeTitle;
        this.episodeSeason = episodeSeason;
        this.episodeNumber = episodeNumber;
        this.episodeImageURL = episodeImageURL;
        this.episodeSummary = episodeSummary;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public void setEpisodeTitle(String episodeTitle) {
        this.episodeTitle = episodeTitle;
    }

    public int getEpisodeSeason() {
        return episodeSeason;
    }

    public void setEpisodeSeason(int episodeSeason) {
        this.episodeSeason = episodeSeason;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getEpisodeImageURL() {
        return episodeImageURL;
    }

    public void setEpisodeImageURL(String episodeImageURL) {
        this.episodeImageURL = episodeImageURL;
    }

    public String getEpisodeSummary() {
        return episodeSummary;
    }

    public void setEpisodeSummary(String episodeSummary) {
        this.episodeSummary = episodeSummary;
    }
}
