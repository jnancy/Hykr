package com.example.david.healthyapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final String EMAIL = "email";
    private static final String PROFILE = "public_profile";
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private CallbackManager callbackManager;
    public String name;
    public String id;
    public String picture;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    android.support.v7.widget.Toolbar toolbar;

    public static Context contextOfApplication;

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contextOfApplication = getApplicationContext();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        setContentView(R.layout.activity_main);
        boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
        AppEventsLogger.activateApp(getApplication());

        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL, PROFILE));

        final TextView fbNameView = findViewById(R.id.textView2);

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    // Login successful!
                    public void onSuccess(LoginResult loginResult) {
                        LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile"));
                        Log.e("ONSUCCESS", loginResult.getAccessToken().getToken());

                        // Make our call to the Graph API
                        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.e("Main", response.toString());

                                try {
                                    JSONObject data = response.getJSONObject();
                                    // Gets requested variables from graph API
                                    name = object.getString("name");
                                    id = object.getString("id");
                                    picture = object.getString("picture");
                                    Log.e("NAME:", name);

                                    String imageURLString = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                    URL imageURL = new URL (imageURLString);

                                    // Loads all desired variables into SharedPreferences
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("fbName", name);
                                    editor.putString("id", id);
                                    editor.putString("url", imageURLString);
                                    editor.apply();

                                    SharedPreferences prefs = sharedPreferences;
                                    String restoredText = prefs.getString("fbName", "Default String");
                                   // fbNameView.setText(restoredText);
                                    }
                                catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }


                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,link,picture");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }
                        @Override
                        public void onCancel() {
                            // App code
                        }

                        @Override
                        public void onError(FacebookException exception) {
                            // App code
                        }
                });


        NavigationView navigationView = findViewById(R.id.navigation_view);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Intent newIntent;
                switch (item.getItemId()) {
                    case R.id.nav_camera:
                        newIntent = new Intent(getApplicationContext(), UserProfile.class);
                        startActivity(newIntent);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_gallery:
                        newIntent = new Intent(getApplicationContext(), HikeActivity.class);
                        startActivity(newIntent);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_slideshow:
                        newIntent = new Intent(getApplicationContext(), MapsActivity.class);
                        startActivity(newIntent);
                        drawerLayout.closeDrawers();
                        break;
                }
                return false;
            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
        } else if (id == R.id.nav_slideshow) {
        } else if (id == R.id.nav_settings) {
        } else if (id == R.id.nav_share) {
        } else if (id == R.id.nav_send) {
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
