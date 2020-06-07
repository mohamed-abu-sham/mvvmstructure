package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImagesItem implements Serializable {

    @SerializedName("image")
    private String image;

    @SerializedName("id")
    private int id;

    boolean selected = false;

    public String getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}