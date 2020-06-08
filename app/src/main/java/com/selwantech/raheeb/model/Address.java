package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Address implements Serializable {

    @SerializedName("name")
    String name;
    @SerializedName("lat")
    String latitude;
    @SerializedName("lon")
    String longitude;
    @SerializedName("floor")
    String floor;
    @SerializedName("building_number")
    String building;


    public Address(String name, String latitude, String longitude, String floor, String building) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.floor = floor;
        this.building = building;
    }

    public String getName() {
        return name;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getFloor() {
        return floor;
    }

    public String getBuilding() {
        return building;
    }
}
