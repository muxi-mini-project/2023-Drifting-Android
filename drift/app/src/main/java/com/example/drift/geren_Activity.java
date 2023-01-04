package com.example.drift;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class geren_Activity extends AppCompatActivity {
    private boolean finish = false;
    FragmentManager fm = getSupportFragmentManager();//创建并获取一个Fragment管理器
    Fragment fragment = fm.findFragmentById(R.id.container2);//向FragmentMAnager请求并获取fragment
    Fragment fragment2 = createFragment2();
    User mUser;
    static final String FRAGMENT_KEY = "id";
    Bundle args = new Bundle();


    protected Fragment createFragment1() {
        return new gerenFragment();
    }

    protected Fragment createFragment2() {
        return new gerenbianjiFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateUI();
        setContentView(R.layout.activity_geren);//显示land_main.xml中的视图


        if (fragment == null)
            fragment = createFragment1();


        args.putSerializable(FRAGMENT_KEY,mUser.getUUID());
        fragment.setArguments(args);

        fm.beginTransaction()
                .add(R.id.container2, fragment)//容器视图资源ID，需要绑定的Fragment
                .commit();//创建一个新的fragment事物，执行一个fragment添加操作后提交事务
        Button mZiliaoButton = (Button) findViewById(R.id.ziliao_button);
        mZiliaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finish == false) {
                    FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
                    if (!fragment2.isAdded()) {
                        mTransaction.add(R.id.container2, fragment2);
                        mTransaction.replace(R.id.container2, fragment2);
                    } else
                        mTransaction.replace(R.id.container2, fragment2);
                    mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_CLOSE);
                    finish = true;
                    mZiliaoButton.setText("完成");

                    fragment2.setArguments(args);

                    mTransaction.commit();
                }
                else if(finish == true){
                    FragmentTransaction mTransaction = getSupportFragmentManager().beginTransaction();
                    mTransaction.replace(R.id.container2,fragment);
                    mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    finish = false;
                    mZiliaoButton.setText("编辑");
                    mTransaction.commit();
                }
            }
        });
    }
    private void updateUI(){
        UserLab mUserLab = UserLab.get(geren_Activity.this);
        if(mUser ==null)
            mUser = mUserLab.NewUser();
        mUser.setName("张章");
    }
}


