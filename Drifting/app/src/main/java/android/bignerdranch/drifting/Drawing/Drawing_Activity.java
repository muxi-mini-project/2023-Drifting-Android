package android.bignerdranch.drifting.Drawing;

import android.bignerdranch.drifting.Camera.Camera_ZoomImageView;
import android.bignerdranch.drifting.Login.Login_LoginActivity;
import android.bignerdranch.drifting.Mine.GetAllItems;
import android.bignerdranch.drifting.R;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Drawing_Activity extends AppCompatActivity {
    private Button mPhotoButton;
    private Button commit;
    private Button change;
    private ImageButton back;
    private Camera_ZoomImageView picture;
    private String path;
    private long file_id;
    private static final OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(60, TimeUnit.SECONDS).
            readTimeout(60, TimeUnit.SECONDS).
            writeTimeout(60, TimeUnit.SECONDS).build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawing_activity);
        picture = (Camera_ZoomImageView) findViewById(R.id.picture);
        mPhotoButton = (Button) findViewById(R.id.album);
        commit = (Button) findViewById(R.id.commit);
        change = (Button) findViewById(R.id.change);
        back = (ImageButton) findViewById(R.id.back);
        file_id = getIntent().getLongExtra("file_id",0);

        ActivityResultLauncher<Intent> launcher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    // 从相册返回的数据
                    if (result.getData() != null) {
                        // 得到图片的全路径
                        Uri uri0 = result.getData().getData();
                        path = getRealPathFromURI(uri0);
                        picture.setImageURI(uri0);
                        commit.setVisibility(View.VISIBLE);
                        change.setVisibility(View.VISIBLE);
                        mPhotoButton.setVisibility(View.INVISIBLE);
                    }
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                launcher2.launch(intent);
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(path);

                RequestBody requestFile = RequestBody.create(file,MediaType.parse("image/*"));
                ArrayList<MultipartBody.Part> body = new ArrayList<>();
                body.add(MultipartBody.Part.createFormData("picture", file.getName(), requestFile));
                body.add(MultipartBody.Part.createFormData("file_id",String.valueOf(file_id)));
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("http://116.204.121.9:61583/")
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();
                Drawing_Api drawingApi = retrofit.create(Drawing_Api.class);
                Call<create_return> call = drawingApi.upload(body,Login_LoginActivity.getToken());
                call.enqueue(new Callback<create_return>() {
                    @Override
                    public void onResponse(Call<create_return> call, Response<create_return> response) {
                        create_return create_return = response.body();
                        if(create_return.getCode() == 200){
                            Toast.makeText(Drawing_Activity.this, "上传成功", Toast.LENGTH_SHORT).show();
                            GetAllItems.getGetAllItems().refreshMessage();
                            finish();
                        }else{
                            Toast.makeText(Drawing_Activity.this, "上传出现问题，请稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<create_return> call, Throwable t) {
                        Toast.makeText(Drawing_Activity.this, "上传出现问题，请稍后重试", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                launcher2.launch(intent);
            }
        });
    }
    private String getRealPathFromURI(Uri contentUri) { //传入图片uri地址
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}