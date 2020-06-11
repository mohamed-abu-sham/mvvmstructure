package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;
import com.selwantech.raheeb.helper.GeneralFunction;

import java.io.Serializable;
import java.util.ArrayList;

import okhttp3.MultipartBody;

public class Post implements Serializable {


    ArrayList<String> images;

    @SerializedName("title")
    String title;

    @SerializedName("condition_id")
    int conditionId;

    @SerializedName("description")
    String description;

    @SerializedName("category_id")
    int categoryId;

    @SerializedName("price")
    double price;

    @SerializedName("lat")
    double lat;

    @SerializedName("lon")
    double lon;

    @SerializedName("is_ship_nationwide")
    int is_ship_nationwide;

    @SerializedName("category_box_size_id")
    int category_box_size_id;


    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getConditionId() {
        return conditionId;
    }

    public void setConditionId(int conditionId) {
        this.conditionId = conditionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public int getIs_ship_nationwide() {
        return is_ship_nationwide;
    }

    public void setIs_ship_nationwide(int is_ship_nationwide) {
        this.is_ship_nationwide = is_ship_nationwide;
    }

    public int getCategory_box_size_id() {
        return category_box_size_id;
    }

    public void setCategory_box_size_id(int category_box_size_id) {
        this.category_box_size_id = category_box_size_id;
    }

    public ArrayList<MultipartBody.Part> getImagesMultypart() {
        ArrayList<MultipartBody.Part> partArrayList = new ArrayList<>();
        for (String image : getImages()) {
            partArrayList.add(GeneralFunction.getImageMultipart(image, "images[]"));
        }
        return partArrayList;
    }
}
