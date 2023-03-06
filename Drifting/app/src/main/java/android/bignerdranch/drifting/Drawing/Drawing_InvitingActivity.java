package android.bignerdranch.drifting.Drawing;

import androidx.appcompat.app.AppCompatActivity;

import android.bignerdranch.drifting.Inviting.Loading.inviting_messageReturn;
import android.bignerdranch.drifting.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Drawing_InvitingActivity extends AppCompatActivity {
    private Button commit_button;
    private Button refuse_button;
    private TextView host;
    private TextView start_time;
    private TextView theme_setting;
    private TextView time_restrict;
    private TextView people_before;
    private inviting_messageReturn request_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawing_inviting);

        commit_button = (Button) findViewById(R.id.commit_button);
        refuse_button = (Button) findViewById(R.id.refuse_button);
        host = (TextView) findViewById(R.id.host);
        start_time = (TextView) findViewById(R.id.start_time);
        theme_setting = (TextView) findViewById(R.id.theme_setting);
        time_restrict = (TextView) findViewById(R.id.time_restrict);
        people_before = (TextView) findViewById(R.id.people_before);

        commit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        refuse_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}