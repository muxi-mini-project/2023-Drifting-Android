package com.example.drift;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * 暂时无用的接口
 */
public abstract class SingleFragmentActvity extends AppCompatActivity {
    protected abstract Fragment createFragment();//创建一个空的Fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//显示land_main.xml中的视图

        FragmentManager fm = getSupportFragmentManager();//创建并获取一个Fragment管理器
        Fragment fragment = fm.findFragmentById(R.id.main_container);//向FragmentMAnager请求并获取fragment

        if (fragment == null)
            fragment = createFragment();
        fm.beginTransaction()
                .add(R.id.main_container, fragment)//容器视图资源ID，需要绑定的Fragment
                .commit();//创建一个新的fragment事物，执行一个fragment添加操作后提交事务
    }
}
