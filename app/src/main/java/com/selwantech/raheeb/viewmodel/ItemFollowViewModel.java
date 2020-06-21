
package com.selwantech.raheeb.viewmodel;

import android.content.Context;
import android.view.Gravity;

import com.selwantech.raheeb.enums.FollowStatus;
import com.selwantech.raheeb.helper.SessionManager;
import com.selwantech.raheeb.interfaces.ItemClickWithType;
import com.selwantech.raheeb.model.ProductOwner;

import androidx.databinding.BaseObservable;


public class ItemFollowViewModel extends BaseObservable {

    private final Context context;
    ItemClickWithType mRecyclerClick;
    private ProductOwner user;
    private int position;

    public ItemFollowViewModel(Context context, ProductOwner user, int position, ItemClickWithType mRecyclerClick) {
        this.context = context;
        this.user = user;
        this.position = position;
        this.mRecyclerClick = mRecyclerClick;
    }

    public ProductOwner getUser() {
        return user;
    }

    public void setUser(ProductOwner user) {
        this.user = user;
        notifyChange();
    }

    public void onItemClicked(){
        mRecyclerClick.onClick(user, FollowStatus.NON.getStatus(),position);
    }

    public void onFollowClicked() {
        if (!user.isIsFollow()) {
            mRecyclerClick.onClick(user, FollowStatus.FOLLOW.getStatus(),position);
        } else {
            mRecyclerClick.onClick(user, FollowStatus.UNFOLLOW.getStatus(),position);
        }
    }

    public int getGravity() {
        return SessionManager.getLanguage().equals("ar") ? Gravity.LEFT : Gravity.RIGHT;
    }

}
