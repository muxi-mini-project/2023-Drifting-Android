package android.bignerdranch.drifting.Novel;

import android.bignerdranch.drifting.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Novel_switch_mode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.switch_mode);
        Button acquaintance_mode = (Button) findViewById(R.id.acquaintance_mode);
        acquaintance_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Novel_AcquaintanceModeActivity.class));
                finish();
            }
        });

        Button stranger_mode = (Button) findViewById(R.id.stranger_mode);
        stranger_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Novel_StrangerModeActivity.class));
                finish();
            }
        });
    }
}