package com.example.taras.homeworklesson19;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * Created by taras on 17.04.16.
 */
public final class CustomNotification {

    public static void showNotification(MyGcmListenerService myGcmListenerService, Bundle data) {
        final RemoteViews remoteViews = new RemoteViews(myGcmListenerService.getPackageName(), R.layout.custom_notification);
        remoteViews.setTextViewText(R.id.tv_title_CN, data.getString(Constants.TITLE));
        remoteViews.setTextViewText(R.id.tv_message_CN, data.getString(Constants.MESSAGE));

        final Intent activityIntent = new Intent(myGcmListenerService, MainActivity.class);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        final PendingIntent openIntent = PendingIntent.getActivity(myGcmListenerService, 0, activityIntent, PendingIntent.FLAG_ONE_SHOT);

        final Notification.Builder notificationBuilder = new Notification.Builder(myGcmListenerService)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_light_pressed)
                .setAutoCancel(false)
                .setContentIntent(openIntent);


        final Notification notification = notificationBuilder.build();
        notification.bigContentView = remoteViews;
        notification.contentView = remoteViews;

        if (Build.VERSION.SDK_INT >= 21) {
            notification.headsUpContentView = remoteViews;
        }

        final NotificationManager notifyManager;
        notifyManager = (NotificationManager) myGcmListenerService.getSystemService(Context.NOTIFICATION_SERVICE);

        ITaskCallback callback = new ITaskCallback() {
            @Override
            public void onStart() {
            }

            @Override
            public void onFinish(Bitmap bitmap) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    notificationBuilder.setSmallIcon(Icon.createWithBitmap(bitmap));
                }

                notifyManager.notify(0, notification);
            }
        };

        AsyncDownloader asyncDownloader = new AsyncDownloader(callback);
        asyncDownloader.execute(data.getString(Constants.IMAGE_SRC));
    }
}
