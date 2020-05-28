
package com.selwantech.raheeb.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.databinding.BaseObservable;

import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.Product;


public class ItemProductViewModel extends BaseObservable {

    private final Context context;
    RecyclerClick mRecyclerClick;
    private Product product;
    private int position;

    public ItemProductViewModel(Context context, Product product, int position, RecyclerClick mRecyclerClick) {
        this.context = context;
        this.product = product;
        this.position = position;
        this.mRecyclerClick = mRecyclerClick;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        notifyChange();
    }

    public void onItemClick(View view) {
        mRecyclerClick.onClick(product, position);
    }

    public int isShipping() {
        return product.isIsShipNationwide() ?
                View.VISIBLE : View.GONE;
    }
}
