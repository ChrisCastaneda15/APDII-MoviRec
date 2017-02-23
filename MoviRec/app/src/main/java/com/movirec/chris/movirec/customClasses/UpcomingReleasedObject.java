package com.movirec.chris.movirec.customClasses;


import java.io.Serializable;
import java.util.ArrayList;

public class UpcomingReleasedObject implements Serializable{
    String type;
    ArrayList<Movie> movies;

    public UpcomingReleasedObject(String type, ArrayList<Movie> movies) {
        this.type = type;
        this.movies = movies;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
