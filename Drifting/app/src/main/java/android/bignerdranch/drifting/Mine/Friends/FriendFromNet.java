package android.bignerdranch.drifting.Mine.Friends;

public class FriendFromNet {
    private String name;
    private String selfWord;
    private String sex;
    private String avatar;

    //加n表示新的，防止和其他的冲突
    public String getNameN() {
        return name;
    }

    public void setNameN(String name) {
        this.name = name;
    }

    public String getSelfWord() {
        return selfWord;
    }

    public void setSelfWord(String selfWord) {
        this.selfWord = selfWord;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
