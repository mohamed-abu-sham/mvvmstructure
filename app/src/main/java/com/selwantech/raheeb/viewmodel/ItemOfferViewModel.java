
package com.selwantech.raheeb.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.databinding.BaseObservable;

import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.MyOffer;


public class ItemOfferViewModel extends BaseObservable {

    private final Context context;
    RecyclerClick mRecyclerClick;
    private MyOffer myOffer;
    private int position;

    public ItemOfferViewModel(Context context, MyOffer myOffer, int position, RecyclerClick mRecyclerClick) {
        this.context = context;
        this.myOffer = myOffer;
        this.position = position;
        this.mRecyclerClick = mRecyclerClick;
    }

    public MyOffer getMyOffer() {
        return myOffer;
    }

    public void setMyOffer(MyOffer myOffer) {
        this.myOffer = myOffer;
        notifyChange();
    }

    public void onItemClick(View view) {
        mRecyclerClick.onClick(myOffer, position);
    }

}
