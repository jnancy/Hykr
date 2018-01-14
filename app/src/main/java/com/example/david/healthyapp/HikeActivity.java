package com.example.david.healthyapp;

import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HikeActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_hike, contentFrameLayout);

        List<String> list = new ArrayList<>();
        list.add("https://cdn-files.apstatic.com/climb/106821891_smallMed_1494143342.jpg");

        LinearLayout layout = (LinearLayout) findViewById(R.id.linear);
        /*for (int i = 0; i < 1; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setId(i);
            imageView.setPadding(2, 2, 2, 2);
            loadImageFromURL(list.get(i), imageView);
            imageView.setMinimumWidth(350);
            imageView.setMaxHeight(400);
            layout.addView(imageView);
        }*/
        ImageView imageView = new ImageView(this);
        File file = new File("drawable/hike1.jpg");
        //Image image = new Image(file.toURI().toString());
        //imageView.setImage(image);
        //layout.addView(view);
    }

    public boolean loadImageFromURL(String fileUrl,
                                    ImageView iv){
        try {

            URL myFileUrl = new URL (fileUrl);
            HttpURLConnection conn =
                    (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();

            InputStream is = conn.getInputStream();
            iv.setImageBitmap(BitmapFactory.decodeStream(is));

            return true;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
