package android.bignerdranch.drifting.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 用户
 */
public class User_ {
    private String name;//昵称
    private UUID mUUID;//id
    private String signature;//个性签名
    private String sex;
    private boolean ifTongZhi;//是否通知进度
    private final List<UUID> mMydrift_cameraLab;//漂流相机项目库
    private String portrait;//头像

    public User_() {//初始化
        mUUID = UUID.randomUUID();
        signature = "这个人很懒，没有设置个性签名";
        ifTongZhi = false;
        mMydrift_cameraLab = new ArrayList<UUID>();
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

    public UUID getUUID() {
        return mUUID;
    }

    public boolean isIfTongZhi() {
        return ifTongZhi;
    }

    public void setIfTongZhi(boolean ifTongZhi) {
        this.ifTongZhi = ifTongZhi;
    }

    public List<UUID> getMydrift_cameraLab() {
        return mMydrift_cameraLab;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
