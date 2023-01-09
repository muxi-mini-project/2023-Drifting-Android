package com.example.drift;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class LayoutMain extends AppCompatActivity {
    User mUser;
    final String FRAGMENT_KEY = "id";

    protected Fragment createshezhiFragment() {
        return new shezhiFragment();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);//显示land_main.xml中的视图
        ImageView mGeren = (ImageView) findViewById(R.id.geren_button);
        ImageView mHaoyou = (ImageView) findViewById(R.id.haoyou_button);
        ImageView mFaxian = (ImageView) findViewById(R.id.faxian_button);
        ImageView mSheZhi = (ImageView) findViewById(R.id.shezhi_button);
        updateUI();
        mGeren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserNow.getUserNow().getUser() != null) {
                    Intent intent;
                    intent = new Intent(LayoutMain.this, geren_Activity.class);
                    intent.putExtra(FRAGMENT_KEY, mUser.getUUID());
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
            }
        });
        mHaoyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mFaxian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserNow.getUserNow().getUser() != null) {
                    Intent intent = new Intent(LayoutMain.this, yaoqing_camera.class);
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "请先登录", Toast.LENGTH_SHORT);
            }
        });
        mSheZhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();//创建并获取一个Fragment管理器
                FragmentTransaction transaction = fm.beginTransaction();
                Fragment fragment = createshezhiFragment();
                Bundle args = new Bundle();
                args.putSerializable(FRAGMENT_KEY, mUser.getUUID());
                fragment.setArguments(args);
                if (fragment == null)
                    fragment = fm.findFragmentById(R.id.main_container);
                transaction
                        .add(R.id.main_container, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_CLOSE)
                        .commit();
            }
        });
    }

    private void updateUI() {
        UserLab mUserLab = UserLab.get(LayoutMain.this);
        if (mUser == null)
            mUser = mUserLab.NewUser();
        mUser.setName("张涵");
        UserNow.getUserNow().setUser(mUser);
    }
}

