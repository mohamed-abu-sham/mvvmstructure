package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;

public class ImagesItem {

    @SerializedName("image")
    private String image;

    @SerializedName("id")
    private int id;

    public String getImage() {
        return image;
    }

    public int getId() {
        return id;
    }
}