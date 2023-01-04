package com.example.drift;

import java.util.UUID;

public class User {
    private String name;
    private UUID mUUID;
    private String signature;
    private boolean sex;//0男1女

    public User() {//初始化
        mUUID = UUID.randomUUID();
        signature = "这个人很懒，没有设置个性签名";
        sex = true;
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
}
