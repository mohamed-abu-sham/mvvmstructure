package com.selwantech.raheeb.helper;

import android.app.Activity;
import android.os.Bundle;

import com.selwantech.raheeb.R;
import com.selwantech.raheeb.enums.NotificationEvents;
import com.selwantech.raheeb.model.notificationsdata.Notification;
import com.selwantech.raheeb.ui.dialog.RateDialog;
import com.selwantech.raheeb.utils.AppConstants;

import androidx.navigation.Navigation;

public class NotificationHelper {

    Activity activity;
    Notification notification;

    public NotificationHelper(Activity activity, Notification notification) {
        this.activity = activity;
        this.notification = notification;
    }

    public void handleNotificationEvent() {
        Bundle data = new Bundle();
        NotificationEvents notificationEvent = NotificationEvents.fromString(notification.getNotifyData().getType());
        switch (notificationEvent) {
            case NEW_MESSAGE:
            case TYPE_OFFER:
            case TYPE_REJECT_OFFER:
            case TYPE_APPROVED_OFFER:
                data.putInt(AppConstants.BundleData.CHAT_ID, notification.getNotifyData().getAcctionId());
                navigate(R.id.chatFragment, data);
                break;
            case TYPE_FOLLOWING:
                data.putInt(AppConstants.BundleData.USER_ID, notification.getNotifyData().getAcctionId());
                navigate(R.id.userProfileFragment, data);
                break;
            case TYPE_FAVORITE:
                data.putInt(AppConstants.BundleData.PRODUCT_ID, notification.getNotifyData().getAcctionId());
                navigate(R.id.productDetailsFragment, data);
                break;
            case TYPE_RATE:
                RateDialog rateDialog = new RateDialog(activity, notification.getNotifyData().getAcctionId());
                rateDialog.showRateDialog();
                break;
            case TYPE_RATED:
                break;
            case TYPE_BUY_NOW:
                break;
        }
    }

    private void navigate(int destinationId, Bundle data) {
        Navigation.findNavController(activity, R.id.nav_host_fragment)
                .navigate(destinationId, data);
    }
}
