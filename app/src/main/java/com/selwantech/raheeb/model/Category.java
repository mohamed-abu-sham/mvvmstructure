package com.selwantech.raheeb.model;

import android.view.View;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Category implements Serializable {

    @SerializedName("image")
    private String image;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("is_ship_nationwide")
    private boolean isShipNationwide;

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public boolean isIsShipNationwide() {
        return isShipNationwide;
    }

    public int isIsShipNationwideVisible() {
        return isShipNationwide ? View.VISIBLE : View.GONE;
    }
}