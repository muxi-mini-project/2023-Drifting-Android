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
    private Long nowuser;//目前参与人数
    private final String date;//发起时间
    private Long ID;//id
    private int timeofone;//单人用时,单位:天
    private final List<String> photoLab;//项目所含图片
    private Long maxuser;//最大参与人数
    private Long creatornow; //当前创作人
    private Boolean Ifover;//是否结束
    private int kind;//0生人1熟人

    /**
     * 构造器
     *
     * @param name      项目名称
     * @param title     主题
     */
    public Camera_(String name, String title, Long maxuser, String titlephoto,Long nowuser) {
        if (title == null || title == "")
            this.title = "无主题";
        else
            this.title = title;
        this.nowuser = nowuser;
        photoLab = new ArrayList<String>();
        this.timeofone = timeofone;
        this.name = name;
        this.date = null;
        this.maxuser = maxuser;
        this.Ifover = false;
        this.titlephoto = titlephoto;
    }

    public Long getNowuser() {
        return nowuser;
    }

    public void setNowuser(Long nowuser) {
        this.nowuser = nowuser;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getTitlephoto() {
        return titlephoto;
    }

    public void setTitlephoto(String titlephoto) {
        this.titlephoto = titlephoto;
    }

    public Long getID() {
        return ID;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public List<String> getPhotoLab() {
        return photoLab;
    }

    public String getTitle() {
        return title;
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

//    public String getinitiator(Context context) {
//        return User_Lab.get(context).getUser(participator.get(0)).getName();
//    }

    public Long getMaxuser() {
        return maxuser;
    }

    public Long getCreatornow() {
        return creatornow;
    }

    public void setCreatornow(Long creatornow) {
        this.creatornow = creatornow;
    }

    public Boolean getIfover() {
        return Ifover;
    }

    public void setIfover(Boolean ifover) {
        Ifover = ifover;
    }

}
