package android.bignerdranch.drifting.Camera;

import android.app.AlertDialog;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.User_Now;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Camera_setting_stranger extends AppCompatActivity {
    final int KIND = 0;
    EditText mName;
    EditText mTitle;
    EditText mNumber;
    String name;
    String theme;
    Long number;
    ImageView iv_image;
    String mCoverURL;
    Button mSelectCover;
    Button mStart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drifting_stranger_mode);
        updateUI();
        mName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = mName.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                theme = mTitle.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                number = Integer.valueOf(mNumber.getText().toString()).longValue();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mSelectCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowChoices();
            }
        });
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number > 9 || number <= 0||theme == null||name == null||mCoverURL == null) {
                    Toast.makeText(getApplicationContext(), "请输入正确的数据或选择封面", Toast.LENGTH_SHORT).show();
                } else {
                    Camera_return_upload.Camera_upload_create upload_create = new Camera_return_upload.Camera_upload_create();
                    upload_create.setName(name);
                    upload_create.setCover(mCoverURL);
                    upload_create.setKind(KIND);
                    upload_create.setTheme(theme);
                    upload_create.setNumber(new Long(number).intValue());
                    Retrofit.Builder builder = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl("http://116.204.121.9:61583/");
                    Retrofit retrofit = builder.build();
                    Camera_connector camera_connector = retrofit.create(Camera_connector.class);
                    Call<Camera_return_upload.Camera_return_create> call = camera_connector.CreateCamera_new(upload_create, User_Now.getUserNow().getUser().getToken());
                    call.enqueue(new Callback<Camera_return_upload.Camera_return_create>() {
                        @Override
                        public void onResponse(Call<Camera_return_upload.Camera_return_create> call, Response<Camera_return_upload.Camera_return_create> response) {
                            Toast.makeText(getApplicationContext(),"创建成功，现在开始创作吧！",Toast.LENGTH_SHORT).show();
                            Camera_ camera = new Camera_(name,theme,number,mCoverURL,new Long(1));
                            Camera_ready_create.getCamera_ready_create().setCamera(camera);
                            Intent intent = new Intent(Camera_setting_stranger.this,Camera_Start.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<Camera_return_upload.Camera_return_create> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),t.getMessage().toString(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void updateUI() {
        mName = (EditText) findViewById(R.id.name_text);
        mTitle = (EditText) findViewById(R.id.title_text);
        mNumber = (EditText) findViewById(R.id.person_number_text);
        iv_image = (ImageView) findViewById(R.id.iv_image);
        mSelectCover = (Button) findViewById(R.id.stranger_choose_front_cover);
        mStart = (Button) findViewById(R.id.stranger_start);
    }

    private void ShowChoices() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Holo_Light_Dialog);
        //    指定下拉列表的显示数据
        final String[] choices = {"封面1", "封面2", "封面3", "封面4", "封面5", "封面6", "封面7", "封面8"};
        //    设置一个下拉的列表选择项
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: {
                        iv_image.setImageResource(R.drawable.cover_1);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/点构图.jpg";
                        break;
                    }
                    case 1: {
                        iv_image.setImageResource(R.drawable.cover_2);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/抓拍，广场上玩滑板的少年.jpg";
                        break;
                    }
                    case 2: {
                        iv_image.setImageResource(R.drawable.cover_3);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/三分线，冷清的广场.jpg";
                        break;
                    }
                    case 3: {
                        iv_image.setImageResource(R.drawable.cover_4);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/三分线构图.jpg";
                        break;
                    }
                    case 4: {
                        iv_image.setImageResource(R.drawable.cover_5);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/4.jpg";
                        break;
                    }
                    case 5: {
                        iv_image.setImageResource(R.drawable.cover_6);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/3.jpg";
                        break;
                    }
                    case 6: {
                        iv_image.setImageResource(R.drawable.cover_7);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/2.jpg";
                        break;
                    }
                    case 7: {
                        iv_image.setImageResource(R.drawable.cover_8);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/1.jpg";
                        break;
                    }
                }
            }
        });
        builder.show();
    }
}
