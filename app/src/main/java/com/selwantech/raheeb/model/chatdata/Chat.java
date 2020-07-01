package com.selwantech.raheeb.model.chatdata;

import android.view.View;

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

    @SerializedName("is_seen")
    boolean is_seen;
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

    public int isIs_seen() {
        return is_seen ? View.GONE : View.VISIBLE;
    }

    public void setIs_seen(boolean is_seen) {
        this.is_seen = is_seen;
    }
}