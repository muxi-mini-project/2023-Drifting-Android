package android.bignerdranch.drifting.Camera;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.bignerdranch.drifting.Mine.FileUtils;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.User_Now;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Camera_Activity extends AppCompatActivity {
    Button mTakephotoButton;
    Button mPhotoButton;
    Camera_ZoomImageView picture;
    private Uri imageUri;//获取到的uri
    private String FileUri;//本地保存的地址
    Integer id;//传来的项目id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_paishe);
        id = getIntent().getIntExtra("camera_id", 2);
        Log.d("camera_bug", id.toString());

        ActivityResultLauncher launcher1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {//这里的result即为传回来的intent型变量
                if (result.getResultCode() == RESULT_OK) {
                    //将拍摄的照片显示出来
                    Bitmap bitmap = null;
                    try {
                        bitmap = BitmapFactory.decodeStream(getApplicationContext().getContentResolver().openInputStream(imageUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    picture.setImageBitmap(bitmap);
                    try {
                        FileUri = FileUtils.saveFile(getApplicationContext(), bitmap, new Date().getTime() + ".png", "camera_data");
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("camera_bug", "图片保存失败");
                    }
                    mTakephotoButton.setText("确定");
                    mPhotoButton.setText("更换");
                }
            }
        });
        ActivityResultLauncher launcher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    // 从相册返回的数据
                    if (result.getData() != null) {
                        // 得到图片的全路径
                        Uri uri = result.getData().getData();
                        imageUri = uri;
                        Bitmap bitmap = null;
                        try {
                            bitmap = BitmapFactory.decodeStream(getApplicationContext().getContentResolver().openInputStream(uri));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    picture.setImageBitmap(bitmap);
                        try {
                            FileUri = FileUtils.saveFile(getApplicationContext(), bitmap, new Date().getTime() + ".png", "camera_data");
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("camera_bug", "图片保存失败");
                        }
                        mTakephotoButton.setText("确定");
                        mPhotoButton.setText("更换");
                    }
                }

            }
        });

        picture = (Camera_ZoomImageView) findViewById(R.id.picture);
        mTakephotoButton = (Button) findViewById(R.id.camera_button);
        mPhotoButton = (Button) findViewById(R.id.album);

        mTakephotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTakephotoButton.getText().equals("拍摄")) {
                    File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                    try {
                        if (outputImage.exists()) {
                            outputImage.delete();
                        }
                        outputImage.createNewFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (Build.VERSION.SDK_INT >= 24) {
                        imageUri = FileProvider.getUriForFile(Camera_Activity.this,
                                "com.example.drift.fileprovider", outputImage);
                    } else {
                        imageUri = Uri.fromFile(outputImage);
                    }
                    //启动相机程序
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    launcher1.launch(intent);

                } else if (mTakephotoButton.getText().equals("确定")) {
                    uploadPicture(FileUri, User_Now.getUserNow().getUser().getToken(), id);
                }
            }
        });
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPhotoButton.getText().equals("相册")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    launcher2.launch(intent);
                } else if (mPhotoButton.getText().equals("更换")) {
                    mTakephotoButton.setText("拍摄");
                    mPhotoButton.setText("相册");
                }
            }
        });
    }

    private void uploadPicture(String fileuri, String token, Integer id) {
        File file = new File(fileuri);
        Log.d("camera_bug", "开始上传");
        RequestBody requestBody = RequestBody.create(file, MediaType.parse("multipart/form-data"));
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("picture", file.getName(), requestBody);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://116.204.121.9:61583/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        Camera_connector camera_connector = retrofit.create(Camera_connector.class);
        Log.d("camera_bug", id.toString() + "/" + User_Now.getUserNow().getUser().getToken() + "/");
        Call<Camera_return_upload.Camera_return_make> call = camera_connector.MakeCamera(body, new Long(id), token);
        call.enqueue(new Callback<Camera_return_upload.Camera_return_make>() {
            @Override
            public void onResponse(Call<Camera_return_upload.Camera_return_make> call, Response<Camera_return_upload.Camera_return_make> response) {
                if (response.isSuccessful()) {
                    Log.d("camera_bug", "图片上传成功");
                    Intent intent = new Intent(Camera_Activity.this, Camera_Start.class);
                    intent.putExtra("camera_id", id);
                    startActivity(intent);
                    finish();
                    file.delete();
                } else
                    Log.d("camera_bug", "上传失败");
            }

            @Override
            public void onFailure(Call<Camera_return_upload.Camera_return_make> call, Throwable t) {
                Log.d("camera_bug", t.toString());
            }
        });
    }
}