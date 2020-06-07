package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductOwner implements Serializable {

    @SerializedName("rate")
    private double rate;

    @SerializedName("is_valid")
    private boolean isValid;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("avatar")
    private String avatar;

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

    public String getAvatar() {
        return avatar;
    }
}