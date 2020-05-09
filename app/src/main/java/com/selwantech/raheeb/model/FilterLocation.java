package com.selwantech.raheeb.model;

import java.io.Serializable;

public class FilterLocation implements Serializable {

    String locationName ;
    double lat ;
    double lon ;
    int km ;

    public FilterLocation(String locationName, double lat, double lon, int km) {
        this.locationName = locationName;
        this.lat = lat;
        this.lon = lon;
        this.km = km;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }
}
