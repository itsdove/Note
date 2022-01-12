package com.example.myapplication.noteall;


import org.litepal.crud.LitePalSupport;

public class Note extends LitePalSupport {
    public String Title;
    int id;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

}
