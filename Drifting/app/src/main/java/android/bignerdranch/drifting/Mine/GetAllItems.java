package android.bignerdranch.drifting.Mine;

import android.annotation.SuppressLint;
import android.bignerdranch.drifting.Camera.Camera_Lab;
import android.bignerdranch.drifting.User.User_;
import android.bignerdranch.drifting.User.User_Lab;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 工具类，用于获取单个用户所有四种项目的名称和参与人数，并获取这些项目是否是该用户正在进行，项目是否已经结束
 * 排序：漂流本+漂流相机+漂流小说+漂流画(目前仅添加了漂流相机)
 * 目前测试阶段使用，连上接口后要改
 */
public class GetAllItems extends AppCompatActivity {
    private static GetAllItems mGetAllItems;
    private static User_ user;
    private static Context context;
    /**
     * 获取工具，绑定uid
     */

    @SuppressLint("SuspiciousIndentation")
    public static GetAllItems get(UUID uid, Context context1) {
        if (mGetAllItems == null)
            mGetAllItems = new GetAllItems();
        context = context1;
//        user = User_Lab.get(context).getUser(uid);

        return mGetAllItems;
    }
    /**
     * 获取所有项目名称
     */
    public List<String> getAllName() {
        List<String> Allnames = new ArrayList<>();
        //for(UUID id : user.getMy)漂流本
        for (UUID id : user.getMydrift_cameraLab())
            Allnames.add(Camera_Lab.get(context)
                    .getdrift_camera(id).getName());
        //for(UUID id : user.getMy)漂流小说

        //for(UUID id : user.getMy)漂流画
        return Allnames;
    }

    /**
     * 获取所有项目的最大参与人数
     */
    public List<Integer> getAllMaxuser() {
        List<Integer> Allmaxusers = new ArrayList<>();
        //for(UUID id : user.getMy)漂流本
        for (UUID id : user.getMydrift_cameraLab())
            Allmaxusers.add(Camera_Lab.get(context)
                    .getdrift_camera(id).getMaxuser());
        //for(UUID id : user.getMy)漂流小说

        //for(UUID id : user.getMy)漂流画
        return Allmaxusers;
    }

    /**
     * 获取所有项目的目前参与人数
     */
    public List<Integer> getAllnowuser() {
        List<Integer> Allnowuser = new ArrayList<>();
        //for(UUID id : user.getMy)漂流本
        for (UUID id : user.getMydrift_cameraLab())
            Allnowuser.add(Camera_Lab.get(context)
                    .getdrift_camera(id).getParticipator().size());
        //for(UUID id : user.getMy)漂流小说

        //for(UUID id : user.getMy)漂流画
        return Allnowuser;
    }

    /**
     * 判断所有项目是否结束并返回结果
     */
    public List<Boolean> getAllIfover() {
        List<Boolean> AllIfover = new ArrayList<>();
        //for(UUID id : user.getMy)漂流本
        for (UUID id : user.getMydrift_cameraLab())
            AllIfover.add(Camera_Lab.get(context)
                    .getdrift_camera(id).getIfover());
        //for(UUID id : user.getMy)漂流小说

        //for(UUID id : user.getMy)漂流画
        return AllIfover;
    }

    /**
     * 获取所有正在进行的项目名称
     */
    public List<String> getAllNameunderway() {
        List<String> Nameunderway = new ArrayList<>();
        List<String> Allname = getAllName();
        List<Boolean> AllIfover = getAllIfover();
        for (int i = 0; i < Allname.size(); i++)
            if (!AllIfover.get(i))
                Nameunderway.add(Allname.get(i));
        return Nameunderway;
    }

    /**
     * 获取所有已结束的项目的名称
     */
    public List<String> getAllNameover() {
        List<String> Nameunderway = new ArrayList<>();
        List<String> Allname = getAllName();
        List<Boolean> AllIfover = getAllIfover();
        for (int i = 0; i < AllIfover.size(); i++)
            if (AllIfover.get(i))
                Nameunderway.add(Allname.get(i));
        return Nameunderway;
    }

    /**
     * 获取所有正在进行的项目的最大参与人数
     */
    public List<Integer> getAllMaxuserunderway() {
        List<Integer> ALlMaxuserunderway = new ArrayList<>();
        List<Integer> AllMaxuser = getAllMaxuser();
        List<Boolean> AllIfover = getAllIfover();
        for (int i = 0; i < AllIfover.size(); i++)
            if (!AllIfover.get(i))
                ALlMaxuserunderway.add(AllMaxuser.get(i));
        return ALlMaxuserunderway;
    }

    /**
     * 获取所有已结束的项目的最大参与人数
     */
    public List<Integer> getAllMaxuserover() {
        List<Integer> ALlMaxuserunderway = new ArrayList<>();
        List<Integer> AllMaxuser = getAllMaxuser();
        List<Boolean> AllIfover = getAllIfover();
        for (int i = 0; i < AllIfover.size(); i++)
            if (AllIfover.get(i))
                ALlMaxuserunderway.add(AllMaxuser.get(i));
        return ALlMaxuserunderway;
    }

    /**
     * 获取所有正在进行的项目的目前参与人数
     */
    public List<Integer> getAllnowuserunderway() {
        List<Integer> Allnowuserunderway = new ArrayList<>();
        List<Integer> Allnowuser = getAllnowuser();
        List<Boolean> AllIfover = getAllIfover();
        for (int i = 0; i < AllIfover.size(); i++)
            if (!AllIfover.get(i))
                Allnowuserunderway.add(Allnowuser.get(i));
        return Allnowuserunderway;
    }

    /**
     * 获取所有已结束的项目的参与人数
     */
    public List<Integer> getAllnowuserover() {
        List<Integer> Allnowuserover = new ArrayList<>();
        List<Integer> Allnowuser = getAllnowuser();
        List<Boolean> AllIfover = getAllIfover();
        for (int i = 0; i < AllIfover.size(); i++)
            if (AllIfover.get(i))
                Allnowuserover.add(Allnowuser.get(i));
        return Allnowuserover;
    }

    /**
     * 判断所有正在进行项目当前创作者是否是该用户并返回结果
     */
    public List<Boolean> getAllIfuserunderway() {
        List<Boolean> AllIfuserunderway = new ArrayList<>();
        //for(UUID id : user.getMy)漂流本
        for (UUID id : user.getMydrift_cameraLab())
            if (!Camera_Lab.get(context).getdrift_camera(id).getIfover())
                AllIfuserunderway.add(Camera_Lab.get(context)
                        .getdrift_camera(id).getCreatornow().equals(""));
        //for(UUID id : user.getMy)漂流小说

        //for(UUID id : user.getMy)漂流画
        return AllIfuserunderway;
    }

}
