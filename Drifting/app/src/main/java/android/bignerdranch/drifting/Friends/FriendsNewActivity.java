package android.bignerdranch.drifting.Friends;

import android.app.AlertDialog;
import android.bignerdranch.drifting.Friends.AddnewFriends.AddFriendsInterface;
import android.bignerdranch.drifting.Friends.AddnewFriends.AddFriends_return;
import android.bignerdranch.drifting.Friends.AddnewFriends.AddnewFriendSend;
import android.bignerdranch.drifting.Main.Main_FriendsFragment;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.User_Now;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FriendsNewActivity extends AppCompatActivity {

    private EditText newFriendsID;
    private Button sent;
    private Integer targetID;
    private String token = User_Now.getUserNow().getUser().getToken();
    private RecyclerView FriendsWillBe;
    private FriendAdderAdapter mFriendAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_newadd);
        newFriendsID = findViewById(R.id.add_newFTx);
        sent = findViewById(R.id.add_newFBtn);
        newFriendsID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int p =0;
                try {
                    Integer we = Integer.valueOf(newFriendsID.getText().toString());
                }catch (Exception e){
                    p =1;
                    Toast.makeText(FriendsNewActivity.this, "请不要输入数字以外的字符", Toast.LENGTH_SHORT).show();
                }
                if (p == 0){
                    targetID = Integer.valueOf(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        AddnewFriendSend addnewFriendSend = new AddnewFriendSend();
               addnewFriendSend.setAdderID(User_Now.getUserNow().getUser().getId());
               addnewFriendSend.setTargetID(targetID);

        sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddnewFriendSend addnewFriendSend = new AddnewFriendSend();
                addnewFriendSend.setAdderID(User_Now.getUserNow().getUser().getId());
                addnewFriendSend.setTargetID(targetID);
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("http://116.204.121.9:61583/")
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();
                AddFriendsInterface addFriendsInterface = retrofit.create(AddFriendsInterface.class);
                Call<AddFriends_return> call = addFriendsInterface.getNewFriendsID(addnewFriendSend,
                        token);
                call.enqueue(new Callback<AddFriends_return>() {
                    @Override
                    public void onResponse(Call<AddFriends_return> call, Response<AddFriends_return> response) {
                        Toast.makeText(FriendsNewActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(Call<AddFriends_return> call, Throwable t) {
                    }
                });
            }
        });


        FriendsWillBe = findViewById(R.id.newWillFriendsList);
        FriendsWillBe.setLayoutManager(new LinearLayoutManager(this));
        updateUI();

    }
    private void updateUI() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://116.204.121.9:61583/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        AddFriendsInterface friendListInterface = retrofit.create(AddFriendsInterface.class);
        Call<FriendsList_return> call = friendListInterface.getWhoWbeMyFriends(User_Now.getUserNow().getUser().getToken());
        call.enqueue(new Callback<FriendsList_return>() {
            @Override
            public void onResponse(Call<FriendsList_return> call, Response<FriendsList_return> response) {
                //Toast.makeText(getActivity(), response.body().getDataN().get(0).getNameN(), Toast.LENGTH_SHORT).show();测试成功
                FriendsList_return friendsList_return = response.body();
                List<FriendsList_return.Friends_Net> friends_nets = friendsList_return.getDataN();
                if(friends_nets != null){
                    mFriendAdapter = new FriendAdderAdapter(friends_nets);
                    FriendsWillBe.setAdapter(mFriendAdapter);
                }


            }

            @Override
            public void onFailure(Call<FriendsList_return> call, Throwable t) {

            }
        });
    }
    private class FriendsAdderHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{

        private ImageView headImage;
        private TextView friendName;
        private TextView friendAuto;
        private FriendsList_return.Friends_Net mFriends;

        public void bind(FriendsList_return.Friends_Net friends){
            mFriends = friends;
            headImage.setImageResource(R.drawable.book);
            friendName.setText(mFriends.getNameN());
            friendAuto.setText(mFriends.getSelfWord());
        }

        public FriendsAdderHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.friends_piece_adder,parent,false));

            headImage = itemView.findViewById(R.id.head_image);
            friendName = itemView.findViewById(R.id.friend_name);
            friendAuto = itemView.findViewById(R.id.friend_autograph);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(FriendsNewActivity.this, "删除好友", Toast.LENGTH_SHORT).show();
            ShowChoices();
        }
    }
    private class FriendAdderAdapter extends RecyclerView.Adapter<FriendsAdderHolder>{
        private List<FriendsList_return.Friends_Net> mFriends;

        public FriendAdderAdapter(List<FriendsList_return.Friends_Net> friends) {
            mFriends = friends;
        }

        @Override
        public FriendsAdderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FriendsNewActivity.this);
            return new FriendsAdderHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(FriendsAdderHolder holder, int position) {
            FriendsList_return.Friends_Net friends = mFriends.get(position);
            holder.bind(friends);
        }
        @Override
        public int getItemCount() {
            return mFriends.size();
        }
    }
    private void ShowChoices() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Holo_Light_Dialog);
        //    指定下拉列表的显示数据
        final String[] choices = {"删除好友", "返回"};
        //    设置一个下拉的列表选择项
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: {
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 2);
                    }
                    case 1: {
                        break;
                    }
                }
            }
        });
        builder.show();
    }


}
