
package com.selwantech.raheeb.viewmodel;

import android.content.Context;
import android.view.View;

import com.selwantech.raheeb.interfaces.RecyclerClickNoData;
import com.selwantech.raheeb.model.ValidateItem;

import androidx.databinding.BaseObservable;


public class ItemValidateViewModel extends BaseObservable {

    private final Context context;
    private ValidateItem validateItem;
    private int position;

    RecyclerClickNoData recyclerClickNoData;

    public ItemValidateViewModel(Context context, ValidateItem validateItem, int position, RecyclerClickNoData recyclerClickNoData) {
        this.context = context;
        this.validateItem = validateItem;
        this.position = position;
        this.recyclerClickNoData = recyclerClickNoData;
    }

    public ValidateItem getValidateItem() {
        return validateItem;
    }

    public void setValidateItem(ValidateItem validateItem) {
        this.validateItem = validateItem;
        notifyChange();
    }


    public void onItemClick(View view) {
        if (!validateItem.isValid()) {
            recyclerClickNoData.onClick(position);
        }
    }

    public int isValid() {
        return !validateItem.isValid() ? View.VISIBLE : View.GONE;
    }
}
