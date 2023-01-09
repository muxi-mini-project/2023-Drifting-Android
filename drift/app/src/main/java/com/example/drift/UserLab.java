package com.example.drift;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserLab {
    private static UserLab sUserLab;
    private List<User> mUsers;

    public static UserLab get(Context context) {
        if (sUserLab == null)
            sUserLab = new UserLab(context);
        return sUserLab;
    }

    private UserLab(Context context) {
        mUsers = new ArrayList<>();
        User muser = new User();
        mUsers.add(muser);
    }

    public List<User> getUsers() {
        return mUsers;
    }

    public User getUser(UUID id) {
        for (User user : mUsers)
            if (user.getUUID().equals(id)) {
                return user;
            }
        return null;
    }

    public User NewUser() {
        User user = new User();
        mUsers.add(user);
        return user;
    }

}
