
package com.selwantech.raheeb.viewmodel;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.selwantech.raheeb.helper.SessionManager;
import com.selwantech.raheeb.interfaces.RecyclerClick;
import com.selwantech.raheeb.model.notificationsdata.Notification;

import androidx.databinding.BaseObservable;


public class ItemNotificationViewModel extends BaseObservable {

    private final Context context;
    RecyclerClick mRecyclerClick;
    private Notification notification;
    private int position;

    public ItemNotificationViewModel(Context context, Notification notification, int position, RecyclerClick mRecyclerClick) {
        this.context = context;
        this.notification = notification;
        this.position = position;
        this.mRecyclerClick = mRecyclerClick;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
        notifyChange();
    }

    public void onItemClick(View view) {
        mRecyclerClick.onClick(notification, position);
    }

    public int getGravity() {
        return SessionManager.getLanguage().equals("ar") ? Gravity.LEFT : Gravity.RIGHT;
    }

}
