package android.bignerdranch.drifting;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
public class LoginActivity extends AppCompatActivity {



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        new CountDownTimer(2000, 1000)
        {
            public void onTick(long millisUntilFinished) {}
            @Override
            public void onFinish() {
                setContentView(R.layout.login);
            }

        }.start();

    }

    
}