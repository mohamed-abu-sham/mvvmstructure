package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GeoAddress implements Serializable {

    @SerializedName("id")
    int id;
    @SerializedName("city")
    String City = "";
    @SerializedName("geocoding")
    String geocoding = "";
    @SerializedName("country")
    String Country = "";
    @SerializedName("PostalCode")
    String PostalCode = "";
    @SerializedName("KnownName")
    String KnownName = "";
    @SerializedName("latitude")
    double latitude;
    @SerializedName("longitude")
    double longitude;

    @SerializedName("nickname")
    String nickname = "";
    @SerializedName("state")
    boolean isDefualt = false;

    public GeoAddress(String City, String State, String Country,
                      String PostalCode, String KnownName, double latitude, double longitude) {
        this.City = City;
        this.geocoding = State;
        this.Country = Country;
        this.PostalCode = PostalCode;
        this.KnownName = KnownName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getGeocoding() {
        return geocoding;
    }

    public void setGeocoding(String geocoding) {
        this.geocoding = geocoding;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getKnownName() {
        return KnownName;
    }

    public void setKnownName(String knownName) {
        KnownName = knownName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isDefualt() {
        return isDefualt;
    }

    public void setDefualt(boolean defualt) {
        isDefualt = defualt;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAddress() {
        StringBuilder addressStr = new StringBuilder();
        addressStr.append((getCountry() != null ? getCountry() : "unnamedCountry") + ",");
        addressStr.append((getCity() != null ? getCity() : "unnamedCity") + ",");
        addressStr.append(getGeocoding() != null ? getGeocoding() : "unnamedRoad");
        return addressStr.toString();
    }
}
