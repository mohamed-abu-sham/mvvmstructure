
package com.selwantech.raheeb.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.databinding.BaseObservable;

import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.FilterBy;


public class ItemFilterByViewModel extends BaseObservable {

    private final Context context;
    RecyclerClick mRecyclerClick;
    private FilterBy filterBy;
    private int position;

    public ItemFilterByViewModel(Context context, FilterBy filterBy, int position, RecyclerClick mRecyclerClick) {
        this.context = context;
        this.filterBy = filterBy;
        this.position = position;
        this.mRecyclerClick = mRecyclerClick;
    }

    public FilterBy getFilterBy() {
        return filterBy;
    }

    public void setFilterBy(FilterBy filterBy) {
        this.filterBy = filterBy;
        notifyChange();
    }

    public void onCancelClick(View view) {
        mRecyclerClick.onClick(filterBy, position);
    }

}
