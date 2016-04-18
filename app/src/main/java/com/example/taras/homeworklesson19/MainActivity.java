package com.example.taras.homeworklesson19;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public final class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GoogleCloudMessaging gcm;
    private String regId;
    private static SwitchCompat swtchNotificationType;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!GcmPushHelper.checkGooglePlayServices(this)) {
            Toast
                    .makeText(this, getString(R.string.not_installed_play_services), Toast.LENGTH_LONG)
                    .show();
            return;
        }

        gcm = GoogleCloudMessaging.getInstance(this);
        regId = GcmPushHelper.getRegistrationId(this);

        if (TextUtils.isEmpty(regId)) {
            GcmPushHelper.registerGcmAsync(this, gcm);
        }

        swtchNotificationType = (SwitchCompat) findViewById(R.id.swtch_notification_type_AM);
        textView = (TextView) findViewById(R.id.tv_AM);
        textView.setText(getString(R.string.default_notification_view));
    }

    @Override
    public void onClick(View v) {
        Log.v("taras_tag", regId);

        if (notificationType().equals(Constants.DEFAULT)) {
            textView.setText(getString(R.string.default_notification_view));
        } else {
            textView.setText(getString(R.string.custom_notification_view));
        }
    }

    public static String notificationType() {
        return swtchNotificationType.isChecked() ? Constants.DEFAULT : Constants.CUSTOM;
    }
}
