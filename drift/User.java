package com.example.drift;

import android.net.Uri;
import android.widget.ImageView;

import java.util.List;
import java.util.UUID;

/**
 * 用户
 */
public class User {
    private String name;//昵称
    private UUID mUUID;//id
    private String signature;//个性签名
    private boolean sex;//0男1女
    private boolean ifTongZhi;//是否通知进度
    private drift_cameraLab Mydrift_cameraLab;//漂流相机项目库
    private String portrait;//头像

    public User() {//初始化
        mUUID = UUID.randomUUID();
        signature = "这个人很懒，没有设置个性签名";
        sex = true;
        ifTongZhi = false;
        Mydrift_cameraLab = new drift_cameraLab();
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

    public boolean isSex() {
        return sex;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public boolean isIfTongZhi() {
        return ifTongZhi;
    }

    public void setIfTongZhi(boolean ifTongZhi) {
        this.ifTongZhi = ifTongZhi;
    }

    public drift_cameraLab getMydrift_cameraLab() {
        return Mydrift_cameraLab;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
