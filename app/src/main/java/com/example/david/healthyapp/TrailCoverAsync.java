package com.example.david.healthyapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TrailCoverAsync extends AsyncTask<Void, Void, Bitmap> {

    @SuppressLint("StaticFieldLeak")
    private Context applicationContext = MainActivity.getContextOfApplication();

    private String restoredURL = PrefsUtils.getPreferenceValue(applicationContext, "CoverURL","default_url");

    @Override
    protected Bitmap doInBackground(Void... params) {

        try {
            Log.e("URL: ", restoredURL);
            URL url = new URL(restoredURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap mIcon1 = BitmapFactory.decodeStream(input);
            return mIcon1;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
