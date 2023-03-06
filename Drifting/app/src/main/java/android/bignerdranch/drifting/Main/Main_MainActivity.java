package android.bignerdranch.drifting.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.bignerdranch.drifting.Inviting.Inviting_Fragment;
import android.bignerdranch.drifting.Inviting.Loading.inviting_messageReturn;
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
    static inviting_messageReturn request_return;

    public static inviting_messageReturn getRequest_return() {
        return request_return;
    }

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
        replaceFragment(mFragment);

        //导航栏链接
        ImageButton mine = (ImageButton) findViewById(R.id.mine);
        ImageButton friends = (ImageButton) findViewById(R.id.friends);
        ImageButton discovering = (ImageButton) findViewById(R.id.discovering);
        ImageButton settings = (ImageButton) findViewById(R.id.settings);

        //导航栏按钮相应
        mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (User_Now.getUserNow().getUser() != null) {
                    replaceFragment(new Mine_Fragment());
                    mine.setImageResource(R.drawable.mine);
                    friends.setImageResource(R.drawable.friends_unchosen);
                    discovering.setImageResource(R.drawable.discovering_unchosen);
                    settings.setImageResource(R.drawable.settings_unchosen);
                } else
                    Toast.makeText(getApplicationContext(), "请先登录", Toast.LENGTH_SHORT).show();
            }
        });
        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friends.setImageResource(R.drawable.friends);
                mine.setImageResource(R.drawable.mine_unchosen);
                discovering.setImageResource(R.drawable.discovering_unchosen);
                settings.setImageResource(R.drawable.settings_unchosen);
                replaceFragment(new Main_FriendsFragment());
            }
        });
        discovering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                discovering.setImageResource(R.drawable.discovering);
                settings.setImageResource(R.drawable.settings_unchosen);
                mine.setImageResource(R.drawable.mine_unchosen);
                friends.setImageResource(R.drawable.friends_unchosen);
                replaceFragment(new Main_DiscoveringFragment());


            }

        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.setImageResource(R.drawable.settings);
                mine.setImageResource(R.drawable.mine_unchosen);
                friends.setImageResource(R.drawable.friends_unchosen);
                discovering.setImageResource(R.drawable.discovering_unchosen);
                replaceFragment(new Main_SettingsFragment());
            }
        });


    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_container,fragment);
        transaction.commit();
    }

}