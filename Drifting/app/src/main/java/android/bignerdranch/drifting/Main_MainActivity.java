package android.bignerdranch.drifting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Main_MainActivity extends AppCompatActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, Main_MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
                //Toast.makeText(MainActivity.this,"mine is clicked",Toast.LENGTH_SHORT).show();
                Main_MineFragment mFragment = new Main_MineFragment();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.content_container, mFragment);
                fragmentTransaction.commit();
            }
        });
        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"friends is clicked",Toast.LENGTH_SHORT).show();
                Main_FriendsFragment mFragment = new Main_FriendsFragment();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.content_container, mFragment);
                fragmentTransaction.commit();
            }
        });
        discovering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"discovering is clicked",Toast.LENGTH_SHORT).show();
                Main_DiscoveringFragment mFragment = new Main_DiscoveringFragment();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.content_container, mFragment);
                fragmentTransaction.commit();

            }

        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"settings is clicked",Toast.LENGTH_SHORT).show();
                Main_SettingsFragment mFragment = new Main_SettingsFragment();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = manager.beginTransaction();
                fragmentTransaction.replace(R.id.content_container, mFragment);
                fragmentTransaction.commit();
            }
        });

    }


}