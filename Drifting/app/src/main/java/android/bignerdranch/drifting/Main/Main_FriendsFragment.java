package android.bignerdranch.drifting.Main;

import android.app.AlertDialog;
import android.bignerdranch.drifting.Friends.FriendListInterface;
import android.bignerdranch.drifting.Friends.FriendsList_return;
import android.bignerdranch.drifting.Friends.FriendsNewActivity;
import android.bignerdranch.drifting.Friends.Friends_;
import android.bignerdranch.drifting.Friends.Friends_Lab;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.User_Now;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main_FriendsFragment extends Fragment {
    private RecyclerView friendList;
    private FriendAdapter mFriendAdapter;
    private TextView newFriends;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends_fragment,container,false);

        friendList = (RecyclerView) view.findViewById(R.id.friend_list);
        newFriends = view.findViewById(R.id.newFriend);
        friendList.setLayoutManager(new LinearLayoutManager(getActivity()));
        newFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FriendsNewActivity.class);
                startActivity(intent);
            }
        });
        updateUI();

        return view;
    }

    private void updateUI() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://116.204.121.9:61583/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        FriendListInterface friendListInterface = retrofit.create(FriendListInterface.class);
        Call<FriendsList_return> call = friendListInterface.getFriendList(User_Now.getUserNow().getUser().getToken());
        call.enqueue(new Callback<FriendsList_return>() {
            @Override
            public void onResponse(Call<FriendsList_return> call, Response<FriendsList_return> response) {
                //Toast.makeText(getActivity(), response.body().getDataN().get(0).getNameN(), Toast.LENGTH_SHORT).show();测试成功
                FriendsList_return friendsList_return = response.body();
                List<FriendsList_return.Friends_Net> friends_nets = friendsList_return.getDataN();
                if(friends_nets != null){
                    mFriendAdapter = new FriendAdapter(friends_nets);
                    friendList.setAdapter(mFriendAdapter);
                }


            }

            @Override
            public void onFailure(Call<FriendsList_return> call, Throwable t) {

            }
        });
    }

    private class FriendsHolder extends RecyclerView.ViewHolder
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

        public FriendsHolder(LayoutInflater inflater,ViewGroup parent){
            super(inflater.inflate(R.layout.friends_piece,parent,false));

            headImage = itemView.findViewById(R.id.head_image);
            friendName = itemView.findViewById(R.id.friend_name);
            friendAuto = itemView.findViewById(R.id.friend_autograph);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "是否删除好友", Toast.LENGTH_SHORT).show();
            ShowChoices();
        }
    }
    private class FriendAdapter extends RecyclerView.Adapter<FriendsHolder>{
        private List<FriendsList_return.Friends_Net> mFriends;

        public FriendAdapter(List<FriendsList_return.Friends_Net> friends) {
            mFriends = friends;
        }

        @Override
        public FriendsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new FriendsHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(FriendsHolder holder, int position) {
            FriendsList_return.Friends_Net friends = mFriends.get(position);
            holder.bind(friends);
        }
        @Override
        public int getItemCount() {
            return mFriends.size();
        }
    }
    private void ShowChoices() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Holo_Light_Dialog);
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
