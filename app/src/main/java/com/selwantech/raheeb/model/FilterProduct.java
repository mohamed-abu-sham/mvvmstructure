package com.selwantech.raheeb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FilterProduct implements Serializable {

    private static FilterProduct filterProduct = null;
    @Expose
    @SerializedName("lat")
    double lat = 0.0;
    @Expose
    @SerializedName("lon")
    double lon = 0.0;
    @Expose
    @SerializedName("price_min")
    double price_min = 0.0;
    @Expose
    @SerializedName("price_max")
    double price_max = 0.0;
    @Expose
    @SerializedName("shipping")
    boolean shipping = false;
    @Expose
    @SerializedName("pick_up")
    boolean pick_up = false;
    @Expose
    @SerializedName("category_id")
    int category_id = 0;
    @Expose
    @SerializedName("distance")
    int distance = 0;
    @Expose
    @SerializedName("title")
    String title;
    @Expose
    @SerializedName("ordering")
    String ordering;

    private FilterProduct() {

    }

    public static FilterProduct getInstance() {
        if (filterProduct == null)
            filterProduct = new FilterProduct();
        return filterProduct;
    }

    public static FilterProduct getObj() {
        return filterProduct;
    }

    public void setObj(FilterProduct filterProduct) {

        FilterProduct.filterProduct = filterProduct;
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

    public double getPrice_min() {
        return price_min;
    }

    public void setPrice_min(double price_min) {
        this.price_min = price_min;
    }

    public double getPrice_max() {
        return price_max;
    }

    public void setPrice_max(double price_max) {
        this.price_max = price_max;
    }

    public boolean isShipping() {
        return shipping;
    }

    public void setShipping(boolean shipping) {
        this.shipping = shipping;
    }

    public boolean isPick_up() {
        return pick_up;
    }

    public void setPick_up(boolean pick_up) {
        this.pick_up = pick_up;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrdering() {
        return ordering;
    }

    public void setOrdering(String ordering) {
        this.ordering = ordering;
    }
}
