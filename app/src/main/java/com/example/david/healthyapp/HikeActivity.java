package com.example.david.healthyapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import android.widget.ListView;

import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.ViewFlipper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HikeActivity extends MainActivity {

    private SeekBar seekbar;
    private Integer difficulty;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_hike, contentFrameLayout);
        seekbar = findViewById(R.id.seekbar);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonEasy) {
                    difficulty = 1;
                }
                if (checkedId == R.id.radioButtonMedium) {
                    difficulty = 2;
                }
                if (checkedId == R.id.radioButtonHard) {
                    difficulty = 3;
                }
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    TrailAsync obj = new TrailAsync() {
        @Override
        protected void onPostExecute(String json) {
            ArrayList<Trail> trails = new ArrayList<>();
            int seekBarValue = seekbar.getProgress();
            try {
                JSONObject object = new JSONObject(json);
                JSONArray jsonArray = object.getJSONArray("trails");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    String name = jsonObj.getString("name");
                    String length = jsonObj.getString("length");
                    String tDifficulty = jsonObj.getString("difficulty");
                    Double intLength = Double.valueOf(length);
                    if (intLength <= seekBarValue) {
                        if (difficulty == 1) {
                            if (tDifficulty.contains("green")) {
                                trails.add(new Trail(name, length, tDifficulty));
                            }
                        }
                        if (difficulty == 2) {
                            if (tDifficulty.contains("blue")) {
                                trails.add(new Trail(name, length, tDifficulty));
                            }
                        }
                        if (difficulty == 3) {
                            if (tDifficulty.contains("black")) {
                                trails.add(new Trail(name, length, tDifficulty));
                            }
                        }
                    }
                }

                ViewFlipper flipper = findViewById(R.id.simpleViewFlipper);
                flipper.showNext();
                // Create the adapter to convert the array to views
                CustomAdapter adapter = new CustomAdapter(getContextOfApplication(), trails);
                // Attach the adapter to a ListView
                ListView listView = findViewById(R.id.lvItems);
                listView.setAdapter(adapter);
            } catch (JSONException e) {
                Log.e("ERROR: ", "Error processing JSON", e);
            }
        }

    };



//        LinearLayout layout = (LinearLayout) findViewById(R.id.linear);
//        for (int i = 0; i < 1; i++) {
//            ImageView imageView = new ImageView(this);
//            imageView.setId(i);
//            imageView.setPadding(2, 2, 2, 2);
//            Picasso.with(this).load(list.get(i)).into(imageView);
//            //loadImageFromURL(list.get(i), imageView);
//            imageView.setMinimumWidth(350);
//            imageView.setMaxHeight(400);
//            layout.addView(imageView);



    public void gotoResults(View view) {

        obj.execute();
    }
}


        /*for (int i = 0; i < 1; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setId(i);
            imageView.setPadding(2, 2, 2, 2);
            loadImageFromURL(list.get(i), imageView);
            imageView.setMinimumWidth(350);
            imageView.setMaxHeight(400);
            layout.addView(imageView);
        }*/
        /*ImageView imageView = new ImageView(this);
        File file = new File("drawable/hike1.jpg");
        Image image = new Image(file.toURI().toString());
        //imageView.setImage(image);
        layout.addView(view);*/
    //}

//    public boolean loadImageFromURL(String fileUrl,
//                                    ImageView iv){
//        try {
//
//            URL myFileUrl = new URL (fileUrl);
//            HttpURLConnection conn =
//                    (HttpURLConnection) myFileUrl.openConnection();
//            conn.setDoInput(true);
//            conn.connect();
//
//            InputStream is = conn.getInputStream();
//            iv.setImageBitmap(BitmapFactory.decodeStream(is));
//
//            return true;
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
//}
