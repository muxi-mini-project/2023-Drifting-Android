package android.bignerdranch.drifting.Camera;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bignerdranch.drifting.Friends.FriendListInterface;
import android.bignerdranch.drifting.Friends.FriendsList_return;
import android.bignerdranch.drifting.Mine.GetAllItems;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.User_Now;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Camera_setting_acquaintance extends AppCompatActivity {
    final Long KIND = new Long(1);
    EditText mName;
    EditText mTitle;
    EditText mNumber;
    String name;
    String theme;
    Long number;
    Long id;//创建后返回的id
    ImageView iv_image;
    String mCoverURL;
    Button mSelectCover;
    Button mStart;
    Button mInviting;
    List<FriendsList_return.Friends_Net> list = new ArrayList<>();
    List<Integer> friend_invite = new ArrayList<>();//要邀请的好友id列表
    List<Integer> friendID = new ArrayList<>(); //friends的ID，用于邀请
    static Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drifting_acquaintance_mode);
        updateUI();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 200) {
                    ShowChoices_friends();
                }
                if (msg.what == 201)
                    for (int i = 0; i < friend_invite.size(); i++)
                        InvitingFriends((long) User_Now.getUserNow().getUser().getId(),
                                (long) friend_invite.get(i),
                                id,
                                User_Now.getUserNow().getUser().getToken());
            }
        };

        mName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name = mName.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                theme = mTitle.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                number = Integer.valueOf(mNumber.getText().toString()).longValue();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mSelectCover.setOnClickListener(v -> ShowChoices());
        mStart.setOnClickListener(v -> {
            if (number == null || theme == null || name == null || mCoverURL == null||number > 9 || number <= 0 ){
                Toast.makeText(getApplicationContext(), "请输入正确的数据或选择封面", Toast.LENGTH_SHORT).show();
            } else {
                Create_Camera(name, mCoverURL, number, theme);
            }
        });
        mInviting.setOnClickListener(v -> {
            //获取好友列表进行邀请
            GetFriends();
        });
    }

    private void updateUI() {
        mName = (EditText) findViewById(R.id.name_text);
        mTitle = (EditText) findViewById(R.id.title_text);
        mNumber = (EditText) findViewById(R.id.person_number_text);
        iv_image = (ImageView) findViewById(R.id.iv_image);
        mSelectCover = (Button) findViewById(R.id.acquaintance_choose_front_cover);
        mStart = (Button) findViewById(R.id.acquaintance_start);
        mInviting = (Button) findViewById(R.id.acquaintance_inviting_friends);
    }

    private void ShowChoices() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Holo_Light_Dialog);
        //    指定下拉列表的显示数据
        final String[] choices = {"封面1", "封面2", "封面3", "封面4", "封面5", "封面6", "封面7", "封面8"};
        //    设置一个下拉的列表选择项
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: {
                        iv_image.setImageResource(R.drawable.cover_1);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/点构图.jpg";
                        break;
                    }
                    case 1: {
                        iv_image.setImageResource(R.drawable.cover_2);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/抓拍，广场上玩滑板的少年.jpg";
                        break;
                    }
                    case 2: {
                        iv_image.setImageResource(R.drawable.cover_3);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/三分线，冷清的广场.jpg";
                        break;
                    }
                    case 3: {
                        iv_image.setImageResource(R.drawable.cover_4);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/三分线构图.jpg";
                        break;
                    }
                    case 4: {
                        iv_image.setImageResource(R.drawable.cover_5);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/4.jpg";
                        break;
                    }
                    case 5: {
                        iv_image.setImageResource(R.drawable.cover_6);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/3.jpg";
                        break;
                    }
                    case 6: {
                        iv_image.setImageResource(R.drawable.cover_7);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/2.jpg";
                        break;
                    }
                    case 7: {
                        iv_image.setImageResource(R.drawable.cover_8);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/1.jpg";
                        break;
                    }
                }
            }
        });
        builder.show();
    }

    private void ShowChoices_friends() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Holo_Light_Dialog);
        final String[] friendstr = new String[list.size()];//friends名字
        final boolean[] select = new boolean[list.size()];//是否邀请
        for (int i = 0; i < list.size(); i++)
            friendstr[i] = list.get(i).getNameN();
        for (int i = 0; i < list.size(); i++)
            friendID.add(list.get(i).getStudentID().intValue());
        for (int i = 0; i < friendID.size(); i++)
            for (int j = 0; j < friend_invite.size(); j++)
                if (friend_invite.get(j) == friendID.get(i))
                    select[i] = true;
        builder.setTitle("选择好友");
        builder.setMultiChoiceItems(friendstr, select, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                select[which] = isChecked;
            }
        });

        builder.setPositiveButton("邀请", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<Integer> friend_Invite = new ArrayList<>();
                for (int i = 0; i < list.size(); i++)
                    if (select[i]) {
                            friend_Invite.add(friendID.get(i));
                    }
                friend_invite = friend_Invite;
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    private void GetFriends() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://116.204.121.9:61583/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        FriendListInterface friendListInterface = retrofit.create(FriendListInterface.class);
        Call<FriendsList_return> call = friendListInterface.getFriendList(User_Now.getUserNow().getUser().getToken());
        call.enqueue(new Callback<FriendsList_return>() {
            @Override
            public void onResponse(Call<FriendsList_return> call, Response<FriendsList_return> response) {
                if (response.isSuccessful()) {
                    FriendsList_return friendsList_return = response.body();
                    List<FriendsList_return.Friends_Net> friends_nets = friendsList_return.getDataN();
                    list = friends_nets;
                    Message message = new Message();
                    message.what = 200;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onFailure(Call<FriendsList_return> call, Throwable t) {
                Log.d("camera_bug", "获取好友列表失败");
            }
        });
    }

    private void InvitingFriends(Long InviterID, Long FriendID, Long file_id, String token) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://116.204.121.9:61583/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        Camera_connector camera_connector = retrofit.create(Camera_connector.class);
        Camera_return_upload.Camera_upload_inviting_friend body = new Camera_return_upload.Camera_upload_inviting_friend();
        body.setFile_id(file_id);
        body.setFriend_id(FriendID);
        body.setHost_id(InviterID);
        body.setFile_Kind("漂流相机");
        Call<Camera_return_upload.Camera_return_inviting_friend> call
                = camera_connector.Camera_inviting_friend(body, token);
        call.enqueue(new Callback<Camera_return_upload.Camera_return_inviting_friend>() {
            @Override
            public void onResponse(Call<Camera_return_upload.Camera_return_inviting_friend> call, Response<Camera_return_upload.Camera_return_inviting_friend> response) {
                if (response.isSuccessful())
                    Log.d("camera_bug", "邀请成功");
                Log.d("camera_bug",response.body().getMessage().toString());
            }

            @Override
            public void onFailure(Call<Camera_return_upload.Camera_return_inviting_friend> call, Throwable t) {
                Log.d("camera_bug", t.toString());
            }
        });
    }

    private void Create_Camera(String name, String mCoverURL, Long number, String theme) {
        Camera_return_upload.Camera_upload_create upload_create = new Camera_return_upload.Camera_upload_create();
        upload_create.setName(name);
        upload_create.setCover(mCoverURL);
        upload_create.setKind(KIND);
        upload_create.setTheme(theme);
        upload_create.setNumber(number);
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://116.204.121.9:61583/");
        Retrofit retrofit = builder.build();
        Camera_connector camera_connector = retrofit.create(Camera_connector.class);
        Call<Camera_return_upload.Camera_return_create> call = camera_connector.CreateCamera_new(upload_create, User_Now.getUserNow().getUser().getToken());
        call.enqueue(new Callback<Camera_return_upload.Camera_return_create>() {
            @Override
            public void onResponse(Call<Camera_return_upload.Camera_return_create> call, Response<Camera_return_upload.Camera_return_create> response) {
                if (response.isSuccessful()) {
                    GetAllItems.getGetAllItems().refreshMessage();
                    id = response.body().getData();
                    Message message = new Message();
                    message.what = 201;
                    handler.sendMessage(message);
                    Intent intent = new Intent(Camera_setting_acquaintance.this, Camera_Start.class);
                    intent.putExtra("camera_id", response.body().getData().intValue());
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "创建成功，现在开始创作吧！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Camera_return_upload.Camera_return_create> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
