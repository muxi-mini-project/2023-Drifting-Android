package android.bignerdranch.drifting;

import android.media.Image;

import java.util.UUID;

public class Friends {
    private UUID mUUID;

    public UUID getUUID() {
        return mUUID;
    }

    private int imageHead;
    private String name;
    private String autograph;

    public int getImageHead() {
        return imageHead;
    }

    public void setImageHead(int imageHead) {
        this.imageHead = imageHead;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }
}
