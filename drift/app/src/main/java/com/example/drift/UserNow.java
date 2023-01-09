package com.example.drift;

import android.content.Context;

public class UserNow {
    static UserNow usernow;
    static User user;

    public static UserNow getUserNow() {
        if (usernow == null)
            usernow = new UserNow();
        return usernow;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
