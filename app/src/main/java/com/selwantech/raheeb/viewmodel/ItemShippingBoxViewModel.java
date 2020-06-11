
package com.selwantech.raheeb.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.databinding.BaseObservable;

import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.BoxSize;


public class ItemShippingBoxViewModel extends BaseObservable {

    private final Context context;
    RecyclerClick mRecyclerClick;
    private BoxSize boxSize;
    private int position;

    public ItemShippingBoxViewModel(Context context, BoxSize boxSize, int position, RecyclerClick mRecyclerClick) {
        this.context = context;
        this.boxSize = boxSize;
        this.position = position;
        this.mRecyclerClick = mRecyclerClick;
    }

    public BoxSize getBoxSize() {
        return boxSize;
    }

    public void setBoxSize(BoxSize boxSize) {
        this.boxSize = boxSize;
        notifyChange();
    }

    public void onItemClick(View view) {
        mRecyclerClick.onClick(boxSize, position);
    }

}
