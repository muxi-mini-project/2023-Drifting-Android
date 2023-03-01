package android.bignerdranch.drifting.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.bignerdranch.drifting.Main.Main_MainActivity;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.User_;
import android.bignerdranch.drifting.User.User_Now;
import android.bignerdranch.drifting.User.User_connector;
import android.bignerdranch.drifting.User.User_return;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login_LoginActivity extends AppCompatActivity {
    private EditText mAccountText;
    private EditText mPasswordText;
    private Button mLoginButton;
    private Integer account;
    private String password;
    User_ user = new User_();
  public static class User_returnAll{
        private Long code;
        private User_return data;
        private Object message;
        public User_return getData() {
            return data;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        mAccountText = (EditText) findViewById(R.id.login_account);
        mPasswordText = (EditText) findViewById(R.id.login_password);
        mLoginButton = (Button) findViewById(R.id.login_button);


        mAccountText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int p = 0;
                try{
                   Integer we = Integer.valueOf(mAccountText.getText().toString());
                }catch (Exception e){
                    p = 1;
                    Toast.makeText(getApplicationContext(),"请不要在账号中输入英文",Toast.LENGTH_SHORT).show();
                }
                if(p == 0){
                account = Integer.valueOf(mAccountText.getText().toString());
                user.setId(account);
            }
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
                Login_student student = new Login_student();
                if( account == null||password == null){
                    Toast.makeText(Login_LoginActivity.this, "请输入账号或密码", Toast.LENGTH_SHORT).show();
                }
                else{
                    student.setStudentID(account);
                    student.setPassWord(password);
                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl("http://116.204.121.9:61583/")
                            .addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit = builder.build();
                    Login_connector login_connetor = retrofit.create(Login_connector.class);
                    Call<Login_return> call = login_connetor.login(student);
                    call.enqueue(new Callback<Login_return>() {
                        @Override
                        public void onResponse(Call<Login_return> call, Response<Login_return> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(Login_LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                Intent intent = Main_MainActivity.newIntent(Login_LoginActivity.this);
                                startActivity(intent);
                                finish();
                                upload_User(response.body().getData());
                            } else {
                                Toast.makeText(getApplicationContext(), "身份认证失败，请重新登录", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Login_return> call, Throwable t) {
                            Toast.makeText(Login_LoginActivity.this, "网络或服务器错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

//                Toast.makeText(Login_LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
//                Intent intent = Main_MainActivity.newIntent(Login_LoginActivity.this);
//                startActivity(intent);
//                finish();
            }
        });
    }
    public void upload_User(String token) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://116.204.121.9:61583/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        User_connector mine_connector = retrofit.create(User_connector.class);
        Call<User_returnAll> call = mine_connector.getUserMes(token);

        call.enqueue(new Callback<User_returnAll>() {
            @Override
            public void onResponse(Call<User_returnAll> call, Response<User_returnAll> response) {
                User_returnAll user2 = response.body();
               User_return user1 = user2.getData();
                //  Toast.makeText(getApplicationContext(),"用户数据获取成功",Toast.LENGTH_SHORT).show();
                    user.setName(user1.getName());
                if (user1.getSelfWord() != "" && user1.getSelfWord() != null)
                    user.setSignature(user1.getSelfWord());
                user.setSex(user1.getSex());
                user.setToken(token);
                User_Now.getUserNow().setUser(user);
                user.setPortrait("http://mini-project.muxixyz.com/drifting/user_avatar/"+
                        User_Now.getUserNow().getUser().getId()+"myicon.JPG");
            }

            @Override
            public void onFailure(Call<User_returnAll> call, Throwable t) {

            }
        });
    }
}