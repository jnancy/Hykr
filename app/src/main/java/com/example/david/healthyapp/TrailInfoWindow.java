package com.example.david.healthyapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class TrailInfoWindow extends HikeActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trail_info);

        FrameLayout contentFrameLayout = findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.trail_info, contentFrameLayout);

        String name;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                name = null;
            } else {
                name = extras.getString("name");
            }
        } else {
            name = (String) savedInstanceState.getSerializable("name");
        }

        String length;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                length = null;
            } else {
                length = extras.getString("length");
            }
        } else {
            length = (String) savedInstanceState.getSerializable("length");
        }

        String difficulty;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                difficulty = null;
            } else {
                difficulty = extras.getString("difficulty");
            }
        } else {
            difficulty = (String) savedInstanceState.getSerializable("difficulty");
        }

        String summary;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                summary = null;
            } else {
                summary = extras.getString("summary");
            }
        } else {
            summary = (String) savedInstanceState.getSerializable("summary");
        }

        String high;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                high = null;
            } else {
                high = extras.getString("high");
            }
        } else {
            high = (String) savedInstanceState.getSerializable("high");
        }

        String location;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                location = null;
            } else {
                location = extras.getString("location");
            }
        } else {
            location = (String) savedInstanceState.getSerializable("location");
        }

        String imgMedium;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                imgMedium = null;
            } else {
                imgMedium = extras.getString("imgMedium");
            }
        } else {
            imgMedium = (String) savedInstanceState.getSerializable("imgMedium");
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CoverURL", imgMedium);
        editor.apply();
        Log.e("URL_RECEIVED", imgMedium);

        String lengthFinal = length + " km";
        String highFinal = high + " m";

        final TextView NameView = findViewById(R.id.textView4);
        final TextView LengthView = findViewById(R.id.textView6);
        final TextView SummaryView = findViewById(R.id.textView8);
        final TextView HighView = findViewById(R.id.textView9);
        final TextView LocationView = findViewById(R.id.textView7);
        final ImageView iv = findViewById(R.id.iv);

        if (difficulty.contains("green")) {
            iv.setBackgroundColor(Color.parseColor("#008101"));
        }
        if (difficulty.contains("blue")) {
            iv.setBackgroundColor(Color.parseColor("#0365cb"));
        }
        if (difficulty.contains("black")) {
            iv.setBackgroundColor(Color.parseColor("black"));
        }

        NameView.setText(name);
        LengthView.setText(lengthFinal);
        SummaryView.setText(summary);
        HighView.setText(highFinal);
        LocationView.setText(location);

        obj.execute();

    }

    @SuppressLint("StaticFieldLeak")
    TrailCoverAsync obj = new TrailCoverAsync() {
        @Override
        protected void onPostExecute(Bitmap bmp) {
            super.onPostExecute(bmp);

            Bitmap bm = bmp;

            final ImageView CoverImage = findViewById(R.id.CoverImage);
            CoverImage.setImageBitmap(bm);

        }
    };
}
