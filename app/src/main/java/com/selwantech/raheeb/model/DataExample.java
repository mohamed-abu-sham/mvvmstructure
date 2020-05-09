package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataExample implements Serializable {

    @SerializedName("name")
    private String title;

    @SerializedName("image")
    private String image;

    @SerializedName("rate")
    private float rate = 0;


    public DataExample(String title, String image, float rate) {
        this.title = title;
        this.image = image;
        this.rate = rate;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
