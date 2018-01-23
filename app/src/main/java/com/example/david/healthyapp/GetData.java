package com.example.david.healthyapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by brandon on 2018-01-22.
 */

public class GetData {

    private static GetData _GetData;
    private static Context context;
    private String TAG = GetData.class.getSimpleName();

    private GetData(Context context) {
        GetData.context = context;
    }

    public static GetData getInstance(Context context) {
        if (null == _GetData) {
            synchronized (GetData.class) {
                if (null == _GetData) {
                    _GetData = new GetData(context);
                }
            }
        }
        return _GetData;
    }

//public void GetUserData(final AccessToken accessToken) {
//        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
//            @Override
//            public void onCompleted(JSONObject object, GraphResponse response) {
//                final TextView fbNameView = findViewById(R.id.textView2);
//                final ImageView fbProfilePic = findViewById(R.id.imageView2);
//                Log.e("BMain", response.toString());
//                String name = null;
//                String id = null;
//                URL img_value = null;
//                try {
//                    name = object.getString("name");
//                    id = object.getString("id");
//                    img_value = new URL("http://graph.facebook.com/" + id + "/picture?type=medium");
//                    Bitmap mIcon1 = BitmapFactory.decodeStream(img_value.openConnection().getInputStream());
//                    fbNameView.setText(name);
//                    fbProfilePic.setImageBitmap(mIcon1);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,name,link");
//        request.setParameters(parameters);
//        request.executeAsync();
//    }
}
