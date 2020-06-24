package com.selwantech.raheeb.ui.splashscreen;


import com.selwantech.raheeb.model.notificationsdata.NotifyData;
import com.selwantech.raheeb.ui.base.BaseNavigator;

public interface SplashScreenNavigator extends BaseNavigator {

    String getInviteToken();

    NotifyData getNotification();
}
