package android.bignerdranch.drifting.User;

import android.bignerdranch.drifting.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class USer_items_Manager extends AppCompatActivity {
    Button mAllitems;
    Button mMyitems;
    Button mAttenditems;
    TextView mReturn;
    @Override
   protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_manager);
        updateUI();
        mAllitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"开发中",Toast.LENGTH_SHORT).show();
            }
        });
        mAttenditems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"开发中",Toast.LENGTH_SHORT).show();
            }
        });
        mMyitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"开发中",Toast.LENGTH_SHORT).show();
            }
        });
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
    private void updateUI(){
        mAllitems = findViewById(R.id.all_items);
        mMyitems = findViewById(R.id.my_items);
        mAttenditems = findViewById(R.id.attend_items);
        mReturn = findViewById(R.id.return_Minefragment);
    }
}
