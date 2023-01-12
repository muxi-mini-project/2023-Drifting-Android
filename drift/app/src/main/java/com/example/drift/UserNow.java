package com.example.drift;

/**
 * 管理当前用户
 */
public class UserNow {
   private static UserNow usernow;
   private User user;

    /**
     * 获取管理器
     */
    public static UserNow getUserNow() {
        if (usernow == null)
            usernow = new UserNow();
        return usernow;
    }

    /**
     * 通过管理器来获取当前用户
     */
    public User getUser() {
        return user;
    }

    /**
     *通过管理器设置当前用户
     */
    public void setUser(User user) {
        this.user = user;
    }
}
