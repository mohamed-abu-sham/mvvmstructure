package com.selwantech.raheeb.model;

import java.io.Serializable;

public class ValidateItem implements Serializable {

    int image;
    int color;
    int text;
    boolean isValid;

    public ValidateItem(int image, int color, int text, boolean isValid) {
        this.image = image;
        this.color = color;
        this.text = text;
        this.isValid = isValid;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getText() {
        return text;
    }

    public void setText(int text) {
        this.text = text;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
