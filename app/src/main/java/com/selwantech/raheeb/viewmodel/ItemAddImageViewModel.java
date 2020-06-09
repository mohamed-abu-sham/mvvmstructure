
package com.selwantech.raheeb.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.databinding.BaseObservable;

import com.selwantech.raheeb.interfaces.RecyclerClick;


public class ItemAddImageViewModel extends BaseObservable {

    private final Context context;
    RecyclerClick mRecyclerClick;
    private String imagesItem;
    private int position;

    public ItemAddImageViewModel(Context context, String imagesItem, int position, RecyclerClick mRecyclerClick) {
        this.context = context;
        this.imagesItem = imagesItem;
        this.position = position;
        this.mRecyclerClick = mRecyclerClick;
    }

    public String getImagesItem() {
        return imagesItem;
    }

    public void setImagesItem(String imagesItem) {
        this.imagesItem = imagesItem;
        notifyChange();
    }

    public void onItemClick(View view) {
        mRecyclerClick.onClick(imagesItem, position);
    }

}
