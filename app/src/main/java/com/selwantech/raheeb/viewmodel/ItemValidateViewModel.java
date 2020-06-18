
package com.selwantech.raheeb.viewmodel;

import android.content.Context;
import android.view.View;

import com.selwantech.raheeb.model.ValidateItem;

import androidx.databinding.BaseObservable;


public class ItemValidateViewModel extends BaseObservable {

    private final Context context;
    private ValidateItem validateItem;
    private int position;

    public ItemValidateViewModel(Context context, ValidateItem validateItem, int position) {
        this.context = context;
        this.validateItem = validateItem;
        this.position = position;
    }

    public ValidateItem getValidateItem() {
        return validateItem;
    }

    public void setValidateItem(ValidateItem validateItem) {
        this.validateItem = validateItem;
        notifyChange();
    }


    public void onItemClick(View view) {

    }
}
