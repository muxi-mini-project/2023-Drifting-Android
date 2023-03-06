package android.bignerdranch.drifting;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Book_DriftingBookActivity extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_create);

        Button acquaintance_mode = (Button) findViewById(R.id.acquaintance_mode);
        acquaintance_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Book_AcquaintanceModeActivity.class));
            }
        });

        Button stranger_mode = (Button) findViewById(R.id.stranger_mode);
        stranger_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Book_StrangerModeActivity.class));
            }
        });
         ImageButton create_back = findViewById(R.id.create_back);
         create_back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
    }
}