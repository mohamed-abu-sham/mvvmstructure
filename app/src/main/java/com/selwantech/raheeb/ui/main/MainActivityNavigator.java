package com.selwantech.raheeb.ui.main;


import com.selwantech.raheeb.model.notificationsdata.NotifyData;
import com.selwantech.raheeb.ui.base.BaseNavigator;

public interface MainActivityNavigator extends BaseNavigator {
    NotifyData getNotification();
}
