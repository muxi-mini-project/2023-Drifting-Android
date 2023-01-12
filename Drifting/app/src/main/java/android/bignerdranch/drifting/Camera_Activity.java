package android.bignerdranch.drifting;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;

public class Camera_Activity extends AppCompatActivity {
    Button mTakephotoButton;
    Button mPhotoButton;
    Camera_ZoomImageView picture;
    private Uri imageUri;
    public static final int TAKE_PHOTO = 1;
    final String PHOTO_RETURN = "photo_return";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_paishe);

        ActivityResultLauncher launcher1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {//这里的result即为传回来的intent型变量
                if (result.getResultCode() == RESULT_OK) {
                    //将拍摄的照片显示出来
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                        mTakephotoButton.setText("确定");
                        mPhotoButton.setText("更换");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
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
                        picture.setImageURI(uri);
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
                    Intent intent = new Intent(Camera_Activity.this, Camera_Start.class);
                    intent.putExtra(PHOTO_RETURN, imageUri.toString());
                    setResult(RESULT_OK, intent);
                    finish();
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
}