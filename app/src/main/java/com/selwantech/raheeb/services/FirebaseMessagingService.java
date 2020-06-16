package com.selwantech.raheeb.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.selwantech.raheeb.R;
import com.selwantech.raheeb.enums.FollowStatus;
import com.selwantech.raheeb.helper.SessionManager;
import com.selwantech.raheeb.repository.DataManager;
import com.selwantech.raheeb.repository.network.ApiCallHandler.APICallBack;
import com.selwantech.raheeb.ui.splashscreen.SplashScreenActivity;
import com.selwantech.raheeb.utils.AppConstants;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().get("type") != null) {
            String type = remoteMessage.getData().get("type");

        }
        super.onMessageReceived(remoteMessage);
    }

    public void showNotification(Context context, String title, String body, int type) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = type;
        String channelId = "KHADMAT";
        String channelName = "Khadmat";
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

        Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
        intent.putExtra("notificationType", type);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
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
