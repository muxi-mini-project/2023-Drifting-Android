package android.bignerdranch.drifting;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * 主界面
 */
public class Main_MainActivity extends AppCompatActivity {
    User_ mUser;
    Bundle args = new Bundle();
    final String GET_USER = "user_id";//通过id在界面间传输用户数据，避免重复请求
    int ctor = 0;//获取当前的fragment，确保不重复点击

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, Main_MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        updateUI();

        //默认进入邀请界面
        Inviting_Fragment mFragment = new Inviting_Fragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(R.id.content_container, mFragment);
        fragmentTransaction.commit();


        //导航栏链接
        ImageButton mine = (ImageButton) findViewById(R.id.mine);
        ImageButton friends = (ImageButton) findViewById(R.id.friends);
        ImageButton discovering = (ImageButton) findViewById(R.id.discovering);
        ImageButton settings = (ImageButton) findViewById(R.id.settings);

        //导航栏按钮相应
        mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctor != 1) {
                    Fragment fragment = new Mine_Fragment();
                    fragment.setArguments(args);
                    replaceFragment(fragment);
                    ctor = 1;
                }
            }
        });
        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctor != 2)
                    replaceFragment(new Main_FriendsFragment());
                ctor = 2;
            }
        });
        discovering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctor != 3)
                    replaceFragment(new Main_DiscoveringFragment());
                ctor = 3;
            }

        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctor != 4)
                    replaceFragment(new Main_SettingsFragment());
                ctor = 4;
            }
        });


    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_container, fragment);
        transaction.commit();
    }

    private void updateUI() {//测试阶段用于进入个人界面
        User_Lab mUserLab = User_Lab.get(Main_MainActivity.this);
        if (mUser == null)
            mUser = mUserLab.NewUser();
        mUser.setName("张涵");
        User_Now.getUserNow().setUser(mUser);
        args.putSerializable(GET_USER, mUser.getUUID());
    }
}
