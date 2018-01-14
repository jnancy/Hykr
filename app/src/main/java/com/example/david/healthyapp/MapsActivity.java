package com.example.david.healthyapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MapsActivity extends MainActivity
        implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {


    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

//    @Override
//    public void run() throws IOException {
//        // Create URL
//        URL webEndpoint = new URL("https://www.mountainproject.com/data/");
//
//        // Create connection
//        HttpsURLConnection myConnection =
//                (HttpsURLConnection) webEndpoint.openConnection();
//        myConnection.setRequestProperty("User-Agent", "healthy-app-v0.1");
//        if (myConnection.getResponseCode() == 200) {
//            InputStreamReader responseBodyReader =
//                    new InputStreamReader(responseBody, "UTF-8");
//            JsonReader jsonReader = new JsonReader(responseBodyReader);
//
//            jsonReader.beginObject(); // Start processing the JSON object
//            while (jsonReader.hasNext()) { // Loop through all keys
//                String key = jsonReader.nextName(); // Fetch the next key
//                if (key.equals("name")) { // Check if desired key
//                    // Fetch the value as a String
//                    // Make this accessible to onMapReady !!!!!!!!!!!!!!!!!!!!!!
//                    String Name = jsonReader.nextString();
//                    private String Name
//                }
//
//                if (key.equals("longitude")) {
//                    // Make this accessible to onMapReady !!!!!!!!!!!!!!!!!!!!!!
//                    String Longitude = jsonReader.nextString();
//                }
//
//
//                if (key.equals("latitude")) {
//                    // Make this accessible to onMapReady !!!!!!!!!!!!!!!!!!!!!!
//                    String Latitude = jsonReader.nextString();
//                } else {
//                    jsonReader.skipValue(); // Skip values of other keys
//                }
//            }
//        } else {
//            // Error handling code goes here
//        }
//    }


    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();
        CameraUpdateFactory.zoomTo(10);
        // and move the map's camera to the same location.
        LatLng GrouseGrind = new LatLng(49.386111, -123.076389);
        LatLng Headstone = new LatLng(49.3522, -123.2413);
        LatLng ArbutusOriginal = new LatLng(49.3314, -123.2663);
        LatLng JavaDirect = new LatLng(49.3334, -122.9375);
        LatLng AncientMariner = new LatLng(49.331, -123.2665);
        map.addMarker(new MarkerOptions().position(GrouseGrind)
                .title("Grouse Grind"));
        map.addMarker(new MarkerOptions().position(Headstone)
                .title("Headstone"));
        map.addMarker(new MarkerOptions().position(ArbutusOriginal)
                .title("Arbutus Original"));
        map.addMarker(new MarkerOptions().position(JavaDirect)
                .title("Java Direct"));
        map.addMarker(new MarkerOptions().position(AncientMariner)
                .title("Ancient Mariner"));

    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }
}

