package com.example.myapplication;

import org.litepal.crud.LitePalSupport;

public class RecordItem extends LitePalSupport {
    private String title;
    private int type,state;
    private String url;
    private String description;
    private float rating;
    public RecordItem(){
        this.type=-1;
        this.state=-1;
        this.rating=0;
    }

    public void setTitle(String title) { this.title = title; }
    public void setType(int type) { this.type = type; }
    public void setState(int state) { this.state = state; }
    public void setUrl(String url) { this.url = url; }
    public void setDescription(String description) { this.description = description; }
    public void setRating(float rating) { this.rating = rating; }

    public String getTitle(){return title;}
    public int getType() { return type; }
    public int getState() { return state; }
    public String getUrl() { return url; }
    public String getDescription() { return description; }
    public float getRating() { return rating; }

}
