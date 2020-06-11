package com.selwantech.raheeb.model.notificationsdata;

import com.google.gson.annotations.SerializedName;

public class Notify {

    @SerializedName("title")
    private String title;

    @SerializedName("body")
    private String body;

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}