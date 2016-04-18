package com.example.taras.homeworklesson19;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Created by taras on 17.04.16.
 */
public abstract class GcmPushHelper {

    public static final int RQ_CODE             = 9000;
    public static final String TAG              = "LogTag";
    public static final String SENDER_ID        = "193979491962";

    public static final String PROPERTY_REG_ID  = "registration_id";

    protected static boolean checkGooglePlayServices(Activity activity) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode))
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity, RQ_CODE).show();
            return false;
        }

        return true;
    }

    protected static String getRegistrationId(final Context context) {
        final SharedPreferences shPref = getGcmPreferences(context);
        final String regId = shPref.getString(PROPERTY_REG_ID, "");

        if (TextUtils.isEmpty(regId)) {
            Log.v(TAG, "Registration id not found.");
        }

        return regId;
    }

    protected static void saveRegistrationId(final Context context, final String regId) {
        final SharedPreferences shPref = getGcmPreferences(context);
        final SharedPreferences.Editor shPrefEditor = shPref.edit();
        shPrefEditor.putString(PROPERTY_REG_ID, regId);
        shPrefEditor.apply();
    }

    protected static SharedPreferences getGcmPreferences(Context context) {
        return context.getSharedPreferences("GSMPrefs", Context.MODE_PRIVATE);
    }

    protected static void registerGcmAsync(final Context context, final GoogleCloudMessaging gsm) {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final String regId = gsm.register(SENDER_ID);
                    Log.i(TAG, "Reg ID: " + regId);
                    saveRegistrationId(context, regId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
}
