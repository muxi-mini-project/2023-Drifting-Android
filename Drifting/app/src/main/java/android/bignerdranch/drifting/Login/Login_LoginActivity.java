package android.bignerdranch.drifting.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.bignerdranch.drifting.Main.Main_MainActivity;
import android.bignerdranch.drifting.Mine.FileUtils;
import android.bignerdranch.drifting.Mine.GetAllItems;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.AllItems;
import android.bignerdranch.drifting.User.User_;
import android.bignerdranch.drifting.User.User_Now;
import android.bignerdranch.drifting.User.User_connector;
import android.bignerdranch.drifting.User.User_return;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 登录界面
 */
public class Login_LoginActivity extends AppCompatActivity {
    private EditText mAccountText;
    private EditText mPasswordText;
    private Button mLoginButton;
    private Integer account;
    private String password;
    static String token;
    User_ user = new User_();

    public static class User_returnAll {
        private User_return data;

        public User_return getData() {
            return data;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 200) {
                Intent intent = Main_MainActivity.newIntent(Login_LoginActivity.this);
                startActivity(intent);
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        File file = new File(getFilesDir().getAbsolutePath());
        File drifting = new File(file, "Drifting");
        File target = new File(drifting, "mytoken.txt");
        if (target.exists()) {
            try {
                FileInputStream fis = new FileInputStream(target.getAbsolutePath());
                byte[] b = new byte[fis.available()];
                fis.read(b);
                String readStr = new String(b);
                upload_User(readStr);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                account = Integer.valueOf(mAccountText.getText().toString());
                user.setId(account);
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
                if (account == null || password == null) {
                    Toast.makeText(Login_LoginActivity.this, "请输入账号或密码", Toast.LENGTH_SHORT).show();
                } else {
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
                            token = response.body().getData();
                            if (response.isSuccessful()) {
                                Toast.makeText(Login_LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                try {
                                    FileUtils.savetoken(getApplicationContext(), response.body().getData(), "mytoken.txt", "Drifting");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
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
            }
        });
    }

    //    public void GetItems(){
//        AllItems.getAllItems().setCamera_name_user(GetAllItems.getGetAllItems().GetCamera_ownercrea_name());
//        AllItems.getAllItems().getCamera_nowuser_user(GetAllItems.getGetAllItems().GetCamera_ownercrea_nowuser());
//    }
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
                User_return user1 = response.body().getData();
                //  Toast.makeText(getApplicationContext(),"用户数据获取成功",Toast.LENGTH_SHORT).show();
                user.setName(user1.getName());
                if (user1.getSelfWord() != "" && user1.getSelfWord() != null)
                    user.setSignature(user1.getSelfWord());
                user.setSex(user1.getSex());
                user.setToken(token);
                user.setId(user1.getStudentID().intValue());
                user.setPortrait("http://" + user1.getAvatar());
                try {
                    FileUtils.getImage(user.getPortrait(), FileUtils.AVATAR);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                User_Now.getUserNow().setUser(user);
                Message message = new Message();
                message.what = 200;
                handler.sendMessage(message);
                //  GetItems();
            }

            @Override
            public void onFailure(Call<User_returnAll> call, Throwable t) {

            }
        });
    }

    public static String getToken() {
        return token;
    }
}