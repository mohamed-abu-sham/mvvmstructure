package com.selwantech.raheeb.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Slider implements Serializable {
    @SerializedName("id")
    private int id = -1;
    @SerializedName("image")
    private String image = "";
    @SerializedName("url")
    private String url = "";


    public String getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    @NonNull
    @Override
    public String toString() {
        return getUrl();
    }


}
