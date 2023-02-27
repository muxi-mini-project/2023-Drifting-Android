package android.bignerdranch.drifting;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import java.util.UUID;

/**
 * 个人界面实现编辑资料功能
 */
public class Mine_BIanjiFragment extends AppCompatActivity {
    final String GET_USER = "user_id";
    Button mBaocun;
    ImageButton mFanhui;
    EditText mNicheng;
    EditText mQianming;
    String qianming;
    String nicheng;
    User_ user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_gerenbianji);
        user = User_Lab.get(getApplicationContext()).getUser((UUID) getIntent().getSerializableExtra(GET_USER));

        mFanhui = (ImageButton) findViewById(R.id.fanhui_button);
        mBaocun = (Button) findViewById(R.id.baocun_button);
        mNicheng = (EditText) findViewById(R.id.bianji_nicheng);
        mQianming = (EditText) findViewById(R.id.bianji_qianming);
        mNicheng.setText(user.getName());
        mQianming.setText(user.getSignature());
        mNicheng.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nicheng = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mQianming.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                qianming = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBaocun.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onClick(View v) {
                if(nicheng != null)
                user.setName(nicheng);
                if(qianming != null)
                user.setSignature(qianming);
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        mFanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}