package android.bignerdranch.drifting.Drawing;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.bignerdranch.drifting.Camera.Camera_Activity;
import android.bignerdranch.drifting.Camera.Camera_Start;
import android.bignerdranch.drifting.Camera.Camera_ZoomImageView;
import android.bignerdranch.drifting.R;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Drawing_Album extends AppCompatActivity {
    private ImageButton back;
    private Button album;
    private Button confirm;
    private Button change;
    Camera_ZoomImageView picture;
    private Uri imageUri;
    final String PHOTO_RETURN = "photo_return";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawing_album);
        back = (ImageButton) findViewById(R.id.back);
        album = (Button) findViewById(R.id.album);
        change = (Button) findViewById(R.id.change_button);
        confirm = (Button) findViewById(R.id.commit_button);
        picture = (Camera_ZoomImageView) findViewById(R.id.picture);

        ActivityResultLauncher<Intent> launcher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    // 从相册返回的数据
                    if (result.getData() != null) {
                        // 得到图片的全路径
                        Uri uri = result.getData().getData();
                        imageUri = uri;
                        picture.setImageURI(uri);
                        album.setVisibility(View.INVISIBLE);
                        change.setVisibility(View.VISIBLE);
                        confirm.setVisibility(View.VISIBLE);
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

        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                launcher2.launch(intent);
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}