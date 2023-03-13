package android.bignerdranch.drifting.Mine.User;

/**
 * 当前登录的user
 * 方便获取当前用户的数据，避免重复使用接口
 * 在用户数据变动时需要更新User_Now.user
 */
public class User_Now {
    private static User_Now usernow;
    User_ user;
    static String name;
    static String sex;
    static String avatar;
    static String selfWord;

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
        this.name = user.getName();
        this.sex = user.getSex();
        this.avatar = user.getPortrait();
        this.selfWord = user.getSignature();
    }
}
