package com.example.drift;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 漂流相机库，管理所有drift_camera
 */
public class drift_cameraLab {
    private List<Drift_Camera> mDrift_Cameras;
    private static drift_cameraLab mDrift_cameraLab;

    /**
     * 获取库
     */
    public static drift_cameraLab get(Context context) {
        if (mDrift_cameraLab == null)
            mDrift_cameraLab = new drift_cameraLab();
        return mDrift_cameraLab;
    }

    public drift_cameraLab() {//构造器
        mDrift_Cameras = new ArrayList<Drift_Camera>();
    }

    /**
     * 获取List
     */
    public List<Drift_Camera> getDrift_cameras() {
        return mDrift_Cameras;
    }

    /**
     * 创建新的漂流相机项目，这里的user应为创建人uid
     *
     * @param title 项目主题
     * @param user  项目创建人uid
     */
    public void Newdrift_camera(Context context, String name ,String title, UUID user, int timeofone ,int maxuser) {
        //创建漂流相机项目
        Drift_Camera drift_camera = new Drift_Camera(name,title, user, timeofone,maxuser);
        //保存在总库中
        mDrift_Cameras.add(drift_camera);
        //保存在用户库中
        UserLab.get(context).getUser(user).getMydrift_cameraLab().add(drift_camera.getId());
    }

    /**
     * 从库中获取某个漂流相机项目
     *
     * @param id 项目uid
     * @return 所需项目
     */
    public Drift_Camera getdrift_camera(UUID id) {
        for (Drift_Camera drift_camera : mDrift_Cameras)
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
        UserLab.get(context).getUser(user).getMydrift_cameraLab().add(id);
    }
}
