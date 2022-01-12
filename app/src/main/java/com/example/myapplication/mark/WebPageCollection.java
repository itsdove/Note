package com.example.myapplication.mark;

import org.litepal.crud.LitePalSupport;

import java.util.Calendar;

public class WebPageCollection extends LitePalSupport{
    private String name;
    private String url;
    private Calendar time;
    WebPageCollection(){

    }
    WebPageCollection(String name,String url){
        this.name=name;
        this.url=url;
        this.time=Calendar.getInstance();
    }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public void setUrl(String url) { this.url = url; }
    public String getUrl() { return url; }

    public Calendar getTime() { return time; }
}
