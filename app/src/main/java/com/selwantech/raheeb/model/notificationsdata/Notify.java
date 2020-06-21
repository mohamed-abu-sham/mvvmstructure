package com.selwantech.raheeb.model.notificationsdata;

import com.google.gson.annotations.SerializedName;

public class Notify {

    @SerializedName("title")
    private String title;

    @SerializedName("body")
    private String body;

    @SerializedName("date")
    private String date = "1 day";


    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }
}