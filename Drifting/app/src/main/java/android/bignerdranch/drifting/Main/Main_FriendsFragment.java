package android.bignerdranch.drifting.Main;

import android.annotation.SuppressLint;
import android.bignerdranch.drifting.Book.Book_DriftingBookActivity;
import android.bignerdranch.drifting.Login.Login_LoginActivity;
import android.bignerdranch.drifting.Mine.Friends.FriendFromNet;
import android.bignerdranch.drifting.Mine.Friends.FriendGetInterface;
import android.bignerdranch.drifting.Mine.Friends.Friends_;
import android.bignerdranch.drifting.Mine.Friends.Friends_Lab;
import android.bignerdranch.drifting.Mine.Friends.New.NewFriendList;
import android.bignerdranch.drifting.Mine.Friends.Notice.NoticingActivity;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.User_connector;
import android.bignerdranch.drifting.User.User_return;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main_FriendsFragment extends Fragment {
    private RecyclerView friendList;
    private FriendAdapter mFriendAdapter;
    private ImageButton searchFriendsBtn;
    private EditText searchText;
    private String searchT = "";  //搜索内容
    private TextView newFriends;  //新朋友
    private TextView noticing; //通知




    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends_fragment,container,false);

        friendList = (RecyclerView) view.findViewById(R.id.friend_list);
        searchFriendsBtn = view.findViewById(R.id.search_friends_btn);
        searchText = view.findViewById(R.id.search_edt);
        newFriends = view.findViewById(R.id.newfriend);
        noticing = view.findViewById(R.id.text_notice);
        friendList.setLayoutManager(new LinearLayoutManager(getActivity()));

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchT = s.toString();
            }
        });
       noticing.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //startActivity(new Intent(getContext(),NoticingActivity.class));,不知道为啥会闪退
           }
       });
       newFriends.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //startActivity(new Intent(getContext(),NewFriendList.class));
           }
       });



        searchFriendsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "yes", Toast.LENGTH_SHORT).show();
                updateUIw();

            }
        });


        updateUI();
        return view;
    }
    private void updateUI(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://116.204.121.9:61583/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        FriendGetInterface friendGetInterface = retrofit.create(FriendGetInterface.class);
        Call<List<FriendFromNet>> call = friendGetInterface.getFriendList("Bearer:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdHVkZW50IjoyMDIyMjE0NTE5LCJleHAiOjE2Nzg4NTA0MzUsImlzcyI6IktpdFpoYW5nWXMifQ.x7RCIq6VCXBcobfyWBsVXFQV5YK2-m6cDtaoJBrZpJc");
        call.enqueue(new Callback<List<FriendFromNet>>() {
            @Override
            public void onResponse(Call<List<FriendFromNet>> call, Response<List<FriendFromNet>> response) {
                List<FriendFromNet> friendFromNetList = response.body();
                newFriends.setText(friendFromNetList.get(0).getNameN());
            }

            @Override
            public void onFailure(Call<List<FriendFromNet>> call, Throwable t) {

                Toast.makeText(getActivity(), "w", Toast.LENGTH_SHORT).show();
            }
        });

        Friends_Lab friendsLab = Friends_Lab.get(getActivity());
        List<Friends_> friends = friendsLab.getFriends();

        mFriendAdapter = new FriendAdapter(friends);
        friendList.setAdapter(mFriendAdapter);
    }

    private void updateUIw() {
        Friends_Lab friendsLab = Friends_Lab.get(getActivity());
        List<Friends_> friends = friendsLab.getFriends();
        List<Friends_> newFriends = new ArrayList<>();
        for (int i = 0 ;i<friends.size();i++){
            if (friends.get(i).getName().contains(searchT)){
                newFriends.add(friends.get(i));
            }
        }


        mFriendAdapter = new FriendAdapter(newFriends);
        friendList.setAdapter(mFriendAdapter);
    }

    private class FriendsHolder extends RecyclerView.ViewHolder{

        private ImageView headImage;
        private TextView friendName;
        private TextView friendAuto;
        private Friends_ mFriends;

        public void bind(Friends_ friends){
            mFriends = friends;
            headImage.setImageResource(mFriends.getImageHead());
            friendName.setText(mFriends.getName());
            friendAuto.setText(mFriends.getAutograph());
        }

        public FriendsHolder(LayoutInflater inflater,ViewGroup parent){
            super(inflater.inflate(R.layout.friends_piece,parent,false));

            headImage = itemView.findViewById(R.id.head_image);
            friendName = itemView.findViewById(R.id.friend_name);
            friendAuto = itemView.findViewById(R.id.friend_autograph);


        }
    }
    private class FriendAdapter extends RecyclerView.Adapter<FriendsHolder>{
        private List<Friends_> mFriends;

        public FriendAdapter(List<Friends_> friends) {
            mFriends = friends;
        }


        @Override
        public FriendsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new FriendsHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(FriendsHolder holder, int position) {
            Friends_ friends = mFriends.get(position);
            holder.bind(friends);

        }

        @Override
        public int getItemCount() {
            return mFriends.size();
        }
    }




}
