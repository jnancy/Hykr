package com.example.david.healthyapp;

import android.Manifest;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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


//    LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//    @SuppressLint("MissingPermission")
//    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//    double myLat = 49.287224;
//    double myLng = -123.124011;
//
//    private final LocationListener locationListener = new LocationListener() {
//        public void onLocationChanged(Location location) {
//            myLng = location.getLongitude();
//            myLat = location.getLatitude();
//        }
//    };
//
//lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListener);

    protected Location mLastLocation;

    protected GoogleMap nMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_maps, contentFrameLayout);
        setUpMapIfNeeded();

    }

    private void setUpMapIfNeeded() {
        if (nMap == null) {
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
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
    public void onMapReady (GoogleMap map) {
        nMap = map;

        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        enableMyLocation();
        CameraUpdateFactory.zoomTo(10);
        new MarkerTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class MarkerTask extends AsyncTask<Void, Void, String> {

        private static final String LOG_TAG = "Hykr";

        private String url = "https://www.mountainproject.com/data/get-routes-for-lat-lon?lat=37.937915&lon=-107.833746&maxDistance=10&minDiff=5.6&maxDiff=5.10&key=200207554-301205ccb6737ed3d9915ba030e010a4";

        private final String SERVICE_URL = url;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(Void... args) {

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
                Log.e(LOG_TAG, "Error connecting to service", e);
                //throw new IOException("Error connecting to service", e); //uncaught
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }

            return json.toString();
        }

        // Executed after the complete execution of doInBackground() method


        protected void onPostExecute(String json) {

            try {
                // De-serialize the JSON string into an array of city objects
                JSONObject object = new JSONObject(json);
                JSONArray jsonArray  = object.getJSONArray("routes");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    String name = jsonObj.getString("name");
                    Double latitude = jsonObj.getDouble("latitude");
                    Double longitude = jsonObj.getDouble("longitude");
                    Double stars = jsonObj.getDouble("stars");

//                    final int nameID = getResources().getIdentifier(name, "id",
//                            getPackageName());
//                    final int ratingID = getResources().getIdentifier(String.valueOf(rating), "id",
//                            getPackageName());

                    LatLng latLng = new LatLng(latitude, longitude);

                    nMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).title(name).position(latLng).snippet("Rating: " + stars));

                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error processing JSON", e);
            }

        }
    }






        // and move the map's camera to the same location.
//        LatLng PoolBoy = new LatLng(49.3522, -123.2413);
//        LatLng QueenOfCapilano = new LatLng(49.351, -123.3575);
//        LatLng MobyDick = new LatLng(49.3521, -123.2288);
//        LatLng Jenga = new LatLng(49.3521, -123.2288);
//        LatLng AncientMariner = new LatLng(49.331, -123.2665);
//        map.addMarker(new MarkerOptions().position(PoolBoy)
//                .title("Pool Boy"));
//        map.addMarker(new MarkerOptions().position(QueenOfCapilano)
//                .title("Queen of Capilano"));
//        map.addMarker(new MarkerOptions().position(MobyDick)
//                .title("Moby Dick"));
//        map.addMarker(new MarkerOptions().position(Jenga)
//                .title("Jenga"));
//        map.addMarker(new MarkerOptions().position(AncientMariner)
//                .title("Ancient Mariner"));

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    public void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (nMap != null) {
            // Access to the location has been granted to the app.
            nMap.setMyLocationEnabled(true);

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

        // Display the missing permission error dialog when the fragments resume.
        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else mPermissionDenied = true;
    }
}

