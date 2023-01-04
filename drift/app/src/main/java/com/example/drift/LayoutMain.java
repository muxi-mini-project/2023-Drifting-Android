package com.example.drift;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class LayoutMain extends AppCompatActivity {


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
        mGeren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LayoutMain.this, geren_Activity.class);
                startActivity(intent);
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
                Intent intent = new Intent(LayoutMain.this, yaoqing_camera.class);
                startActivity(intent);
            }
        });
        mSheZhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFragment(createshezhiFragment());
            }
        });
    }

    public void startFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();//创建并获取一个Fragment管理器
        if (fragment == null)
            fragment = fm.findFragmentById(R.id.main_container);
        fm.beginTransaction()
                .add(R.id.main_container, fragment)
                .commit();
    }
}

