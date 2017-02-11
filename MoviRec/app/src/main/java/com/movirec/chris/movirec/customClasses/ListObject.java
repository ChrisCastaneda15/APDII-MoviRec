package com.movirec.chris.movirec.customClasses;


import java.util.ArrayList;
import java.util.Date;

public class ListObject {
    String listTitle;
    Date listCreateDate;
    int listSize;
    ArrayList<Media> listMedia;

    public ListObject(String listTitle, Date listCreateDate, ArrayList<Media> listMedia) {
        this.listTitle = listTitle;
        this.listCreateDate = listCreateDate;
        this.listMedia = listMedia;
        this.listSize = listMedia.size();
    }

    public String getListTitle() {
        return listTitle;
    }

    public void setListTitle(String listTitle) {
        this.listTitle = listTitle;
    }

    public Date getListCreateDate() {
        return listCreateDate;
    }

    public void setListCreateDate(Date listCreateDate) {
        this.listCreateDate = listCreateDate;
    }

    public int getListSize() {
        return listSize;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }

    public ArrayList<Media> getListMedia() {
        return listMedia;
    }

    public void setListMedia(ArrayList<Media> listMedia) {
        this.listMedia = listMedia;
    }
}
