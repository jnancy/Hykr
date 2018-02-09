package com.example.david.healthyapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;


public class UserProfile extends MainActivity {

    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contextOfApplication = getApplicationContext();


        setContentView(R.layout.activity_user_profile);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        Log.e("TEST", "test");
        callbackManager = CallbackManager.Factory.create();

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_user_profile, contentFrameLayout);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        String restoredText = prefs.getString("fbName", "Default String");

        final TextView fbNameView = findViewById(R.id.textView2);

        Log.e("Restored: ", restoredText);

        fbNameView.setText(restoredText);

        obj.execute();
    }

    @SuppressLint("StaticFieldLeak")
    MyAsync obj = new MyAsync() {

        @Override
        protected void onPostExecute(Bitmap bmp) {
            super.onPostExecute(bmp);

            Bitmap bm = bmp;

            final ImageView fbProfilePic = findViewById(R.id.imageView2);
            fbProfilePic.setImageBitmap(bm);

        }
    };


//        if(AccessToken.getCurrentAccessToken() != null){
//            GetUserData(accessToken);
//        }

//    public void GetUserData(final AccessToken accessToken) {
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
//        request.executeAndWait();
//    }
}



//setContentView(R.layout.activity_user_profile);








