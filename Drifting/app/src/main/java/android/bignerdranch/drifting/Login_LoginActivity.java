package android.bignerdranch.drifting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 登录界面
 */
public class Login_LoginActivity extends AppCompatActivity {
    private ImageView mWelcome;
    private EditText mAccountText;
    private EditText mPasswordText;
    private Button mLoginButton;
    private int account;
    private String password;
    TextView ty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        mWelcome = findViewById(R.id.welcome);
        mAccountText = findViewById(R.id.login_account);
        mPasswordText = findViewById(R.id.login_password);
        mLoginButton = findViewById(R.id.login_button);
        ty = findViewById(R.id.text_try);
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
                account = Integer.parseInt(mAccountText.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });//接收账号
        mPasswordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = mPasswordText.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });//接收密码

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ty.setText(account+password);
//               Login_student student = new Login_student();
//               student.setStudentID(account);
//               student.setPassWord(password);
//                Retrofit.Builder builder = new Retrofit.Builder()
//                        .baseUrl("http://116.204.121.9:61583/")
//                        .addConverterFactory(GsonConverterFactory.create());
//                Retrofit retrofit = builder.build();
//                Login_connetor login_connetor = retrofit.create(Login_connetor.class);
//                Call<Login_return> call = login_connetor.login(student);
//                call.enqueue(new Callback<Login_return>() {
//                    @Override
//                   public void onResponse(Call<Login_return> call, Response<Login_return> response) {
//                        if (response.isSuccessful()) {
//                       Toast.makeText(Login_LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//
                        Intent intent = Main_MainActivity.newIntent(Login_LoginActivity.this);
                        startActivity(intent);
                        finish();
//                         } else {
//                            ty.setText(response.body().getMessage().toString());
//                           Toast.makeText(getApplicationContext(), "身份认证失败，请重新登录", Toast.LENGTH_SHORT).show();
//                          }
//                    }
//
//                    @Override
//                   public void onFailure(Call<Login_return> call, Throwable t) {
//
//                        ty.setText(t.getMessage());
//                        //测试阶段获取错误原因并显示在界面上
//                    }
//                });

        }
});}}