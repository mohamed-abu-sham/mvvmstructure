package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;

public class Condition {

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}