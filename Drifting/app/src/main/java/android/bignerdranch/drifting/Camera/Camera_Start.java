package android.bignerdranch.drifting.Camera;

import android.app.Activity;
import android.bignerdranch.drifting.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.net.URL;
import java.util.List;

/**
 * 拍摄前的一个界面，放置图片
 */
public class Camera_Start extends AppCompatActivity {
    ImageView photo1;
    ImageView photo2;
    ImageView photo3;
    ImageView photo4;
    final String PHOTO_RETURN = "photo_return";
    boolean p = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_start);
        //Camera_ camera = Camera_ready_create.camera_ready_create.getCamera();
        if (p == false) {
            photo1 = (ImageView) findViewById(R.id.photo1);
            photo2 = (ImageView) findViewById(R.id.photo2);
            photo3 = (ImageView) findViewById(R.id.photo3);
            photo4 = (ImageView) findViewById(R.id.photo4);
        }
        ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    photo1.setImageResource(R.drawable.try2);
                    photo2.setImageResource(R.drawable.try3);
                    photo3.setImageResource(R.drawable.try4);
                    Uri uri1 = Uri.parse(result.getData().getStringExtra(PHOTO_RETURN));
                    photo4.setImageURI(uri1);
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
                finish();
            }
        });
    }
//    private List<URL> GetPicture(Camera_ camera){
//
//    }
}