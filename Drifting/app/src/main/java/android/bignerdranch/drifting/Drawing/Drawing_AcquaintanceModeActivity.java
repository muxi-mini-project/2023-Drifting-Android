package android.bignerdranch.drifting.Drawing;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.bignerdranch.drifting.User.User_;
import android.bignerdranch.drifting.friendsInvitingItem;
import android.bignerdranch.drifting.Camera.Camera_Activity;
import android.bignerdranch.drifting.Friends.FriendListInterface;
import android.bignerdranch.drifting.Friends.FriendsList_return;
import android.bignerdranch.drifting.Login.Login_LoginActivity;
import android.bignerdranch.drifting.Inviting.*;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.User_Now;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Drawing_AcquaintanceModeActivity extends AppCompatActivity {
    private ImageView iv_image;
    private String cover_url;
    private String name;
    private String theme;
    private String person_number;
    private ArrayList<friendsInvitingItem> target_friend = new ArrayList<>();
    private ArrayList<friendsInvitingItem> friend_list = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private FriendsList_return friendsList_return;
    private long newDrawingId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acquaintance_mode);

        iv_image = (ImageView) findViewById(R.id.iv_image);
        Button choose_cover = (Button) findViewById(R.id.acquaintance_choose_front_cover);
        Button start = (Button) findViewById(R.id.acquaintance_start);
        Button inviting_friends = (Button) findViewById(R.id.acquaintance_inviting_friends);
        EditText name_text = (EditText) findViewById(R.id.name_text);
        EditText theme_text = (EditText) findViewById(R.id.title_text);
        EditText person_number_text = (EditText) findViewById(R.id.person_number_text);

        cover_url = "mini-project.muxixyz.com/drifting/covers/1.jpg";

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://116.204.121.9:61583/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        FriendListInterface friendListInterface = retrofit.create(FriendListInterface.class);
        Call<FriendsList_return> call = friendListInterface.getFriendList(User_Now.getUserNow().getUser().getToken());
        call.enqueue(new Callback<FriendsList_return>() {
            @Override
            public void onResponse(Call<FriendsList_return> call, Response<FriendsList_return> response) {
                friendsList_return = response.body();

                for(int i = 0;i < friendsList_return.getDataN().size();i++){
                    friend_list.add(new friendsInvitingItem(friendsList_return.getDataN().get(i).getNameN(), friendsList_return.getDataN().get(i).getStudentID()));
                    names.add(friendsList_return.getDataN().get(i).getNameN());
                }
            }

            @Override
            public void onFailure(Call<FriendsList_return> call, Throwable t) {

            }
        });

        inviting_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Drawing_AcquaintanceModeActivity.this, android.R.style.Theme_Holo_Light_Dialog);
                builder.setItems(names.toArray(new String[0]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean flag = false;
                        for(int i = 0;i < target_friend.size();i++){
                            if(target_friend.get(i).getId() == friend_list.get(which).getId()){
                                flag = true;
                            }
                        }
                        if(!flag){
                            friend_list.get(which).setIfChosen(true);
                            names.set(which,friend_list.get(which).getName() + "（已选择）");
                            target_friend.add(friend_list.get(which));
                        }
                    }
                });
                builder.show();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name == null || theme == null || person_number == null || person_number.equals("")){
                    Toast.makeText(Drawing_AcquaintanceModeActivity.this, "请输入名字，主题和人数", Toast.LENGTH_SHORT).show();
                }
                else{
                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl("http://116.204.121.9:61583/")
                            .addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit = builder.build();
                    create_message message = new create_message(cover_url,1,name,Integer.parseInt(person_number),theme);
                    Api api = retrofit.create(Api.class);
                    Call<create_return> call = api.create(Login_LoginActivity.getToken(),message);
                    call.enqueue(new Callback<create_return>() {
                        @Override
                        public void onResponse(Call<create_return> call, Response<create_return> response) {
                            create_return create_return = response.body();
                            if(create_return != null){
                                if(create_return.getCode() == 200){
                                    newDrawingId = create_return.getData();
                                    Toast.makeText(Drawing_AcquaintanceModeActivity.this, "创建成功！", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Drawing_AcquaintanceModeActivity.this, Drawing_Activity.class);
                                    intent.putExtra("file_id",newDrawingId);
                                    startActivity(intent);
                                    for(int i = 0;i < target_friend.size();i++){
                                        User_Now user_now = User_Now.getUserNow();
                                        inviting_reactionBody invitingBody
                                                = new inviting_reactionBody(newDrawingId,"漂流画",target_friend.get(i).getId(),User_Now.getUserNow().getUser().getId());
                                        Call<inviting_reactionReturn> inviting_return = api.invite(Login_LoginActivity.getToken(),invitingBody);
                                        inviting_return.enqueue(new Callback<inviting_reactionReturn>() {
                                            @Override
                                            public void onResponse(Call<inviting_reactionReturn> call, Response<inviting_reactionReturn> response) {
                                                inviting_reactionReturn reactionReturn = response.body();
                                            }
                                            @Override
                                            public void onFailure(Call<inviting_reactionReturn> call, Throwable t) {
                                                Toast.makeText(Drawing_AcquaintanceModeActivity.this, "邀请好友时发生错误，请检查网络", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            }else{
                                Toast.makeText(Drawing_AcquaintanceModeActivity.this, "创建失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<create_return> call, Throwable t) {
                            Toast.makeText(Drawing_AcquaintanceModeActivity.this, "创建失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                finish();
            }
        });

        choose_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseCover();
            }
        });

        name_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name = s.toString();
            }
        });
        theme_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                theme = s.toString();
            }
        });
        person_number_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                person_number = s.toString();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                iv_image.setImageURI(uri);
            }
        }
        if (requestCode == 1) {
            Bundle bundle = data.getExtras();
            iv_image.setImageBitmap((Bitmap) bundle.get("data"));

        }

    }

    private void ChooseCover() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Holo_Light_Dialog);
        final String[] choices = {"背景1","背景2","背景3","背景4","背景5","背景6","背景7","背景8"};
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:{
                        cover_url = "mini-project.muxixyz.com/drifting/covers/点构图.jpg";
                        break;
                    }
                    case 1:{
                        cover_url = "mini-project.muxixyz.com/drifting/covers/抓拍，广场上玩滑板的少年.jpg";
                        break;
                    }
                    case 2:{
                        cover_url = "mini-project.muxixyz.com/drifting/covers/三分线，冷清的广场.jpg";
                        break;
                    }
                    case 3:{
                        cover_url = "mini-project.muxixyz.com/drifting/covers/三分线构图.jpg";
                        break;
                    }
                    case 4:{
                        cover_url = "mini-project.muxixyz.com/drifting/covers/4.jpg";
                        break;
                    }
                    case 5:{
                        cover_url = "mini-project.muxixyz.com/drifting/covers/3.jpg";
                        break;
                    }
                    case 6:{
                        cover_url = "mini-project.muxixyz.com/drifting/covers/2.jpg";
                        break;
                    }
                    case 7:{
                        cover_url = "mini-project.muxixyz.com/drifting/covers/1.jpg";
                        break;
                    }
                }
            }
        });
        builder.show();
    }
}