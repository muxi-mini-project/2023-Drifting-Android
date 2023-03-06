package android.bignerdranch.drifting.Camera;

import android.bignerdranch.drifting.Main.Main_MainActivity;
import android.bignerdranch.drifting.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Camera_Inviting extends AppCompatActivity {
    Button mStartButton;
    Button mFangqiButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_inviting);
        mStartButton = (Button) findViewById(R.id.start_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Camera_Inviting.this, Camera_Start.class);
                startActivity(intent);
            }
        });
        mFangqiButton = (Button) findViewById(R.id.fangqi_button);
        mFangqiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Camera_Inviting.this, Main_MainActivity.class);
                startActivity(intent);
            }
        });
    }
}