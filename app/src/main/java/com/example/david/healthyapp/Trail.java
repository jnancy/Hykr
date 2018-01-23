package com.example.david.healthyapp;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by brandon on 2018-01-22.
 */

public class Trail {
    public String name;
    public String length;

    public Trail(JSONObject object) {


        String SERVICE_URL = "https://www.hikingproject.com/data/get-trails?lat=40.0274&lon=-105.2519&maxDistance=30&key=200207554-f9a3aa945fc91ca908d9be0f7ecd12ba";

        // Invoked by execute() method of this object
            HttpURLConnection conn = null;
            final StringBuilder json = new StringBuilder();
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

            try {
                // De-serialize the JSON string into an array of city objects
                JSONArray jsonArray  = object.getJSONArray("trails");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    this.name = jsonObj.getString("name");
                    this.length = jsonObj.getString("length");


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


        public static ArrayList<Trail> fromJson(JSONArray jsonObjects) {

        ArrayList<Trail> trails = new ArrayList<Trail>();
        for (int i = 0; i < jsonObjects.length(); i++) {
            try {
                trails.add(new Trail(jsonObjects.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return trails;
    }
}


