package com.example.david.healthyapp;

import org.json.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by david on 13/01/18.
 */

public class MountainAPIProvider {

    // Divide difficulties into easy, med, hard
    // iterate through json components
    // if difficulty in what the user selected AND distance within defined range
    //  return name, difficulty, and distance
    String[] Easy = {"5.5", "5.6", "5.7", "5.8", "5.9"};
    String[] Medi = {"5.10a", "5.10b", "5.10c", "5.11a", "5.11b",
            "5.11c", "5.11+", "V0", "V1", "V2", "V3", "V2-V3"};
    String[] Hard = {"5.12a", "5.12b", "5.12c", "V3-V4", "V5"};


    public List<Hike> filterHikes(String[] diff, double dist) {

        try {
            Scanner scanner = new Scanner(new File("app/sampledata/van-routes.json"));
            String text = scanner.useDelimiter("\\A").next();
            scanner.close();
            JSONObject obj = new JSONObject(text);

            JSONArray arr = obj.getJSONArray("routes");

            List<Hike> hikes = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                Hike hike = new Hike();
                String rating = arr.getJSONObject(i).getString("rating");
                double lat2 = arr.getJSONObject(i).getDouble("latitude");
                double lon2 = arr.getJSONObject(i).getDouble("longitude");
                if (this.stringContainsItemFromList(rating, diff) &&
                        distanceBetween(49.3, lat2, -123.12, lon2) < dist) {
                    hike.setName(arr.getJSONObject(i).getString("name"));
                    hike.setLat(lat2);
                    hike.setLon(lon2);
                    hike.setStars(arr.getJSONObject(i).getDouble("stars"));
                    hike.setImgUrl((arr.getJSONObject(i).getString("imgSmallMed")).replace("\\", "" ));
                    hikes.add(hike);
                }
            }
            return hikes;
        } catch (FileNotFoundException e) {
            System.out.println("fuck");
        } catch (JSONException e) {
            System.out.println("shit");
        }
        return new ArrayList<Hike>();
    }


    private static boolean stringContainsItemFromList(String inputStr, String[] items)
    {
        for(int i =0; i < items.length; i++)
        {
            if(inputStr.contains(items[i]))
            {
                return true;
            }
        }
        return false;
    }

    private static double distanceBetween(double la1, double la2, double lo1, double lo2) {
        double lat1 = la1 / 180.0 * Math.PI;
        double lat2 = la2 / 180.0 * Math.PI;
        double deltaLon = (lo2 - lo1) / 180.0 * Math.PI;
        double deltaLat = (la2 - la1) / 180.0 * Math.PI;

        double a = Math.sin(deltaLat / 2.0) * Math.sin(deltaLat / 2.0)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(deltaLon / 2.0) * Math.sin(deltaLon / 2.0);
        double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return c * 6371000;
    }



}
