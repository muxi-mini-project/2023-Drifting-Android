package android.bignerdranch.drifting.Drawing;

import androidx.appcompat.app.AppCompatActivity;

import android.bignerdranch.drifting.Book.Book_AcquaintanceModeActivity;
import android.bignerdranch.drifting.Book.Book_StrangerModeActivity;
import android.bignerdranch.drifting.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Drawing_switch_mode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawing_switch);
        Button acquaintance_mode = (Button) findViewById(R.id.acquaintance_mode);
        acquaintance_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Drawing_AcquaintanceModeActivity.class));
            }
        });

        Button stranger_mode = (Button) findViewById(R.id.stranger_mode);
        stranger_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Book_StrangerModeActivity.class));
            }
        });
    }
}