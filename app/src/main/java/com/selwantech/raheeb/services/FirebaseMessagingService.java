package com.selwantech.raheeb.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.messaging.RemoteMessage;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.helper.SessionManager;
import com.selwantech.raheeb.model.notificationsdata.NotifyData;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.main.MainActivity;
import com.selwantech.raheeb.utils.AppConstants;

import androidx.core.app.NotificationCompat;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        NotifyData notification = new NotifyData(Integer.valueOf(remoteMessage.getData().get(("acction_id"))),
                remoteMessage.getData().get("type"));
        showNotification(getApplicationContext(),
                remoteMessage.getNotification().getTitle(),
                remoteMessage.getNotification().getBody(),
                notification);
        super.onMessageReceived(remoteMessage);
    }

    public void showNotification(Context context, String title, String body, NotifyData notifyData) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = notifyData.getAcctionId();
        String channelId = "RAHEEB";
        String channelName = "Raheeb";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(AppConstants.BundleData.NOTIFICATION, notifyData);
//        stackBuilder.addNextIntent(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        stackBuilder.addNextIntentWithParentStack(intent);
        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                uniqueInt,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(true);
        notificationManager.notify(uniqueInt, mBuilder.build());
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        SessionManager.saveFireBaseToken(s);
        if (SessionManager.isLoggedIn()) {
            DataManager.getInstance().getAuthService().updateFirebaseToken(getApplicationContext(), false, new APICallBack() {
                @Override
                public void onSuccess(Object response) {

                }

                @Override
                public void onError(String error, int errorCode) {

                }
            });
        }
    }


}
