package com.selwantech.raheeb.helper;

import android.app.Activity;
import android.os.Bundle;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.enums.NotificationEvents;
import com.selwantech.raheeb.model.notificationsdata.NotifyData;
import com.selwantech.raheeb.utils.AppConstants;

import androidx.navigation.Navigation;

public class NotificationHelper {

    Activity activity;
    NotifyData notification;

    public NotificationHelper(Activity activity, NotifyData notification) {
        this.activity = activity;
        this.notification = notification;
    }

    public void handleNotificationEvent() {
        Bundle data = new Bundle();
        NotificationEvents notificationEvent = NotificationEvents.fromString(notification.getType());
        switch (notificationEvent) {
        }
    }

    private void navigate(int destinationId, Bundle data) {
        Navigation.findNavController(activity, R.id.nav_host_fragment)
                .navigate(destinationId, data);
    }
}
