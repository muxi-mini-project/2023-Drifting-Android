package android.bignerdranch.drifting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Login_LoginActivity extends AppCompatActivity {
    private ImageView mWelcome;
    private EditText mAccountText;
    private EditText mPasswordText;
    private Button mLoginButton;
    private String account;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mWelcome = (ImageView) findViewById(R.id.welcome);
        mAccountText = (EditText) findViewById(R.id.login_account);
        mPasswordText = (EditText) findViewById(R.id.login_password);
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


        mAccountText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                account = mAccountText.getText().toString();
            }
        });//接收账号
        mPasswordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                password = mPasswordText.getText().toString();
            }
        });//接收密码

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login_LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                Intent intent = Main_MainActivity.newIntent(Login_LoginActivity.this);
                startActivity(intent);
                finish();
            }
        });

    }


}