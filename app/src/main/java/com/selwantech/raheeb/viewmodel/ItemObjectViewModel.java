
package com.selwantech.raheeb.viewmodel;

import android.content.Context;
import android.view.View;

import com.selwantech.raheeb.interfaces.RecyclerClick;
import androidx.databinding.BaseObservable;

public class ItemObjectViewModel extends BaseObservable {

    private final Context context;
    RecyclerClick mRecyclerClick;
    private Object object;
    private int position;

    public ItemObjectViewModel(Context context, Object object, int position, RecyclerClick mRecyclerClick) {
        this.context = context;
        this.object = object;
        this.position = position;
        this.mRecyclerClick = mRecyclerClick;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
        notifyChange();
    }

    public void onItemClick(View view) {
        mRecyclerClick.onClick(object, position);
    }

}
