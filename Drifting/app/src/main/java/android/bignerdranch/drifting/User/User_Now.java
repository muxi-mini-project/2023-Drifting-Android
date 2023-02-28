package android.bignerdranch.drifting.User;

public class User_Now {
    static User_Now usernow;
    User_ user;

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
