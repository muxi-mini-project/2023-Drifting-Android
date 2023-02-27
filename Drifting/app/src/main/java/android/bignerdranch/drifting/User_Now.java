package android.bignerdranch.drifting;

/**
 * 当前登录的user
 * 方便获取当前用户的数据，避免重复使用接口
 */
public class User_Now {
    static User_Now usernow;
    static User_ user;

    public static User_Now getUserNow() {
        if (usernow == null)
            usernow = new User_Now();
        return usernow;
    }

    public User_ getUser() {
        return user;
    }

    public void setUser(User_ user) {
        this.user = user;
    }
}
