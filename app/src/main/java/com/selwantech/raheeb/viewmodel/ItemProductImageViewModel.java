
package com.selwantech.raheeb.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.databinding.BaseObservable;

import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.ImagesItem;


public class ItemProductImageViewModel extends BaseObservable {

    private final Context context;
    RecyclerClick mRecyclerClick;
    private ImagesItem imagesItem;
    private int position;

    public ItemProductImageViewModel(Context context, ImagesItem imagesItem, int position, RecyclerClick mRecyclerClick) {
        this.context = context;
        this.imagesItem = imagesItem;
        this.position = position;
        this.mRecyclerClick = mRecyclerClick;
    }

    public ImagesItem getImagesItem() {
        return imagesItem;
    }

    public void setImagesItem(ImagesItem imagesItem) {
        this.imagesItem = imagesItem;
        notifyChange();
    }

    public void onItemClick(View view) {
        mRecyclerClick.onClick(imagesItem, position);
    }

}
