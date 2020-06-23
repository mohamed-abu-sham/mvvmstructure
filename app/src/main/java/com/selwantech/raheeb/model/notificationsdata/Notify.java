package com.selwantech.raheeb.model.notificationsdata;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Notify implements Serializable {

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