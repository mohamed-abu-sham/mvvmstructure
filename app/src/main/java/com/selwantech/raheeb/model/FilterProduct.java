package com.selwantech.raheeb.model;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;
import com.selwantech.raheeb.App;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.helper.GeoCoderAddress;
import com.selwantech.raheeb.utils.AppConstants;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class FilterProduct implements Serializable {

    private static FilterProduct filterProduct = null;
    @SerializedName("lat")
    double lat = 0.0;
    @SerializedName("lon")
    double lon = 0.0;
    @SerializedName("price_min")
    double price_min = 0.0;
    @SerializedName("price_max")
    double price_max = 0.0;
    @SerializedName("shipping")
    boolean shipping = false;
    @SerializedName("pick_up")
    boolean pick_up = false;
    @SerializedName("category_id")
    Category category = null;
    @SerializedName("distance")
    int distance = 0;
    @SerializedName("title")
    String title;
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

    public void clearData() {
        boolean shipping = isShipping();
        boolean pickup = isPick_up();
        setObj(null);
        getInstance();
        getInstance().setShipping(shipping);
        getInstance().setPick_up(pick_up);
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
        return category != null ? category.getId() : 0;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    public ArrayList<FilterBy> getArraylist() {
        Context mContext = App.getInstance();
        ArrayList<FilterBy> filterByArrayList = new ArrayList<>();
        if (getLat() != 0.0 && getLon() != 0.0) {
            try {
                filterByArrayList.add(new FilterBy(AppConstants.FILTER_BY_KEYS.LOCATION,
                        mContext.getResources().getString(R.string.location),
                        GeoCoderAddress.getInstance().getAddress(new LatLng(getLat(), getLon())).getAddress()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (getPrice_min() != 0.0 && getPrice_max() != 0.0) {
            filterByArrayList.add(new FilterBy(AppConstants.FILTER_BY_KEYS.PRICE,
                    mContext.getResources().getString(R.string.price),
                    getPrice_min() + " " +
                            mContext.getResources().getString(R.string.between) + " " +
                            getPrice_max()));
        }

        if (getDistance() != 0) {
            filterByArrayList.add(new FilterBy(AppConstants.FILTER_BY_KEYS.DISTANCE,
                    mContext.getResources().getString(R.string.distance),
                    getDistance() + mContext.getResources().getString(R.string.km)));

        }

        if (getTitle() != null
                && !getTitle().isEmpty()) {
            filterByArrayList.add(new FilterBy(AppConstants.FILTER_BY_KEYS.DISTANCE,
                    mContext.getResources().getString(R.string.search),
                    getTitle()));
        }

        if (getOrdering() != null
                && !getOrdering().isEmpty()) {
            filterByArrayList.add(new FilterBy(AppConstants.FILTER_BY_KEYS.ORDER_BY,
                    mContext.getResources().getString(R.string.order_by),
                    getOrdering()));
        }
        if (getCategory() != null) {
            filterByArrayList.add(new FilterBy(AppConstants.FILTER_BY_KEYS.CATEGORY,
                    mContext.getResources().getString(R.string.category),
                    getCategory().getName()));
        }
        return filterByArrayList;
    }
}
