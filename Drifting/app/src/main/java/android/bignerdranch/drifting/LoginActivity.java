package android.bignerdranch.drifting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText account_text = (EditText) findViewById(R.id.login_account);
    private EditText password_text = (EditText) findViewById(R.id.login_password);
    private String account;
    private String password;
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