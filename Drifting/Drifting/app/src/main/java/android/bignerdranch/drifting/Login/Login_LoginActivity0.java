package android.bignerdranch.drifting.Login;

import android.bignerdranch.drifting.R;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Login_LoginActivity0 extends AppCompatActivity {
    private ImageView mWelcome;
    private Button mLoginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login1);
        mWelcome = (ImageView) findViewById(R.id.welcome);
        mLoginButton = (Button) findViewById(R.id.login_button);
        new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                mLoginButton.setVisibility(View.VISIBLE);
                mWelcome.setVisibility(View.INVISIBLE);
            }
        }.start();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_LoginActivity0.this,Login_LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


}