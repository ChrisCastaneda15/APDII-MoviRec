package com.movirec.chris.movirec.customClasses;

import com.movirec.chris.movirec.customClasses.Media;

import java.io.Serializable;

public class Movie extends Media implements Serializable{

    public Movie(Boolean isShow, String mediaID, String mediaTitle, String mediaYear, String mediaRating, String mediaReleased, String mediaRuntime,
                 String mediaGenre, String mediaDirector, String mediaActors, String mediaPlot, String mediaPoster, String mediaIMDBScore) {
        super(isShow, mediaID, mediaTitle, mediaYear, mediaRating, mediaReleased, mediaRuntime, mediaGenre, mediaDirector, mediaActors, mediaPlot, mediaPoster,
                mediaIMDBScore);
    }
}
