package com.example.taras.homeworklesson19;

import android.graphics.Bitmap;

/**
 * Created by taras on 17.04.16.
 */
public interface ITaskCallback {
    void onStart();

    void onFinish(Bitmap bitmap);
}
