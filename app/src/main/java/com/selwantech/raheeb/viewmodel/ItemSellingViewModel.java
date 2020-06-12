
package com.selwantech.raheeb.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.databinding.BaseObservable;

import com.selwantech.raheeb.databinding.CellSellingBinding;
import com.selwantech.raheeb.enums.SellingItemClickTypes;
import com.selwantech.raheeb.helper.GeneralFunction;
import com.selwantech.raheeb.interfaces.SellingItemClick;
import com.selwantech.raheeb.model.Selling;
import com.selwantech.raheeb.utils.AppConstants;


public class ItemSellingViewModel extends BaseObservable {

    private final Context context;
    SellingItemClick sellingItemClick;
    private Selling selling;
    private int position;
    CellSellingBinding cellSellingBinding;

    public ItemSellingViewModel(CellSellingBinding cellSellingBinding, Context context, Selling selling, int position, SellingItemClick sellingItemClick) {
        this.cellSellingBinding = cellSellingBinding;
        this.context = context;
        this.selling = selling;
        this.position = position;
        this.sellingItemClick = sellingItemClick;
        setData();
    }

    public int viewMarkedSold() {
        return selling.getStatus().equals(AppConstants.PRODUCT_STATUS.AVAILABLE) ?
                View.VISIBLE : View.GONE;
    }

    public Selling getSelling() {
        return selling;
    }

    public void setSelling(Selling selling) {
        this.selling = selling;
        notifyChange();
        setData();
    }

    private void setData() {
        if (selling.getStatus().equals(AppConstants.PRODUCT_STATUS.AVAILABLE)
                && selling.getInteracted_people() != null &&
                selling.getInteracted_people().size() > 0) {
            GeneralFunction.loadImage(context, selling.getInteracted_people().get(0), cellSellingBinding.imgUser1);
            cellSellingBinding.imgUser1.setVisibility(View.VISIBLE);
            if (selling.getInteracted_people().size() > 1) {
                GeneralFunction.loadImage(context, selling.getInteracted_people().get(1), cellSellingBinding.imgUser2);
                cellSellingBinding.imgUser2.setVisibility(View.VISIBLE);
            }
            if (selling.getInteracted_people().size() > 2) {
                cellSellingBinding.tvMoreCount.setText("+ " + (selling.getInteracted_people().size() - 2));
                cellSellingBinding.linearMoreCount.setVisibility(View.VISIBLE);
            }
            cellSellingBinding.relativeInteracted.setVisibility(View.VISIBLE);
        }
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