
package com.selwantech.raheeb.viewmodel;

import android.content.Context;
import android.view.View;

import com.selwantech.raheeb.interfaces.RecyclerClickNoData;

import androidx.databinding.BaseObservable;


public class ItemSettingViewModel extends BaseObservable {

    private final Context context;
    RecyclerClickNoData recyclerClick;
    private String text;
    private int position;

    public ItemSettingViewModel(Context context, String text, int position, RecyclerClickNoData recyclerClick) {
        this.context = context;
        this.text = text;
        this.position = position;
        this.recyclerClick = recyclerClick;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        notifyChange();
    }

    public void onItemClick(View view) {
        recyclerClick.onClick(position);
    }
}
