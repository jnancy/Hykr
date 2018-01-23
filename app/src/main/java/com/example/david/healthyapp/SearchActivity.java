package com.example.david.healthyapp;

import android.os.Bundle;

import android.widget.FrameLayout;

import android.widget.ListView;

import java.util.ArrayList;


public class SearchActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_search, contentFrameLayout);
        // Construct the data source
        ArrayList<Trail> arrayOfUsers = new ArrayList<>();
        // Create the adapter to convert the array to views
        CustomAdapter adapter = new CustomAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        ListView listView = findViewById(R.id.lvItems);
        listView.setAdapter(adapter);
    }
}
