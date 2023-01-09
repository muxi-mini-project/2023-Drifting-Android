package android.bignerdranch.drifting;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private ImageButton friend;
    private ImageButton person;
    private ImageButton setting;
    private ImageButton discovering;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        friend = findViewById(R.id.friend_btn);
        person = findViewById(R.id.person_btn);
        setting = findViewById(R.id.setting_btn);
        discovering = findViewById(R.id.discover_btn);

        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new DriftingFriend());
            }
        });
        person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new GeRen());
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Shezhi());
            }
        });
        discovering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Discovering());
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
