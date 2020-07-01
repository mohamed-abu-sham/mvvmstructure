package com.selwantech.raheeb.model.chatdata;

import com.google.gson.annotations.SerializedName;
import com.selwantech.raheeb.model.ImagesItem;
import com.selwantech.raheeb.model.Price;

import java.io.Serializable;
import java.util.List;

public class Post implements Serializable {

    @SerializedName("images")
    private List<ImagesItem> images;

    @SerializedName("price")
    private Price price;

    @SerializedName("id")
    private int id;

    @SerializedName("user_id")
    private int user_id;

    @SerializedName("title")
    private String title;

    @SerializedName("status")
    private String status;

    public List<ImagesItem> getImages() {
        return images;
    }

    public Price getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}