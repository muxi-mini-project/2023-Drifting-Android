package android.bignerdranch.drifting.Inviting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.bignerdranch.drifting.Friends.FriendListInterface;
import android.bignerdranch.drifting.Friends.FriendsList_return;
import android.bignerdranch.drifting.Main.Main_FriendsFragment;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.User_Now;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.FileUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 邀请好友参加漂流
 */
public class Inviting_Friends extends AppCompatActivity {
    private RecyclerView friendsList;
    private FriendAdapterI mFriendAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inviting_friends);

        friendsList = findViewById(R.id.friend_list);
        friendsList.setLayoutManager(new LinearLayoutManager(this));
        updateUI();
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

                mFriendAdapter = new FriendAdapterI(friends_nets);
                friendsList.setAdapter(mFriendAdapter);

            }

            @Override
            public void onFailure(Call<FriendsList_return> call, Throwable t) {

            }
        });
    }
    private class FriendsHolderI extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private ImageView headImage;
        private TextView friendName;
        private TextView friendAuto;
        private FriendsList_return.Friends_Net mFriends;

        public void bind(FriendsList_return.Friends_Net friends){
            mFriends = friends;
            try{
                headImage.setImageBitmap(getImage("http://"+mFriends.getAvatar()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            friendName.setText(mFriends.getNameN());
            friendAuto.setText(mFriends.getSelfWord());
        }

        public FriendsHolderI(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.friends_piece,parent,false));

            headImage = itemView.findViewById(R.id.head_image);
            friendName = itemView.findViewById(R.id.friend_name);
            friendAuto = itemView.findViewById(R.id.friend_autograph);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(Inviting_Friends.this, "邀请好友", Toast.LENGTH_SHORT).show();

        }
    }
    private class FriendAdapterI extends RecyclerView.Adapter<FriendsHolderI>{
        private List<FriendsList_return.Friends_Net> mFriends;

        public FriendAdapterI(List<FriendsList_return.Friends_Net> friends) {
            mFriends = friends;
        }

        @Override
        public FriendsHolderI onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(Inviting_Friends.this);
            return new FriendsHolderI(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(FriendsHolderI holder, int position) {
            FriendsList_return.Friends_Net friends = mFriends.get(position);
            holder.bind(friends);
        }
        @Override
        public int getItemCount() {
            return mFriends.size();
        }
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
}