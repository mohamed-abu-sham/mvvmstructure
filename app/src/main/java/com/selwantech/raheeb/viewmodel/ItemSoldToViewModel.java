
package com.selwantech.raheeb.viewmodel;

import android.content.Context;

import androidx.databinding.BaseObservable;

import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.SoldTo;


public class ItemSoldToViewModel extends BaseObservable {

    private final Context context;
    RecyclerClick mRecyclerClick;
    private SoldTo soldTo;
    private int position;

    public ItemSoldToViewModel(Context context, SoldTo soldTo, int position, RecyclerClick mRecyclerClick) {
        this.context = context;
        this.soldTo = soldTo;
        this.position = position;
        this.mRecyclerClick = mRecyclerClick;
    }

    public SoldTo getSoldTo() {
        return soldTo;
    }

    public void setSoldTo(SoldTo soldTo) {
        this.soldTo = soldTo;
        notifyChange();
    }


}
