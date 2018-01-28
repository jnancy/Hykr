package com.example.david.healthyapp;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by brandon on 2018-01-27.
 */

public class TrailAsync extends AsyncTask<Void, Void, String> {
    String SERVICE_URL = "https://www.hikingproject.com/data/get-trails?lat=40.0274&lon=-105.2519&maxDistance=30&key=200207554-f9a3aa945fc91ca908d9be0f7ecd12ba";

    // Invoked by execute() method of this object
    HttpURLConnection conn = null;
    final StringBuilder json = new StringBuilder();

    @Override
    protected String doInBackground(Void... params) {
        try {
            // Connect to the web service
            URL url = new URL(SERVICE_URL);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Read the JSON data into the StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                json.append(buff, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
            //throw new IOException("Error connecting to service", e); //uncaught
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return json.toString();
    }
}
