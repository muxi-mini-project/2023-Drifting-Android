package android.bignerdranch.drifting.Drawing;

import androidx.appcompat.app.AppCompatActivity;

import android.bignerdranch.drifting.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Drawing_Album extends AppCompatActivity {
    private Button back;
    private Button album;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawing_album);


        back = (Button) findViewById(R.id.back);
        album = (Button) findViewById(R.id.album);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}