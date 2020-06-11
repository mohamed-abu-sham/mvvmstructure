package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SetSold implements Serializable {

    @SerializedName("user_id")
    int userId = -1;
    @SerializedName("rate")
    int rate = -1;

    public SetSold(int userId, int rate) {
        this.userId = userId;
        this.rate = rate;
    }

    public SetSold() {

    }

    public int getUserId() {
        return userId;
    }

    public int getRate() {
        return rate;
    }
}
