package com.selwantech.raheeb.model;

import android.view.View;

import com.google.gson.annotations.SerializedName;
import com.selwantech.raheeb.utils.AppConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Selling implements Serializable {

    @SerializedName("date")
    private String date;

    @SerializedName("shipping_price")
    private Price shippingPrice;

    @SerializedName("images")
    private List<ImagesItem> images;

    @SerializedName("category_box_size")
    private CategoryBoxSize categoryBoxSize;

    @SerializedName("description")
    private String description;

    @SerializedName("lon")
    private double lon;

    @SerializedName("title")
    private String title;

    @SerializedName("condition_id")
    private int conditionId;

    @SerializedName("is_ship_nationwide")
    private boolean isShipNationwide;

    @SerializedName("chat_id")
    private int chatId;

    @SerializedName("condition")
    private Condition condition;

    @SerializedName("category_id")
    private int categoryId;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("price")
    private Price price;

    @SerializedName("is_faverate")
    private boolean isFaverate;

    @SerializedName("id")
    private int id;

    @SerializedName("user")
    private ProductOwner productOwner;

    @SerializedName("lat")
    private double lat;

    @SerializedName("status")
    private String status;

    @SerializedName("interacted_people")
    private ArrayList<String> interacted_people;


    public String getDate() {
        return date;
    }

    public Price getShippingPrice() {
        return shippingPrice;
    }

    public List<ImagesItem> getImages() {
        return images;
    }

    public CategoryBoxSize getCategoryBoxSize() {
        return categoryBoxSize;
    }

    public String getDescription() {
        return description;
    }

    public double getLon() {
        return lon;
    }

    public String getTitle() {
        return title;
    }

    public int getConditionId() {
        return conditionId;
    }

    public boolean isIsShipNationwide() {
        return isShipNationwide;
    }

    public int getChatId() {
        return chatId;
    }

    public Condition getCondition() {
        return condition;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getUserId() {
        return userId;
    }

    public Price getPrice() {
        return price;
    }

    public boolean isIsFaverate() {
        return isFaverate;
    }

    public int getId() {
        return id;
    }

    public ProductOwner getProductOwner() {
        return productOwner;
    }

    public double getLat() {
        return lat;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<String> getInteracted_people() {
        return interacted_people;
    }

    public void setInteracted_people(ArrayList<String> interacted_people) {
        this.interacted_people = interacted_people;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int canShare(){
        return getStatus().equals(AppConstants.PRODUCT_STATUS.AVAILABLE) ? View.VISIBLE : View.GONE;
    }
}