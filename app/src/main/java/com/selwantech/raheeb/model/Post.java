package com.selwantech.raheeb.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

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

    @SerializedName("paths")
    ArrayList<String> imagesPaths;

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

    public ArrayList<String> getImagesPaths() {
        return imagesPaths;
    }

    public void setImagesPaths(ArrayList<String> imagesPaths) {
        this.imagesPaths = imagesPaths;
    }
}
