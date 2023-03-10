package android.bignerdranch.drifting.Main;

import static androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_CLOSE;
import static androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.bignerdranch.drifting.Inviting.Inviting_Fragment;
import android.bignerdranch.drifting.Mine.Mine_Fragment;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.User_;
import android.bignerdranch.drifting.User.User_Now;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Main_MainActivity extends AppCompatActivity {
    User_ mUser;
    int ctor = 0;//获取当前界面,防止重复点击

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, Main_MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


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
                    if (User_Now.getUserNow().getUser() != null) {
                        replaceFragment(new Mine_Fragment());
                        mine.setImageResource(R.drawable.mine);
                        friends.setImageResource(R.drawable.friends_unchosen);
                        discovering.setImageResource(R.drawable.discovering_unchosen);
                        settings.setImageResource(R.drawable.settings_unchosen);
                    } else
                        Toast.makeText(getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
                }
                ctor = 1;
            }
        });
        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctor != 2) {
                    friends.setImageResource(R.drawable.friends);
                    mine.setImageResource(R.drawable.mine_unchosen);
                    discovering.setImageResource(R.drawable.discovering_unchosen);
                    settings.setImageResource(R.drawable.settings_unchosen);
                    replaceFragment(new Main_FriendsFragment());
                }
                ctor = 2;
            }
        });
        discovering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctor != 3) {
                    discovering.setImageResource(R.drawable.discovering);
                    settings.setImageResource(R.drawable.settings_unchosen);
                    mine.setImageResource(R.drawable.mine_unchosen);
                    friends.setImageResource(R.drawable.friends_unchosen);
                    replaceFragment(new Main_DiscoveringFragment());
                }
                ctor = 3;
            }

        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ctor != 4) {
                    settings.setImageResource(R.drawable.settings);
                    mine.setImageResource(R.drawable.mine_unchosen);
                    friends.setImageResource(R.drawable.friends_unchosen);
                    discovering.setImageResource(R.drawable.discovering_unchosen);
                    replaceFragment(new Main_SettingsFragment());
                }
                ctor = 4;
            }
        });


    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_container, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_CLOSE);
        transaction.commit();
    }

}