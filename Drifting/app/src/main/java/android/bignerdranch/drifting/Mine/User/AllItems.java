package android.bignerdranch.drifting.Mine.User;

import java.util.List;

public class AllItems {
    static AllItems sAllItems;
    List<String> Camera_name_user;
    List<Long> Camera_nowuser_user;
    List<Long> Camera_maxuser_user;
    public static AllItems getAllItems() {
        if (sAllItems == null)
            sAllItems = new AllItems();
        return sAllItems;
    }

    public List<String> getCamera_name_user() {
        return Camera_name_user;
    }

    public void setCamera_name_user(List<String> camera_name_user) {
        Camera_name_user = camera_name_user;
    }

    public List<Long> getCamera_nowuser_user() {
        return Camera_nowuser_user;
    }

    public void setCamera_nowuser_user(List<Long> camera_nowuser_user) {
        Camera_nowuser_user = camera_nowuser_user;
    }

    public List<Long> getCamera_maxuser_user() {
        return Camera_maxuser_user;
    }

    public void setCamera_maxuser_user(List<Long> camera_maxuser_user) {
        Camera_maxuser_user = camera_maxuser_user;
    }
}
