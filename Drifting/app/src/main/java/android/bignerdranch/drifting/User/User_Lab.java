package android.bignerdranch.drifting.User;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User_Lab {
    private static User_Lab sUserLab;
    private List<User_> mUsers;

    public static User_Lab get(Context context) {
        if (sUserLab == null)
            sUserLab = new User_Lab(context);
        return sUserLab;
    }

    private User_Lab(Context context) {
        mUsers = new ArrayList<>();
        User_ muser = new User_();
        mUsers.add(muser);
    }

    public List<User_> getUsers() {
        return mUsers;
    }

    public User_ getUser(UUID id) {
        for (User_ user : mUsers)
            if (user.getUUID().equals(id)) {
                return user;
            }
        return null;
    }

    public User_ NewUser() {
        User_ user = new User_();
        mUsers.add(user);
        return user;
    }

}
