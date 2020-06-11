
package com.selwantech.raheeb.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.databinding.BaseObservable;

import com.selwantech.raheeb.enums.SellingItemClickTypes;
import com.selwantech.raheeb.interfaces.SellingItemClick;
import com.selwantech.raheeb.model.Selling;


public class ItemSellingViewModel extends BaseObservable {

    private final Context context;
    SellingItemClick sellingItemClick;
    private Selling selling;
    private int position;

    public ItemSellingViewModel(Context context, Selling selling, int position, SellingItemClick sellingItemClick) {
        this.context = context;
        this.selling = selling;
        this.position = position;
        this.sellingItemClick = sellingItemClick;
    }

    public Selling getSelling() {
        return selling;
    }

    public void setSelling(Selling selling) {
        this.selling = selling;
        notifyChange();
    }

    public void onItemClick(View view) {
        sellingItemClick.onClick(selling, SellingItemClickTypes.ITEM_CLICK.getTypeID(), position);
    }

    public void onMarkSoldClick(View view) {
        sellingItemClick.onClick(selling, SellingItemClickTypes.MARK_SOLD_CLICK.getTypeID(), position);
    }

    public void onSellFasterClick(View view) {
        sellingItemClick.onClick(selling, SellingItemClickTypes.SELL_FASTER_CLICK.getTypeID(), position);
    }
}
