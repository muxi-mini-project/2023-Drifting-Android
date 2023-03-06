package android.bignerdranch.drifting.User;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 用户
 */
public class User_ {
    private String name;//昵称
    private Integer id;//studentID
    private String signature;//个性签名
    private String sex;
    private boolean ifTongZhi;//是否通知进度
    private Bitmap avatar;
    private String portrait;//头像
    private String token;//登录获取到的token

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User_() {//初始化

        signature = "这个人很懒，没有设置个性签名";
        ifTongZhi = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }


    public boolean isIfTongZhi() {
        return ifTongZhi;
    }

    public void setIfTongZhi(boolean ifTongZhi) {
        this.ifTongZhi = ifTongZhi;
    }


    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Bitmap getavatar() {
        return avatar;
    }

    public void setavatar(Bitmap bitmap) {
        avatar = bitmap;
    }
}
