package com.waracle.androidtest.model;

public class Cake {

    private final String title;
    private final String desc;
    private final String imageUrl;


    public Cake(String title, String desc, String imageUrl) {
        this.title = title;
        this.desc = desc;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
