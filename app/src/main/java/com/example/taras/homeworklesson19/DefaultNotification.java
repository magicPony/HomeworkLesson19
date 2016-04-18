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

/**
 * Created by taras on 17.04.16.
 */
public final class DefaultNotification {
    
    public static void showNotification(MyGcmListenerService myGcmListenerService, Bundle data) {
        String message, title, imageSrc;

        message = data.getString(Constants.MESSAGE);
        title = data.getString(Constants.TITLE);
        imageSrc = data.getString(Constants.IMAGE_SRC);

        Intent activityIntent = new Intent(myGcmListenerService, MainActivity.class);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent openIntent = PendingIntent.getActivity(myGcmListenerService, 0, activityIntent, PendingIntent.FLAG_ONE_SHOT);

        final Notification.Builder notificationBuilder;
        notificationBuilder = new Notification.Builder(myGcmListenerService)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_light_pressed)
                .setAutoCancel(false)
                .setContentIntent(null);

        if (Build.VERSION.SDK_INT >= 21) {
            notificationBuilder.addAction(android.R.drawable.ic_menu_compass, "OpenApp", openIntent);
            notificationBuilder.setVisibility(Notification.VISIBILITY_PUBLIC);
        }

        final Notification.BigPictureStyle bigPicStyle = new Notification.BigPictureStyle();
        bigPicStyle.setBigContentTitle(title);
        bigPicStyle.setSummaryText(message);

        final NotificationManager notifyManager;
        notifyManager = (NotificationManager) myGcmListenerService.getSystemService(Context.NOTIFICATION_SERVICE);

        ITaskCallback ITaskCallback = new ITaskCallback() {
            @Override
            public void onStart() {
            }

            @Override
            public void onFinish(Bitmap bitmap) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    notificationBuilder.setSmallIcon(Icon.createWithBitmap(bitmap));
                }

                bigPicStyle.bigPicture(bitmap);
                notificationBuilder.setStyle(bigPicStyle);
                notifyManager.notify(0, notificationBuilder.build());
            }
        };

        AsyncDownloader asyncDownloader = new AsyncDownloader(ITaskCallback);
        asyncDownloader.execute(imageSrc);
    }
}
