package android.bignerdranch.drifting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

/**
 * 漂流相机项目界面，展示项目信息
 */
public class Camera_inviting extends AppCompatActivity {
 //   Camera_ mDrift_camera;//当前漂流相机项目
    User_ mUser;
    Button mStartButton;
    Button mFangqiButton;
    TextView mName;
    TextView mTitle;
    TextView minitiator;
    TextView mTime;
    TextView mTimeofone;
    TextView mAllUser;
    final String GET_USER = "user_id";
    final String GET_DRIFT_CAMERA = "drift_camera_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_inviting);

        mUser = User_Lab.get(getBaseContext()).getUser((UUID)getIntent().getSerializableExtra(GET_USER));
      //  mDrift_camera = Camera_Lab.get(getBaseContext()).getdrift_camera((UUID)getIntent().getSerializableExtra(GET_DRIFT_CAMERA));

        updateUI();
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Camera_inviting.this, Camera_Start.class);
               // intent.putExtra(GET_DRIFT_CAMERA,mDrift_camera.getId());
                startActivity(intent);
            }
        });

        mFangqiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Camera_inviting.this, Main_MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void updateUI(){
        mStartButton = (Button) findViewById(R.id.start_button);
        mFangqiButton = (Button) findViewById(R.id.fangqi_button);
        mName = (TextView)findViewById(R.id.camera_name_text);
        minitiator = (TextView) findViewById(R.id.camera_initiator_text);
        mTime = (TextView) findViewById(R.id.camera_time_text);
        mTitle = (TextView) findViewById(R.id.camera_title_text);
        mTimeofone = (TextView) findViewById(R.id.camera_timeofone_text);
        mAllUser = (TextView) findViewById(R.id.camera_alluser_text);

    //    mName.setText(mDrift_camera.getName());
     //   minitiator.setText(minitiator.getText()+mDrift_camera.getinitiator(getBaseContext()));
     //   mTitle.setText(mTitle.getText()+mDrift_camera.getTitle());
     //  mTime.setText(mTime.getText()+mDrift_camera.getDate());
     //  mTimeofone.setText(mTimeofone.getText()+mDrift_camera.getTimeofone());
      //  mAllUser.setText(mAllUser.getText()+"("+mDrift_camera.getParticipator().size()+"位):");
    }
}