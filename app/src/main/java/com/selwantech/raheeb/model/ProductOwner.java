package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;

public class ProductOwner {

    @SerializedName("rate")
    private double rate;

    @SerializedName("is_valid")
    private boolean isValid;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("avatar")
    private Object avatar;

    public double getRate() {
        return rate;
    }

    public boolean isIsValid() {
        return isValid;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Object getAvatar() {
        return avatar;
    }
}