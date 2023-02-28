package android.bignerdranch.drifting.Drawing;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.bignerdranch.drifting.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Drawing_Activity extends AppCompatActivity {

    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;
    private ImageView mImageView4;
    private Button commit;
    private Button refuse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawing_inviting);

        mImageView1 = (ImageView) findViewById(R.id.mImageview1);
        mImageView2 = (ImageView) findViewById(R.id.mImageview2);
        mImageView3 = (ImageView) findViewById(R.id.mImageview3);
        mImageView4 = (ImageView) findViewById(R.id.mImageview4);
        commit = (Button) findViewById(R.id.commit_button);
        refuse = (Button) findViewById(R.id.refuse_button);

        ActivityResultLauncher launcher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    // 从相册返回的数据
                    if (result.getData() != null) {
                        // 得到图片的全路径
                        Uri uri = result.getData().getData();
//                        imageUri = uri;
//                        picture.setImageURI(uri);
//                        mTakephotoButton.setText("确定");
//                        mPhotoButton.setText("更换");
                    }
                }

            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                launcher2.launch(intent);
            }
        });
        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}