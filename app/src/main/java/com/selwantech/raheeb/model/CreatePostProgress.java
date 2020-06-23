package com.selwantech.raheeb.model;

import com.selwantech.raheeb.App;
import com.selwantech.raheeb.R;

import java.io.Serializable;

import androidx.databinding.BaseObservable;

public class CreatePostProgress extends BaseObservable implements Serializable {

    boolean addPhotos = true;
    boolean addDetails = false;
    boolean addPrice = false;
    boolean finish = false;

    public int isAddPhotos() {
        return getColorTint(addPhotos);
    }

    public void setAddPhotos(boolean addPhotos) {
        this.addPhotos = addPhotos;
        notifyChange();
    }

    public int isAddDetails() {
        return getColorTint(addDetails);
    }

    public void setAddDetails(boolean addDetails) {
        this.addDetails = addDetails;
        notifyChange();
    }

    public int isAddPrice() {
        return getColorTint(addPrice);
    }

    public void setAddPrice(boolean addPrice) {
        this.addPrice = addPrice;
        notifyChange();
    }

    public int isFinish() {
        return getColorTint(finish);
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
        notifyChange();
    }

    private int getColorTint(boolean isActive) {
        return isActive ? App.getInstance().getApplicationContext().getResources().getColor(R.color.color_blue)
                : App.getInstance().getApplicationContext().getResources().getColor(R.color.text_gray);
    }
}