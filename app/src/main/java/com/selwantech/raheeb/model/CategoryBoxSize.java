package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;

public class CategoryBoxSize {

    @SerializedName("image")
    private String image;

    @SerializedName("unit")
    private String unit;

    @SerializedName("size")
    private int size;

    @SerializedName("price")
    private Price price;

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

    public Price getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }
}