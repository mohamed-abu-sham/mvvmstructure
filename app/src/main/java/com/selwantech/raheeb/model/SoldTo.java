package com.selwantech.raheeb.model;

import androidx.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SoldTo extends BaseObservable implements Serializable {

    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("avatar")
    String avatar;

    boolean selected;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        notifyChange();
    }
}
