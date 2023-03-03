package android.bignerdranch.drifting.Camera;

import android.bignerdranch.drifting.User.User_Lab;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 单个漂流相机项目
 */
public class Camera_ {
    private final String name;//项目名称
    private final String title;//项目主题
    private String titlephoto;//项目主题图片(封面)
    private final UUID id;//项目id
    private final List<UUID> participator;//项目参与人
    private final Date date;//发起时间
    private int timeofone;//单人用时,单位:天
    private final List<String> photoLab;//项目所含图片
    private final int maxuser;//最大参与人数
    private UUID creatornow; //当前创作人
    private Boolean Ifover;//是否结束

    /**
     * 构造器
     *
     * @param name      项目名称
     * @param title     主题
     * @param user      创建者/参与者
     * @param timeofone 单人用时
     */
    public Camera_(String name, String title, UUID user, int timeofone, int maxuser) {
        if (title == null || title == "")
            this.title = "无主题";
        else
            this.title = title;
        participator = new ArrayList<UUID>();
        id = UUID.randomUUID();
        photoLab = new ArrayList<String>();
        date = new Date();
        participator.add(user);
        this.timeofone = timeofone;
        this.name = name;
        this.maxuser = maxuser;
        this.creatornow = user;
        this.Ifover = false;
    }

    public List<String> getPhotoLab() {
        return photoLab;
    }

    public String getTitle() {
        return title;
    }

    public List<UUID> getParticipator() {
        return participator;
    }

    public UUID getId() {
        return id;
    }

    public String getTimeofone() {
        String p = "" + timeofone + "天";
        return p;
    }

    public void setTimeofone(int timeofone) {
        this.timeofone = timeofone;
    }

    public String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");//进行日期格式转换
        return sdf.format(date);
    }

    public String getName() {
        return name;
    }

    public String getinitiator(Context context) {
        return User_Lab.get(context).getUser(participator.get(0)).getName();
    }

    public int getMaxuser() {
        return maxuser;
    }

    public UUID getCreatornow() {
        return creatornow;
    }

    public void setCreatornow(UUID creatornow) {
        this.creatornow = creatornow;
    }

    public Boolean getIfover() {
        return Ifover;
    }

    public void setIfover(Boolean ifover) {
        Ifover = ifover;
    }

}
