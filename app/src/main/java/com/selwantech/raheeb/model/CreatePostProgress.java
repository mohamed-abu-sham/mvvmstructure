package com.selwantech.raheeb.model;

import androidx.databinding.BaseObservable;

import com.selwantech.raheeb.App;
import com.selwantech.raheeb.R;

import java.io.Serializable;

public class CreatePostProgress extends BaseObservable implements Serializable {

    //    private static CreatePostProgress createPostProgress = null;
    boolean addPhotos = true;
    boolean addDetails = false;
    boolean addPrice = false;
    boolean finish = false;

//    private CreatePostProgress() {
//
//    }

//    public static CreatePostProgress getInstance() {
//        if (createPostProgress == null)
//            createPostProgress = new CreatePostProgress();
//        return createPostProgress;
//    }
//
//    public void setObj(CreatePostProgress createPostProgress) {
//        this.createPostProgress = createPostProgress;
//    }

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