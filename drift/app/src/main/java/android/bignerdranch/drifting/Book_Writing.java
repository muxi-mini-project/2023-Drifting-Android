package android.bignerdranch.drifting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

/**
 * 漂流本写作界面
 */
public class Book_Writing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_writing);

        new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
            }
        }.start();

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}