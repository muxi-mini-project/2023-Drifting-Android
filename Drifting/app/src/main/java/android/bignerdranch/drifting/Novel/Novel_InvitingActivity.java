package android.bignerdranch.drifting.Novel;

import androidx.appcompat.app.AppCompatActivity;

import android.bignerdranch.drifting.Inviting.Loading.inviting_messageReturn;
import android.bignerdranch.drifting.Inviting.Loading.inviting_request;
import android.bignerdranch.drifting.Inviting.inviting_reactionBody;
import android.bignerdranch.drifting.Inviting.inviting_reactionReturn;
import android.bignerdranch.drifting.Login.Login_LoginActivity;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.detail_request.getIdMessageReturn;
import android.bignerdranch.drifting.detail_request.getIdRequestBody;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Novel_InvitingActivity extends AppCompatActivity {
    private Button commit_button;
    private Button refuse_button;
    private TextView host;
    private TextView start_time;
    private TextView theme_setting;
    private TextView people_num;
    private long id = 0;
    private long host_id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novel_inviting);

        id = getIntent().getLongExtra("file_id",0);

        commit_button = (Button) findViewById(R.id.commit_button);
        refuse_button = (Button) findViewById(R.id.refuse_button);
        host = (TextView) findViewById(R.id.host);
        start_time = (TextView) findViewById(R.id.start_time);
        theme_setting = (TextView) findViewById(R.id.theme_setting);
        people_num = (TextView) findViewById(R.id.people_num);

        //与另外三个写法相同
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://116.204.121.9:61583/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        inviting_request request = retrofit.create(inviting_request.class);
        Call<inviting_messageReturn> call = request.novel_request(Login_LoginActivity.getToken());
        call.enqueue(new Callback<inviting_messageReturn>() {
            @Override
            public void onResponse(Call<inviting_messageReturn> call, Response<inviting_messageReturn> response) {
                inviting_messageReturn request_return = response.body();
                for(int i = 0;i < request_return.getData().size();i++){
                    if(request_return.getData().get(i).getFile_id() == id){
                        host_id = request_return.getData().get(i).getHoner_id();
                        android.bignerdranch.drifting.detail_request.request idRequest = retrofit.create(android.bignerdranch.drifting.detail_request.request.class);
                        Call<android.bignerdranch.drifting.detail_request.getIdMessageReturn> idMessageReturnCall
                                = idRequest.idRequest(Login_LoginActivity.getToken(),new getIdRequestBody(host_id));
                        int finalI = i;
                        idMessageReturnCall.enqueue(new Callback<getIdMessageReturn>() {
                            @Override
                            public void onResponse(Call<getIdMessageReturn> call, Response<getIdMessageReturn> response) {
                                getIdMessageReturn messageReturn = response.body();
                                host.setText("发起人：" + messageReturn.getData().getName());
                                start_time.setText("发起时间：" + request_return.getData().get(finalI).getCreatedAt().substring(0,10));
                                theme_setting.setText("主题设定：" + request_return.getData().get(finalI).getTheme());
                                people_num.setText("设定的总人数：" + request_return.getData().get(finalI).getNumber());

                            }

                            @Override
                            public void onFailure(Call<getIdMessageReturn> call, Throwable t) {
                                Toast.makeText(Novel_InvitingActivity.this, "获取详细信息失败，请检查网络", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            }

            @Override
            public void onFailure(Call<inviting_messageReturn> call, Throwable t) {
                Toast.makeText(Novel_InvitingActivity.this, "获取详细信息失败，请检查网络", Toast.LENGTH_SHORT).show();
            }
        });

        commit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.bignerdranch.drifting.Inviting.inviting_reaction
                        reaction = retrofit.create(android.bignerdranch.drifting.Inviting.inviting_reaction.class);
                Call<android.bignerdranch.drifting.Inviting.inviting_reactionReturn> inviting_reactionReturnCall
                        = reaction.accept(Login_LoginActivity.getToken(),new inviting_reactionBody(id,"漂流小说",0,host_id));
                inviting_reactionReturnCall.enqueue(new Callback<inviting_reactionReturn>() {
                    @Override
                    public void onResponse(Call<inviting_reactionReturn> call, Response<inviting_reactionReturn> response) {
                        android.bignerdranch.drifting.Inviting.inviting_reactionReturn reactionReturn = response.body();
                        Toast.makeText(Novel_InvitingActivity.this, "接受成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<inviting_reactionReturn> call, Throwable t) {
                        Toast.makeText(Novel_InvitingActivity.this, "接受失败，请检查网络", Toast.LENGTH_SHORT).show();
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
                        = reaction.refuse(Login_LoginActivity.getToken(),new inviting_reactionBody(id,"漂流小说",0,host_id));
                inviting_reactionReturnCall.enqueue(new Callback<inviting_reactionReturn>() {
                    @Override
                    public void onResponse(Call<inviting_reactionReturn> call, Response<inviting_reactionReturn> response) {
                        android.bignerdranch.drifting.Inviting.inviting_reactionReturn reactionReturn = response.body();
                        Toast.makeText(Novel_InvitingActivity.this, "拒绝成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<inviting_reactionReturn> call, Throwable t) {
                        Toast.makeText(Novel_InvitingActivity.this, "拒绝失败，请检查网络", Toast.LENGTH_SHORT).show();
                    }
                });
                finish();
            }
        });
    }
}