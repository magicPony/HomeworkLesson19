package com.example.taras.homeworklesson19;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by taras on 17.04.16.
 */
public final class MyGcmListenerService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);

        if (MainActivity.notificationType().equals(Constants.DEFAULT)) {
            DefaultNotification.showNotification(this, data);
        } else {
            CustomNotification.showNotification(this, data);
        }
    }
}
