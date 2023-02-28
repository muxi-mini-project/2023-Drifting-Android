package android.bignerdranch.drifting.Camera;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 单个漂流相机项目
 */
public class Camera_ {
    private String title;//项目主题
    private UUID id;//项目id
    private List<UUID> participator;//项目参与人
    private List<ImageView> photoLab;//项目所含图片

    public Camera_(String title, UUID user) {
        //title:主题
        //user:创建者/参与者
        this.title = title;
        participator = new ArrayList<UUID>();
        participator.add(user);
        id = UUID.randomUUID();
        photoLab = new ArrayList<ImageView>();
    }

    public List<ImageView> getPhotoLab() {
        return photoLab;
    }

    public String getTitle() {
        return title;
    }

    public List<UUID> getParticipator() {
        return participator;
    }
}
