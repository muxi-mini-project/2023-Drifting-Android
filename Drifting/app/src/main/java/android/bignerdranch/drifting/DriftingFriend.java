package android.bignerdranch.drifting;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DriftingFriend extends Fragment {
    private RecyclerView friendList;
    private FriendAdapter mFriendAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drifting_friends,container,false);

        friendList = (RecyclerView) view.findViewById(R.id.friend_list);
        friendList.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        FriendsLab friendsLab = FriendsLab.get(getActivity());
        List<Friends> friends = friendsLab.getFriends();

        mFriendAdapter = new FriendAdapter(friends);
        friendList.setAdapter(mFriendAdapter);
    }

    private class FriendsHolder extends RecyclerView.ViewHolder{

        private ImageView headImage;
        private TextView friendName;
        private TextView friendAuto;
        private Friends mFriends;

        public void bind(Friends friends){
            mFriends = friends;
            headImage.setImageResource(mFriends.getImageHead());
            friendName.setText(mFriends.getName());
            friendAuto.setText(mFriends.getAutograph());
        }

        public FriendsHolder(LayoutInflater inflater,ViewGroup parent){
            super(inflater.inflate(R.layout.friend,parent,false));

            headImage = itemView.findViewById(R.id.head_image);
            friendName = itemView.findViewById(R.id.friend_name);
            friendAuto = itemView.findViewById(R.id.friend_autograph);


        }
    }
    private class FriendAdapter extends RecyclerView.Adapter<FriendsHolder>{
        private List<Friends> mFriends;

        public FriendAdapter(List<Friends> friends) {
            mFriends = friends;
        }


        @Override
        public FriendsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new FriendsHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(FriendsHolder holder, int position) {
            Friends friends = mFriends.get(position);
            holder.bind(friends);

        }

        @Override
        public int getItemCount() {
            return mFriends.size();
        }
    }




}
