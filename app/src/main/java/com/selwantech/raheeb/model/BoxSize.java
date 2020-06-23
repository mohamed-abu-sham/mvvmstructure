package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BoxSize implements Serializable {

    @SerializedName("price")
    Price price;
    boolean selected = false;
    @SerializedName("image")
    private String image;
    @SerializedName("unit")
    private String unit;
    @SerializedName("size")
    private int size;
    @SerializedName("id")
    private int id;

    public String getImage() {
        return image;
    }

    public String getUnit() {
        return unit;
    }

    public int getSize() {
        return size;
    }

    public int getId() {
        return id;
    }

    public Price getPrice() {
        return price;
    }

    public String getName() {
        return size + " " + unit;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}