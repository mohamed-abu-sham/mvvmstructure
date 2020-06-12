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
}