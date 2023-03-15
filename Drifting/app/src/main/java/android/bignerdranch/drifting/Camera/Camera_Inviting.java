package android.bignerdranch.drifting.Camera;

import android.bignerdranch.drifting.Inviting.Loading.inviting_messageReturn;
import android.bignerdranch.drifting.Inviting.Loading.inviting_request;
import android.bignerdranch.drifting.Main.Main_MainActivity;
import android.bignerdranch.drifting.Mine.GetAllItems;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.GetNameFormIDRequest;
import android.bignerdranch.drifting.User.User_Now;
import android.bignerdranch.drifting.User.User_connector;
import android.bignerdranch.drifting.User.User_name_getFormID_return;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 受到邀请后点击进入该界面
 */
public class Camera_Inviting extends AppCompatActivity {
    Long id;//项目id
    String name;
    String theme;
    String owner;
    String time;
    int maxaccount;
    int nowaccount;
    Long ownerID;
    Button mAgreeButton;
    Button mFangqiButton;
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    TextView mNametext;
    TextView mThemetext;
    TextView mJindutext;
    TextView mOwnertext;
    TextView mTimetext;
    List<ImageView> images = new ArrayList<>();
    List<Camera_return_upload.Camera_return_mes_body3> contacts;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 200) {
                for (int i = 0; i < contacts.size(); i++)
                    Glide.with(Camera_Inviting.this).load("http://"+contacts.get(i).getThe_words()).into(images.get(i));
            mNametext.setText(name);
            mJindutext.setText("当前进度:"+nowaccount+"/"+maxaccount);
            mTimetext.setText("创建时间:"+time);
            mThemetext.setText("主题设定:"+theme);
            }else if(msg.what == 201){
                mOwnertext.setText(owner);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_inviting);
        mAgreeButton = (Button) findViewById(R.id.start_button);
        image1 = findViewById(R.id.camera_image1);
        image2 = findViewById(R.id.camera_image2);
        image3 = findViewById(R.id.camera_image3);
        image4 = findViewById(R.id.camera_image4);
        mNametext = findViewById(R.id.camera_name_text);
        mThemetext = findViewById(R.id.camera_theme_text);
        mTimetext = findViewById(R.id.camera_time_text);
        mJindutext = findViewById(R.id.camera_jindu_text);
        mOwnertext = findViewById(R.id.camera_owner_text);
        images.add(image1);
        images.add(image2);
        images.add(image3);
        images.add(image4);
        id = getIntent().getLongExtra("file_id", 0);
        contacts = new ArrayList<>();
        GetCameraMes(id.intValue());
        mAgreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Camera_return_upload.Camera_accept_inviting body = new Camera_return_upload.Camera_accept_inviting();
                body.setFileid(id);
                body.setFileKind("漂流相机");
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("http://116.204.121.9:61583/")
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();
                Camera_connector camera_connector = retrofit.create(Camera_connector.class);
                Call<Camera_return_upload.Camera_accept_inviting_return> call = camera_connector.AcceptCamera_inviting(body, User_Now.getUserNow().getUser().getToken());
                call.enqueue(new Callback<Camera_return_upload.Camera_accept_inviting_return>() {
                    @Override
                    public void onResponse(Call<Camera_return_upload.Camera_accept_inviting_return> call, Response<Camera_return_upload.Camera_accept_inviting_return> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(Camera_Inviting.this, "参与成功", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Camera_return_upload.Camera_accept_inviting_return> call, Throwable t) {

                    }
                });
                Camera_return_upload.Camera_upload_accept_random body1 = new Camera_return_upload.Camera_upload_accept_random();
                body1.setStudentID(new Long(User_Now.getUserNow().getUser().getId()));
                body1.setDriftingPictureID(id);
                Call<Camera_return_upload.Camera_return_accept_random> call1 = camera_connector.Camera_accept_random(body1, User_Now.getUserNow().getUser().getToken());
                call1.enqueue(new Callback<Camera_return_upload.Camera_return_accept_random>() {
                    @Override
                    public void onResponse(Call<Camera_return_upload.Camera_return_accept_random> call, Response<Camera_return_upload.Camera_return_accept_random> response) {
                        if (response.isSuccessful()) {
                            GetAllItems.getGetAllItems().refreshMessage();
                            Intent intent = new Intent(Camera_Inviting.this, Camera_Start.class);
                            intent.putExtra("camera_id", id.intValue());
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onFailure(Call<Camera_return_upload.Camera_return_accept_random> call, Throwable t) {
                        Toast.makeText(Camera_Inviting.this, "出现错误，请稍后重试", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        mFangqiButton = (Button) findViewById(R.id.fangqi_button);
        mFangqiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Camera_return_upload.Camera_upload_refuse_inviting body = new Camera_return_upload.Camera_upload_refuse_inviting();
                body.setFileid(id);
                body.setFileKind("漂流相机");
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("http://116.204.121.9:61583/")
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();
                Camera_connector camera_connector = retrofit.create(Camera_connector.class);
                Call<Camera_return_upload.Camera_return_refuse_inviting> call = camera_connector.Camera_refuse_inviting(body, User_Now.getUserNow().getUser().getToken());
                call.enqueue(new Callback<Camera_return_upload.Camera_return_refuse_inviting>() {
                    @Override
                    public void onResponse(Call<Camera_return_upload.Camera_return_refuse_inviting> call, Response<Camera_return_upload.Camera_return_refuse_inviting> response) {
                        Toast.makeText(Camera_Inviting.this, "已成功拒绝", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Camera_return_upload.Camera_return_refuse_inviting> call, Throwable t) {
                        Toast.makeText(Camera_Inviting.this, "出现错误，请稍后重试", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void GetCameraMes(Integer id) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://116.204.121.9:61583/");
        Retrofit retrofit = builder.build();
        inviting_request inviting_request = retrofit.create(android.bignerdranch.drifting.Inviting.Loading.inviting_request.class);
        Call<inviting_messageReturn> call = inviting_request.camera_request(User_Now.getUserNow().getUser().getToken());
        call.enqueue(new Callback<inviting_messageReturn>() {
            @Override
            public void onResponse(Call<inviting_messageReturn> call, Response<inviting_messageReturn> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getData() != null) {
                        List<inviting_messageReturn.data> data = response.body().getData();
                        for (int i = 0; i < data.size(); i++)
                            if (data.get(i).getFile_id() == id) {
                                name = data.get(i).getName();
                                theme = data.get(i).getTheme();
                                ownerID = data.get(i).getHoner_id();
                                nowaccount = data.get(i).getWriter_number();
                                maxaccount = data.get(i).getNumber();
                                time = cutTime(data.get(i).getCreatedAt());
                                Message message = new Message();
                                message.what = 200;
                                mHandler.sendMessage(message);
                                break;
                            }
                    }
                }
            }

            @Override
            public void onFailure(Call<inviting_messageReturn> call, Throwable t) {

            }
        });
    }
    private String cutTime(String s){
        String year = s.substring(0,4);
        String mouth = s.substring(5,7);
        String days = s.substring(8,10);
        return year+"年"+mouth+"月"+days+"日";
    }
    private void GetOwnerMes(Integer id,String token){
        GetNameFormIDRequest body = new GetNameFormIDRequest();
        body.setStudentID(id);
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://116.204.121.9:61583/");
        Retrofit retrofit = builder.build();
        User_connector connector = retrofit.create(User_connector.class);
        Call<android.bignerdranch.drifting.User.User_name_getFormID_return> call = connector.getHostName(body,User_Now.getUserNow().getUser().getToken());
        call.enqueue(new Callback<User_name_getFormID_return>() {
            @Override
            public void onResponse(Call<User_name_getFormID_return> call, Response<User_name_getFormID_return> response) {
                if(response.isSuccessful()){
                    owner = response.body().getData().getName();
                    Message message = new Message();
                    message.what = 201;
                    mHandler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(Call<User_name_getFormID_return> call, Throwable t) {

            }
        });
    }
}