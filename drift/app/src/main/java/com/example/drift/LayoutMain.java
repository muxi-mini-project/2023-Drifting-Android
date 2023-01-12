package com.example.drift;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * 登陆后主界面
 */
public class LayoutMain extends AppCompatActivity {

    FragmentManager fm = getSupportFragmentManager();//创建并获取一个Fragment管理器
    Fragment fragment = fm.findFragmentById(R.id.main_container);//向FragmentMAnager请求并获取fragment
    Fragment fragment5 = new shezhiFragment();
    Bundle args = new Bundle();
    int ctor = 0;
    //用于确定当前fragment以在开启下一个fragment时关闭当前fragment：
    // 1：个人界面、2：好友界面、3：发现界面、4：设置界面

    User mUser;

    final String GET_USER = "user_id";
    final String GET_DRIFT_CAMERA = "drift_camera_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);//显示land_main.xml中的视图
        ImageButton mGeren = (ImageButton) findViewById(R.id.geren_button);
        ImageButton mHaoyou = (ImageButton) findViewById(R.id.haoyou_button);
        ImageButton mFaxian = (ImageButton) findViewById(R.id.faxian_button);
        ImageButton mSheZhi = (ImageButton) findViewById(R.id.shezhi_button);
        // 带回授权结果
        updateUI();
        /**
         * 跳转个人界面
         */
        mGeren.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                if(ctor != 1){
                HideLastFragment();
                if (UserNow.getUserNow().getUser() != null) {

                    if (fragment == null)
                        fragment = new gerenFragment();

                    if (fragment.getArguments() == null)
                        fragment.setArguments(args);
                    if (!fragment.isAdded())
                        fm.beginTransaction()
                                .add(R.id.main_container, fragment)//容器视图资源ID，需要绑定的Fragment
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit();//创建一个新的fragment事物，执行一个fragment添加操作后提交事务
                    else
                        fm.beginTransaction().show(fragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit();


                } else
                    Toast.makeText(getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
                ctor = 1;
            }}
        });
        /**
         * 跳转好友界面
         */
        mHaoyou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ctor != 2){
                HideLastFragment();
                ctor = 2;
                }
            }
        });
        /**
         * 跳转发现界面
         */
        mFaxian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ctor != 3){
                HideLastFragment();
                if (UserNow.getUserNow().getUser() != null) {
                    Intent intent = new Intent(LayoutMain.this, yaoqing_camera.class);
                    intent.putExtra(GET_USER, mUser.getUUID());
                    intent.putExtra(GET_DRIFT_CAMERA, drift_cameraLab.get(getBaseContext())
                            .getdrift_camera(mUser.getMydrift_cameraLab().get(0)).getId());
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), "请先登录", Toast.LENGTH_SHORT);
                ctor = 3;
            }
            }
        });
        /**
         * 跳转设置界面
         */
        mSheZhi.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                if(ctor != 4){
                HideLastFragment();
                FragmentTransaction transaction = fm.beginTransaction();
                Bundle args = new Bundle();
                args.putSerializable(GET_USER, mUser.getUUID());
                fragment5.setArguments(args);
                if (fragment5 == null)
                    fragment5 = fm.findFragmentById(R.id.main_container);
                if (!fragment5.isAdded())
                    transaction
                            .add(R.id.main_container, fragment5)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_CLOSE)
                            .commit();
                else
                    transaction.show(fragment5)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_CLOSE)
                            .commit();
                ctor = 4;
            }
            }
        });
    }

    private void updateUI() {
        UserLab mUserLab = UserLab.get(LayoutMain.this);
        if (mUser == null)
            mUser = mUserLab.NewUser();
        mUser.setName("张涵");
        drift_cameraLab.get(getBaseContext()).Newdrift_camera(getBaseContext()
                , "tryee"
                , "try"
                , mUser.getUUID()
                , 2
                , 11);
        UserNow.getUserNow().setUser(mUser);
        args.putSerializable(GET_USER, mUser.getUUID());
    }

    /**
     * 隐藏上一个fragment
     */
    public void HideLastFragment() {
        switch (ctor) {
            case 1:
                fm.beginTransaction().hide(fragment).commit();
                break;
            //case 2 :fmt.hide(fragment3).commit();break;
            //case 3 :fmt.hide(fragment4).commit();break;
            case 4:
                fm.beginTransaction().hide(fragment5).commit();
                break;
            default:
                break;
        }
    }

}

