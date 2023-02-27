package android.bignerdranch.drifting;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 所有朋友
 */
public class Friends_Lab {
    private static Friends_Lab sFriendsLab;
    private List<Friends_> mFriends;

    public Friends_Lab(Context context) {
        mFriends = new ArrayList<>();
        for (int i = 0;i<100;i++){
            Friends_ friends = new Friends_();
            friends.setImageHead(R.drawable.book);
            friends.setName("No."+i);
            friends.setAutograph("这家伙很懒什么也没有留下");
            mFriends.add(friends);
        }
    }

    public static Friends_Lab get(Context context){
        if (sFriendsLab == null){
            sFriendsLab =new Friends_Lab(context);
        }
        return sFriendsLab;
    }
    public List<Friends_> getFriends(){
        return mFriends;
    }
    public Friends_ getFriends(UUID id){
        for (Friends_ friends:mFriends){
            if (friends.getUUID().equals(id)){
                return friends;
            }
        }
        return null;
    }
}
