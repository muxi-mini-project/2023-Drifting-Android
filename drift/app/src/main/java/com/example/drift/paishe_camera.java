package com.example.drift;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class paishe_camera extends AppCompatActivity {
    Button mTakephotoButton;
    Button mPhotoButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paishe_camera);
        mTakephotoButton = (Button) findViewById(R.id.take_photo_button);
        mPhotoButton = (Button)findViewById(R.id.photo_button);
        mTakephotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}