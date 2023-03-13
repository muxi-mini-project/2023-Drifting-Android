package android.bignerdranch.drifting.Drawing;

import android.bignerdranch.drifting.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Drawing_Start extends AppCompatActivity {
    private Button start_button;
    private Button return_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawing_start);

        start_button = (Button) findViewById(R.id.start_drawing);
        return_button = (Button) findViewById(R.id.return_button);

        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Drawing_Start.this,Drawing_Activity.class));
            }
        });
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}