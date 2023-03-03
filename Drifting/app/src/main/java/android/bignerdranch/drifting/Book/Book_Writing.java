package android.bignerdranch.drifting.Book;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bignerdranch.drifting.R;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Book_Writing extends AppCompatActivity {
    private TextView countdownTx;
    private ImageButton back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_writing);
        countdownTx = findViewById(R.id.countdown);

        new CountDownTimer(20000, 1000) {
            public void onTick(long millisUntilFinished) {
                countdownTx.setText("时间还剩余"+millisUntilFinished/1000+"秒");
            }

            @Override
            public void onFinish() {
                countdownTx.setText("over");
            }
        }.start();

        back =  findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}