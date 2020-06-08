package com.selwantech.raheeb.model;

import androidx.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;
import com.selwantech.raheeb.App;
import com.selwantech.raheeb.R;

import java.io.Serializable;
import java.util.List;

public class Product extends BaseObservable implements Serializable {

    @SerializedName("date")
    private String date;

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

    public String getDate() {
        return date;
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

    public void setFaverate(boolean faverate) {
        isFaverate = faverate;
        notifyChange();
    }

    public String isFavoriteText() {
        return !isIsFaverate() ?
                App.getInstance().getApplicationContext().getResources().getString(R.string.favorite) :
                App.getInstance().getApplicationContext().getResources().getString(R.string.unfavorite);
    }

    public int isFavoriteDrawable() {
        return !isIsFaverate() ?
                R.drawable.ic_favorite_white :
                R.drawable.ic_favorite;
    }
}