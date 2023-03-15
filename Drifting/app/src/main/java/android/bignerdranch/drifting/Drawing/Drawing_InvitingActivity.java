package android.bignerdranch.drifting.Drawing;

import android.bignerdranch.drifting.Inviting.Loading.inviting_messageReturn;
import android.bignerdranch.drifting.Inviting.Loading.inviting_request;
import android.bignerdranch.drifting.Inviting.inviting_reactionBody;
import android.bignerdranch.drifting.Inviting.inviting_reactionReturn;
import android.bignerdranch.drifting.Login.Login_LoginActivity;
import android.bignerdranch.drifting.Main.Main_DiscoveringFragment;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.GetNameFormIDRequest;
import android.bignerdranch.drifting.User.User_Now;
import android.bignerdranch.drifting.User.User_connector;
import android.bignerdranch.drifting.User.User_name_getFormID_return;
import android.bignerdranch.drifting.detail_request.getIdMessageReturn;
import android.bignerdranch.drifting.detail_request.getIdRequestBody;
import android.bignerdranch.drifting.detail_request.messageReturn;
import android.bignerdranch.drifting.detail_request.request;
import android.bignerdranch.drifting.detail_request.request_body;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Drawing_InvitingActivity extends AppCompatActivity {
    private Button commit_button;
    private Button refuse_button;
    private TextView host;
    private TextView start_time;
    private TextView theme_setting;
    private TextView people_num;
    private TextView name;
    private ImageView cover;
    private long id = 0;
    private int position = 0;
    private long host_id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inviting);

        id = getIntent().getLongExtra("file_id", 0);
        position = getIntent().getIntExtra("position", 0);
        String kinds  = getIntent().getStringExtra("kind");
        Log.i("kind",kinds);
        long kind = Long.valueOf(kinds);

        Bundle bundle = getIntent().getExtras();
        inviting_messageReturn.data  data = (inviting_messageReturn.data) bundle.getSerializable("data");
        Log.i("漂流画数据",data.toString());

        commit_button = (Button) findViewById(R.id.commit_button);
        refuse_button = (Button) findViewById(R.id.refuse_button);
        name = (TextView) findViewById(R.id.name);
        host = (TextView) findViewById(R.id.host);
        start_time = (TextView) findViewById(R.id.start_time);
        theme_setting = (TextView) findViewById(R.id.theme_setting);
        people_num = (TextView) findViewById(R.id.people_num);
        cover = (ImageView) findViewById(R.id.cover);

        //与另外三个写法相同
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://116.204.121.9:61583/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

       //通过id得到发起人的名字
        User_connector host_er = retrofit.create(User_connector.class);
        GetNameFormIDRequest getNameFormIDRequest  = new GetNameFormIDRequest();
        getNameFormIDRequest.setStudentID(data.getHoner_id());
        Call<User_name_getFormID_return> name_getFormID_returnCall = host_er.getHostName(getNameFormIDRequest,
                User_Now.getUserNow().getUser().getToken());
        name_getFormID_returnCall.enqueue(new Callback<User_name_getFormID_return>() {
            @Override
            public void onResponse(Call<User_name_getFormID_return> call, Response<User_name_getFormID_return> response) {
                host.setText("发起人："+response.body().getData().getName());
            }

            @Override
            public void onFailure(Call<User_name_getFormID_return> call, Throwable t) {

            }
        });


        start_time.setText("发起时间：" +cutTime(data.getCreatedAt()));
        theme_setting.setText("主题设定：" + data.getTheme());
        people_num.setText("设定的总人数：" + data.getNumber());

        commit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.bignerdranch.drifting.Inviting.inviting_reaction
                        reaction = retrofit.create(android.bignerdranch.drifting.Inviting.inviting_reaction.class);
                Call<android.bignerdranch.drifting.Inviting.inviting_reactionReturn> inviting_reactionReturnCall
                        = reaction.accept(Login_LoginActivity.getToken(), new inviting_reactionBody(id, "漂流画", 0, host_id));
                inviting_reactionReturnCall.enqueue(new Callback<inviting_reactionReturn>() {
                    @Override
                    public void onResponse(Call<inviting_reactionReturn> call, Response<inviting_reactionReturn> response) {
                        android.bignerdranch.drifting.Inviting.inviting_reactionReturn reactionReturn = response.body();
                        Toast.makeText(Drawing_InvitingActivity.this, "接受成功", Toast.LENGTH_SHORT).show();
                        Main_DiscoveringFragment.getMessageReturns().remove(position);
                    }

                    @Override
                    public void onFailure(Call<inviting_reactionReturn> call, Throwable t) {
                        Toast.makeText(Drawing_InvitingActivity.this, "接受失败，请检查网络", Toast.LENGTH_SHORT).show();
                    }
                });
                finish();
            }
        });
        refuse_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.bignerdranch.drifting.Inviting.inviting_reaction
                        reaction = retrofit.create(android.bignerdranch.drifting.Inviting.inviting_reaction.class);
                Call<android.bignerdranch.drifting.Inviting.inviting_reactionReturn> inviting_reactionReturnCall
                        = reaction.refuse(Login_LoginActivity.getToken(), new inviting_reactionBody(id, "漂流画", 0, host_id));
                inviting_reactionReturnCall.enqueue(new Callback<inviting_reactionReturn>() {
                    @Override
                    public void onResponse(Call<inviting_reactionReturn> call, Response<inviting_reactionReturn> response) {
                        android.bignerdranch.drifting.Inviting.inviting_reactionReturn reactionReturn = response.body();
                        Toast.makeText(Drawing_InvitingActivity.this, "拒绝成功", Toast.LENGTH_SHORT).show();
                        Main_DiscoveringFragment.getMessageReturns().remove(position);
                    }

                    @Override
                    public void onFailure(Call<inviting_reactionReturn> call, Throwable t) {
                        Toast.makeText(Drawing_InvitingActivity.this, "拒绝失败，请检查网络", Toast.LENGTH_SHORT).show();
                    }
                });
                finish();
            }
        });
    }
    private String cutTime(String s){
        String year = s.substring(0,4);
        String mouth = s.substring(5,7);
        String days = s.substring(8,10);
        return year+"年"+mouth+"月"+days+"日";
    }
}