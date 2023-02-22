package com.example.drift;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class yaoqing_camera extends AppCompatActivity {
    Button mStartButton;
    Button mFangqiButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yaoqing_camera);
        mStartButton = (Button) findViewById(R.id.start_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(yaoqing_camera.this, start_camera.class);
                startActivity(intent);
            }
        });
        mFangqiButton = (Button) findViewById(R.id.fangqi_button);
        mFangqiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(yaoqing_camera.this, LayoutMain.class);
                startActivity(intent);
            }
        });
    }
}