package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rate implements Serializable {

    @SerializedName("rate")
    int rating;
    @SerializedName("commint")
    String commint;

    public Rate(int rating, String commint) {
        this.rating = rating;
        this.commint = commint;
    }

    public String getCommint() {
        return commint;
    }

    public void setCommint(String commint) {
        this.commint = commint;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}