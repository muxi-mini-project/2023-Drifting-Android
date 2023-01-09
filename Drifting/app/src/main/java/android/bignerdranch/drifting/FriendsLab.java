package android.bignerdranch.drifting;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FriendsLab {
    private static FriendsLab sFriendsLab;
    private List<Friends> mFriends;

    public FriendsLab(Context context) {
        mFriends = new ArrayList<>();
        for (int i = 0;i<100;i++){
            Friends friends = new Friends();
            friends.setImageHead(R.drawable.book);
            friends.setName("No."+i);
            friends.setAutograph("这家伙很懒什么也没有留下");
            mFriends.add(friends);
        }
    }

    public static FriendsLab get(Context context){
        if (sFriendsLab == null){
            sFriendsLab =new FriendsLab(context);
        }
        return sFriendsLab;
    }
    public List<Friends> getFriends(){
        return mFriends;
    }
    public Friends getFriends(UUID id){
        for (Friends friends:mFriends){
            if (friends.getUUID().equals(id)){
                return friends;
            }
        }
        return null;
    }
}
