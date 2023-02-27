package android.bignerdranch.drifting;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 漂流相机库，管理所有drift_camera(测试阶段使用)
 */
public class Camera_Lab {
    private final List<Camera_> mDrift_Cameras;
    private static Camera_Lab mDrift_cameraLab;

    /**
     * 获取库
     */
    public static Camera_Lab get(Context context) {
        if (mDrift_cameraLab == null)
            mDrift_cameraLab = new Camera_Lab();
        return mDrift_cameraLab;
    }

    public Camera_Lab() {//构造器
        mDrift_Cameras = new ArrayList<Camera_>();
    }

    /**
     * 获取List
     */
    public List<Camera_> getDrift_cameras() {
        return mDrift_Cameras;
    }

    /**
     * 创建新的漂流相机项目，这里的user应为创建人uid
     *
     * @param title 项目主题
     * @param user  项目创建人uid
     */
    public void Newdrift_camera(Context context, String name , String title, UUID user, int timeofone , int maxuser) {
        //创建漂流相机项目
        Camera_ drift_camera = new Camera_(name,title, user, timeofone,maxuser);
        //保存在总库中
        mDrift_Cameras.add(drift_camera);
        //保存在用户库中
        User_Lab.get(context).getUser(user).getMydrift_cameraLab().add(drift_camera.getId());
    }

    /**
     * 从库中获取某个漂流相机项目
     *
     * @param id 项目uid
     * @return 所需项目
     */
    public Camera_ getdrift_camera(UUID id) {
        for (Camera_ drift_camera : mDrift_Cameras)
            if (drift_camera.getId().equals(id)) {
                return drift_camera;
            }
        return null;
    }

    /**
     * 在某个漂流相机项目中添加新成员
     *
     * @param user 新人员uid
     * @param id   漂流项目uid
     */
    public void AddnewUser(Context context, UUID user, UUID id) {
        ////保存在项目数据中
        mDrift_cameraLab.getdrift_camera(id).getParticipator().add(user);
        mDrift_cameraLab.getdrift_camera(id).setCreatornow(user);
        //保存在用户数据中
        User_Lab.get(context).getUser(user).getMydrift_cameraLab().add(id);
    }
}

