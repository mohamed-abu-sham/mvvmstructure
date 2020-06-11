package com.selwantech.raheeb.model.chatdata;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Chat implements Serializable {

    @SerializedName("date")
    private String date;

    @SerializedName("post")
    private Post post;

    @SerializedName("id")
    private int id;

    @SerializedName("user")
    private User user;

    public String getDate() {
        return date;
    }

    public Post getPost() {
        return post;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
}