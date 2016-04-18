package com.example.taras.homeworklesson19;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by taras on 17.04.16.
 */
public final class AsyncDownloader extends AsyncTask<String, Void, Bitmap> {

    private ITaskCallback callback;

    public AsyncDownloader(ITaskCallback callback) {
        this.callback = callback;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap res = null;

        try {
            URL url = new URL(params[0]);
            URLConnection connection = url.openConnection();
            res = BitmapFactory.decodeStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        callback.onFinish(bitmap);
    }
}
