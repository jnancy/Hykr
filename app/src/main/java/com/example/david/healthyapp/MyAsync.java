package com.example.david.healthyapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

public class MyAsync extends AsyncTask<Void, Void, Bitmap> {

    Context applicationContext = MainActivity.getContextOfApplication();

    String restoredURL = PrefsUtils.getPreferenceValue(applicationContext, "url","default_url");

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
