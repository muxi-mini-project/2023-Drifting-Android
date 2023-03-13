package android.bignerdranch.drifting.Mine;

import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.Mine.User.User_;
import android.bignerdranch.drifting.Mine.User.User_Now;
import android.bignerdranch.drifting.Mine.User.User_Putdata;
import android.bignerdranch.drifting.Mine.User.User_connector;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 个人界面实现编辑资料功能
 */
public class Mine_BianJiActivity extends AppCompatActivity {
    EditText mNameEdit;
    EditText mSignEdit;
    Button mSaveButton;
    String name;
    String sign;
    User_ mUser;
public class Return_fromdata{
    Object message;

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_bianji);
        mUser = User_Now.getUserNow().getUser();

        mNameEdit = (EditText) findViewById(R.id.bianji_nicheng);
        mSignEdit = (EditText) findViewById(R.id.bianji_qianming);
        mSaveButton = (Button) findViewById(R.id.baocun_button) ;
        mNameEdit.setText(mUser.getName());
        mSignEdit.setText(mUser.getSignature());
        mNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                User_Now.getUserNow().getUser().setName(s.toString());
                mUser.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mSignEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                User_Now.getUserNow().getUser().setSignature(s.toString());
                mUser.setSignature(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User_Putdata user_putdata = new User_Putdata();
                user_putdata.setName(mUser.getName());
                user_putdata.setSelfWord(mUser.getSignature());
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("http://116.204.121.9:61583/")
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();
                User_connector user_connector = retrofit.create(User_connector.class);
                Call<Return_fromdata> call = user_connector.putUserdata(user_putdata,
                        User_Now.getUserNow().getUser().getToken());
                call.enqueue(new Callback<Return_fromdata>() {
                    @Override
                    public void onResponse(Call<Return_fromdata> call, Response<Return_fromdata> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
                        }else
                            Toast.makeText(getApplicationContext(),"保存失败",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<Return_fromdata> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"请求错误",Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(Mine_BianJiActivity.this,Mine_Fragment.class);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

}