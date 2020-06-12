package com.selwantech.raheeb.model.chatdata;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("avatar")
    private String avatar;

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