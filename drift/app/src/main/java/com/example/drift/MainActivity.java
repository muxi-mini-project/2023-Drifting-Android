package com.example.drift;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.drift.ui.login.LoginFragment;

public class MainActivity extends AppCompatActivity {
    private Button mloginButton;
    private LinearLayout mbackground;

    FragmentManager fm = getSupportFragmentManager();//创建并获取一个Fragment管理器
    Fragment fragment = fm.findFragmentById(R.id.login_frame);//向FragmentMAnager请求并获取fragment

    protected Fragment createFragment() {
        return new LoginFragment();
    }//创建一个空的Fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//显示land_main.xml中的视图


        mloginButton = (Button) findViewById(R.id.login_button);
        mbackground = (LinearLayout) findViewById(R.id.login_background);
        mloginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "111", Toast.LENGTH_SHORT).show();
                mbackground.getBackground().mutate().setAlpha(200);
                mloginButton.setVisibility(View.INVISIBLE);
                if (fragment == null)
                    fragment = createFragment();
                fm.beginTransaction()
                        .add(R.id.login_frame, fragment)//容器视图资源ID，需要绑定的Fragment
                        .commit();//创建一个新的fragment事物，执行一个fragment添加操作后提交事务
            }
        });
    }
}

//新建分支git checkout -b 分支名
//克隆仓库git clone SSH
//切换分支git checkout 分支名
//提交git push -o origin 分支名