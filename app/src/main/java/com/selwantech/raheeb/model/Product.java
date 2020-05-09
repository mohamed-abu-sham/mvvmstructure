package com.selwantech.raheeb.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class Product implements Serializable {

    int image ;

    public Product(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
