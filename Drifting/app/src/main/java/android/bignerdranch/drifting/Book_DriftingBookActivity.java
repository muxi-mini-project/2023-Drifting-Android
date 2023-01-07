package android.bignerdranch.drifting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Book_DriftingBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drifting_book_one);

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
    }
}