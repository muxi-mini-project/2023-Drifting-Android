package android.bignerdranch.drifting.Main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bignerdranch.drifting.Friends.AddnewFriends.AddFriends_return;
import android.bignerdranch.drifting.Friends.AddnewFriends.ApiFriendsInterface;
import android.bignerdranch.drifting.Friends.DeleteFriend_call;
import android.bignerdranch.drifting.Friends.FriendListInterface;
import android.bignerdranch.drifting.Friends.FriendsList_return;
import android.bignerdranch.drifting.Friends.FriendsNewActivity;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.User_Now;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
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
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

    @SuppressLint("MissingInflatedId")
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
    /**
     * 更新好友列表
     */
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

               if (friends_nets != null){
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

        public void bind(FriendsList_return.Friends_Net friends)  {
            mFriends = friends;

           try {
               headImage.setImageBitmap(getImage("http://"+mFriends.getAvatar()));
           } catch (Exception e) {
               e.printStackTrace();
           }
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
            ShowChoices(mFriends);
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

    /**
     * 删除好友的地方，后续可以在这里写写更多点击好友的功能
     * @param friends
     */
    private void ShowChoices(FriendsList_return.Friends_Net friends) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Holo_Light_Dialog);
        //    指定下拉列表的显示数据
        final String[] choices = {"删除好友", "返回"};
        //    设置一个下拉的列表选择项
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: {

                        DeleteFriend_call deleteFriend_call = new DeleteFriend_call();
                        deleteFriend_call.setFriendId(new Long(friends.getStudentID()).intValue());
                        deleteFriend_call.setUserId(User_Now.getUserNow().getUser().getId());

                        Retrofit.Builder builder = new Retrofit.Builder()
                                .baseUrl("http://116.204.121.9:61583/")
                                .addConverterFactory(GsonConverterFactory.create());
                        Retrofit retrofit = builder.build();
                        ApiFriendsInterface delete = retrofit.create(ApiFriendsInterface.class);
                        Call<AddFriends_return> call = delete.deleteFriends(deleteFriend_call,
                                User_Now.getUserNow().getUser().getToken());

                        call.enqueue(new Callback<AddFriends_return>() {
                            @Override
                            public void onResponse(Call<AddFriends_return> call, Response<AddFriends_return> response) {
                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                updateUI();
                            }

                            @Override
                            public void onFailure(Call<AddFriends_return> call, Throwable t) {

                            }
                        });

                    }
                    case 1: {
                        break;
                    }
                }
            }
        });
        builder.show();
    }
    private static Bitmap getImage(String path) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if(conn. getResponseCode() == 200){
            InputStream inStream = conn. getInputStream();
            Bitmap bitmap = BitmapFactory. decodeStream(inStream) ;
            return bitmap;
        }else return null;
    }

    private Uri fileToUri(File file){
        if (file != null){
            return Uri.fromFile(file);
        }
        return null;
    }



}
