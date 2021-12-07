package com.example.myapplication;

import org.litepal.crud.LitePalSupport;

public class Note extends LitePalSupport {
    String Title;
    String Address;
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
