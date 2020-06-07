package com.selwantech.raheeb.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Distance {

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