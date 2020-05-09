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
import com.selwantech.raheeb.enums.OrderHiringStatus;
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
            if (type.equals(AppConstants.NotificationTypes.ORDER_TAKEN)) {
                showNotification(getApplicationContext(),
                        getApplicationContext().getResources().getString(R.string.app_name),
                        getApplicationContext().getResources().getString(R.string.order_taken), 1);
                Log.e("@ReceivedCallback", "Order Taken From Firebase");
                Intent intent = new Intent();
                intent.setAction("ORDER");
                intent.addCategory((Intent.CATEGORY_DEFAULT));
                intent.putExtra("order_status", OrderHiringStatus.TAKEN.getStatus());
                intent.putExtra("order_id", Integer.valueOf(remoteMessage.getData().get("type_id")));
                sendBroadcast(intent);
            } else if (type.equals(AppConstants.NotificationTypes.ORDER_EXPIRATION)) {
                showNotification(getApplicationContext(),
                        getApplicationContext().getResources().getString(R.string.app_name),
                        getApplicationContext().getResources().getString(R.string.no_worker_available), 2);
                Log.e("@ReceivedCallback", "Order Declined From Firebase");
                Intent intent = new Intent();
                intent.setAction("ORDER");
                intent.addCategory((Intent.CATEGORY_DEFAULT));
                intent.putExtra("order_status", OrderHiringStatus.DECLINED.getStatus());
                intent.putExtra("order_id", remoteMessage.getData().get("type_id"));
                sendBroadcast(intent);
            } else if (type.equals("worker_tracking") && remoteMessage.getData().get("lat") != null) {
                Intent intent = new Intent();
                intent.setAction("WORKER_TRACKING");
                intent.putExtra("order_id", remoteMessage.getData().get("type_id"));
                intent.putExtra("Lat", remoteMessage.getData().get("lat"));
                intent.putExtra("Lon", remoteMessage.getData().get("lon"));
                sendBroadcast(intent);
            } else if (type.equals(AppConstants.NotificationTypes.ORDER_COMPLEATED)) {
                showNotification(getApplicationContext(),
                        getApplicationContext().getResources().getString(R.string.app_name),
                        getApplicationContext().getResources().getString(R.string.order_completed), 3);
            } else {
                showNotification(getApplicationContext(),
                        remoteMessage.getNotification().getTitle(),
                        remoteMessage.getNotification().getBody(), 0);
            }
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
