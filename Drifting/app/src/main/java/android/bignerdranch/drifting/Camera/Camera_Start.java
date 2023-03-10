package android.bignerdranch.drifting.Camera;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bignerdranch.drifting.Main.Main_MainActivity;
import android.bignerdranch.drifting.Mine.FileUtils;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.User_Now;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 拍摄前的一个界面，放置图片
 */
public class Camera_Start extends AppCompatActivity {
    List<Bitmap> bitmapslist = new ArrayList<>();
    ImageView photo1;
    ImageView photo2;
    ImageView photo3;
    ImageView photo4;
    static ArrayList<String> camera_picture_name = new ArrayList<>();
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 4) {
                photo1.setImageBitmap(bitmapslist.get(0));
                photo2.setImageBitmap(bitmapslist.get(1));
                photo3.setImageBitmap(bitmapslist.get(2));
                photo4.setImageBitmap(bitmapslist.get(3));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Integer id = (Integer) getIntent().getIntExtra("camera_id", 2);
        setContentView(R.layout.camera_start);
        photo1 = (ImageView) findViewById(R.id.photo1);
        photo2 = (ImageView) findViewById(R.id.photo2);
        photo3 = (ImageView) findViewById(R.id.photo3);
        photo4 = (ImageView) findViewById(R.id.photo4);

        SetCameraMes(id, User_Now.getUserNow().getUser().getToken());
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    GetPicture();
                } catch (Exception e) {
                    Log.d("camera_bug", "获取图片错误");
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 500);


        ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    try {
                        GetPicture();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Button mStartCameraButton = (Button) findViewById(R.id.start_camera);
        Button mReturnButton = (Button) findViewById(R.id.return_button);
        mStartCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Camera_Start.this, Camera_Activity.class);
                launcher.launch(intent);
            }
        });
        mReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Camera_Start.this, Main_MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void GetPicture() {
        if (camera_picture_name.size() == 0) {
        } else if (camera_picture_name.size() > 0 && camera_picture_name.size() < 4)
            switch (camera_picture_name.size()) {
                case 1: {
                    new Thread("getphoto1") {
                        @Override
                        public void run() {
                            try {
                                List<Bitmap> list = new ArrayList<>();
                                Log.d("camera_bug", "开始一次请求");
                                list.add(FileUtils.getImage("http://" + camera_picture_name.get(0), FileUtils.PICTURE));
                                Log.d("camera_bug", "获取成功");
                                bitmapslist = list;
                                Message message = new Message();
                                message.what = 1;
                                handler.sendMessage(message);
                            } catch (Exception e) {
                                Log.d("camera_bug", "发生错误");
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    break;
                }
                case 2: {
                    new Thread("getphoto2") {
                        @Override
                        public void run() {
                            try {
                                List<Bitmap> list = new ArrayList<>();
                                Log.d("camera_bug", "开始一次请求");
                                list.add(FileUtils.getImage("http://" + camera_picture_name.get(0), FileUtils.PICTURE));
                                Log.d("camera_bug", "获取成功");
                                Log.d("camera_bug", "开始二次请求");
                                list.add(FileUtils.getImage("http://" + camera_picture_name.get(1), FileUtils.PICTURE));
                                Log.d("camera_bug", "获取成功");
                                bitmapslist = list;
                                Message message = new Message();
                                message.what = 2;
                                handler.sendMessage(message);
                            } catch (Exception e) {
                                Log.d("camera_bug", "发生错误");
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    break;
                }
                case 3: {
                    new Thread("getphoto3") {
                        @Override
                        public void run() {
                            try {
                                List<Bitmap> list = new ArrayList<>();
                                Log.d("camera_bug", "开始一次请求");
                                list.add(FileUtils.getImage("http://" + camera_picture_name.get(0), FileUtils.PICTURE));
                                Log.d("camera_bug", "获取成功");
                                Log.d("camera_bug", "开始二次请求");
                                list.add(FileUtils.getImage("http://" + camera_picture_name.get(1), FileUtils.PICTURE));
                                Log.d("camera_bug", "获取成功");
                                Log.d("camera_bug", "开始三次请求");
                                list.add(FileUtils.getImage("http://" + camera_picture_name.get(2), FileUtils.PICTURE));
                                Log.d("camera_bug", "获取成功");
                                bitmapslist = list;
                                Message message = new Message();
                                message.what = 3;
                                handler.sendMessage(message);
                            } catch (Exception e) {
                                Log.d("camera_bug", "发生错误");
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    break;
                }
                default:
                    break;
            }
        else if (camera_picture_name.size() >= 4) {
            new Thread("getphoto4") {
                @Override
                public void run() {
                    try {
                        int p = camera_picture_name.size();
                        Log.d("camera_bug", "开始一次请求");
                        bitmapslist.add(FileUtils.getImage("http://" + camera_picture_name.get(p - 4), FileUtils.PICTURE));
                        Log.d("camera_bug", "获取成功");
                        Log.d("camera_bug", "开始二次请求");
                        bitmapslist.add(FileUtils.getImage("http://" + camera_picture_name.get(p - 3), FileUtils.PICTURE));
                        Log.d("camera_bug", "获取成功");
                        Log.d("camera_bug", "开始三次请求");
                        bitmapslist.add(FileUtils.getImage("http://" + camera_picture_name.get(p - 2), FileUtils.PICTURE));
                        Log.d("camera_bug", "获取成功");
                        Log.d("camera_bug", "开始四次请求");
                        bitmapslist.add(FileUtils.getImage("http://" + camera_picture_name.get(p - 1), FileUtils.PICTURE));
                        Log.d("camera_bug", "获取成功");
                        Message message = new Message();
                        message.what = 4;
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        Log.d("camera_bug", "发生错误");
                        e.printStackTrace();
                    }
                }
            }.start();

        }
    }

    public void SetCameraMes(Integer id, String token) {
        List<String> list = new ArrayList<>();
        Camera_ camera = new Camera_();
        Camera_return_upload.Camera_upload_mes_body body =
                new Camera_return_upload.Camera_upload_mes_body();
        body.setid(id);
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://116.204.121.9:61583/");
        Retrofit retrofit = builder.build();
        Camera_connector camera_connector = retrofit.create(Camera_connector.class);
        Call<Camera_return_upload.Camera_return_mes_body> call =
                camera_connector.GetCamera_mes_body(body, token);
        call.enqueue(new Callback<Camera_return_upload.Camera_return_mes_body>() {
            @Override
            public void onResponse(Call<Camera_return_upload.Camera_return_mes_body> call, Response<Camera_return_upload.Camera_return_mes_body> response) {
                camera.setName(response.body().getData().getName());
                List<Camera_return_upload.Camera_return_mes_body3> bodyreturn = response.body().getData().getContacts();
                String c = Integer.toString(bodyreturn.size());
                int i;
                for (i = 0; i < bodyreturn.size(); i++) {
                    camera_picture_name.add(bodyreturn.get(i).getThe_words());
                }
                Log.d("camera_bug_message", Integer.toString(camera_picture_name.size()));
            }

            @Override
            public void onFailure(Call<Camera_return_upload.Camera_return_mes_body> call, Throwable t) {
                Log.d("message_of_camerabody", t.toString());
            }
        });
        Log.d("message_camera", Integer.toString(camera.getPhotoLab().size()));
    }
}