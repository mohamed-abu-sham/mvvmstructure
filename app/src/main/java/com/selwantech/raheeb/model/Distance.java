package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class Distance implements Serializable {

    @SerializedName("unit")
    private String unit;

    @SerializedName("distance")
    private int distance;

    public String getUnit() {
        return unit;
    }

    public int getDistance() {
        return distance;
    }

    @NonNull
    @Override
    public String toString() {
        return distance + unit;
    }
}