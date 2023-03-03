package android.bignerdranch.drifting.Book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.bignerdranch.drifting.Main.Main_FriendsFragment;
import android.bignerdranch.drifting.Mine.Friends.Friends_;
import android.bignerdranch.drifting.Mine.Friends.Friends_Lab;
import android.bignerdranch.drifting.R;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Book_inviting_friendsActivity extends AppCompatActivity {

    private RecyclerView friendListRc;
    private FriendAdapter2 mFriendAdapter;
    private String searchT;
    private ImageButton searchFriendsBtn;
    private EditText searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_inviting_friends);
        friendListRc = findViewById(R.id.friend_list);
        friendListRc.setLayoutManager(new LinearLayoutManager(Book_inviting_friendsActivity.this));

        searchFriendsBtn  = findViewById(R.id.search_friends_btn);
        searchText = findViewById(R.id.search_edt);
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
        searchFriendsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUIw();

            }
        });

        updateUI();




    }
    private void updateUI(){
        Friends_Lab friendsLab = Friends_Lab.get(Book_inviting_friendsActivity.this);
        List<Friends_> friends = friendsLab.getFriends();

        mFriendAdapter = new FriendAdapter2(friends);
        friendListRc.setAdapter(mFriendAdapter);
    }
    private void updateUIw() {
        Friends_Lab friendsLab = Friends_Lab.get(Book_inviting_friendsActivity.this);
        List<Friends_> friends = friendsLab.getFriends();
        List<Friends_> newFriends = new ArrayList<>();
        for (int i = 0 ;i<friends.size();i++){
            if (friends.get(i).getName().contains(searchT)){
                newFriends.add(friends.get(i));
            }
        }


        mFriendAdapter = new FriendAdapter2(newFriends);
        friendListRc.setAdapter(mFriendAdapter);
    }
    private class FriendsHolder2 extends RecyclerView.ViewHolder{

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

        public FriendsHolder2(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.friends_piece,parent,false));

            headImage = itemView.findViewById(R.id.head_image);
            friendName = itemView.findViewById(R.id.friend_name);
            friendAuto = itemView.findViewById(R.id.friend_autograph);

        }
    }
    private class FriendAdapter2 extends RecyclerView.Adapter<FriendsHolder2>{
        private List<Friends_> mFriends;

        public FriendAdapter2(List<Friends_> friends) {
            mFriends = friends;
        }


        @Override
        public FriendsHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(Book_inviting_friendsActivity.this);
            return new FriendsHolder2(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(FriendsHolder2 holder, int position) {
            Friends_ friends = mFriends.get(position);
            holder.bind(friends);
        }

        @Override
        public int getItemCount() {
            return mFriends.size();
        }
    }

}