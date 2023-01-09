package com.example.drift;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;

public class start_camera extends AppCompatActivity {
    ImageView photo1;
    ImageView photo2;
    ImageView photo3;
    ImageView photo4;
    final String PHOTO_RETURN = "photo_return";
    boolean p = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_start_camera);
        if (p == false) {
            photo1 = (ImageView) findViewById(R.id.photo1);
            photo2 = (ImageView) findViewById(R.id.photo2);
            photo3 = (ImageView) findViewById(R.id.photo3);
            photo4 = (ImageView) findViewById(R.id.photo4);
        }
        ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    photo1.setImageResource(R.drawable.try2);
                    photo2.setImageResource(R.drawable.try3);
                    photo3.setImageResource(R.drawable.try4);
                    Uri uri1 = Uri.parse(result.getData().getStringExtra(PHOTO_RETURN));
                    photo4.setImageURI(uri1);
                }
            }
        });
        Button mStartCameraButton = (Button) findViewById(R.id.start_button2);
        Button mReturnButton = (Button) findViewById(R.id.return_button);
        mStartCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(start_camera.this, paishe_camera.class);
                launcher.launch(intent);
            }
        });
        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(start_camera.this, LayoutMain.class);
                startActivity(intent);
            }
        });
    }
}