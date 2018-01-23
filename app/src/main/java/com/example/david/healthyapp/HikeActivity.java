package com.example.david.healthyapp;

import android.os.Bundle;

import android.view.View;
import android.widget.FrameLayout;

import android.widget.ListView;

import android.widget.ViewFlipper;

import java.util.ArrayList;


public class HikeActivity extends MainActivity {

//    public class Vars {
//        SeekBar seekbar = findViewById(R.id.seekbar);
//        int seekBarValue = seekbar.getProgress();
//        private String url = "";
//
//        public Vars() {
//            this.url = "https://www.hikingproject.com/data/get-trails?lat=40.0274&lon=-105.2519&maxDistance=30&key=200207554-f9a3aa945fc91ca908d9be0f7ecd12ba"; // actually "set" the value.
//        }
//
//        public String getUrl() {
//            return url;
//        }
//
//        public static void outputToTxt(Vars object) {
//            url = object.getUrl();
//            // ...
//        }
//    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_hike, contentFrameLayout);

    }


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
        ViewFlipper flipper = findViewById(R.id.simpleViewFlipper);
        flipper.showNext();
        // Construct the data source
        ArrayList<Trail> arrayOfTrails = new ArrayList<Trail>();
// Create the adapter to convert the array to views
        CustomAdapter adapter = new CustomAdapter(this, arrayOfTrails);
// Attach the adapter to a ListView
        ListView listView = findViewById(R.id.lvItems);
        listView.setAdapter(adapter);
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
