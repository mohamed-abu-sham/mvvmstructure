package com.selwantech.raheeb.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Condition implements Serializable {

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

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}