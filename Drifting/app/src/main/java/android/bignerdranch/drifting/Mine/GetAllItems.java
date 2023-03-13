package android.bignerdranch.drifting.Mine;

import android.bignerdranch.drifting.Camera.Camera_;
import android.bignerdranch.drifting.Camera.Camera_connector;
import android.bignerdranch.drifting.Camera.Camera_return_upload;
import android.bignerdranch.drifting.Mine.User.User_Now;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 工具类，用于获取单个用户所有四种项目的名称和参与人数，并获取这些项目是否是该用户正在进行，项目是否已经结束
 * 排序：漂流本+漂流相机+漂流小说+漂流画(目前仅添加了漂流相机)
 * 目前测试阶段使用，连上接口后要改
 */
public class GetAllItems extends AppCompatActivity {
   public final static int BOOK = 1001;
   public final static int CAMERA = 1002;
   public final static int NOVEL = 1003;
   public final static int DRAW = 1004;
    private static GetAllItems mGetAllItems;
    /**
     * 缓存，避免重复使用接口
     */
    //漂流相机
    List<Camera_> List_Camera_userAll = new ArrayList<>();//获取用户参加的和自己创建的漂流相机
    List<Camera_> List_Camera_ownercrea = new ArrayList<>();//获取用户自己创建的漂流相机

    static public GetAllItems getGetAllItems() {
        if (mGetAllItems == null)
            mGetAllItems = new GetAllItems();
        return mGetAllItems;
    }

    static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://116.204.121.9:61583/")
            .addConverterFactory(GsonConverterFactory.create());
    static Retrofit retrofit = builder.build();
    static Camera_connector mCamera_connector = retrofit.create(Camera_connector.class);

    /**
     * 获取用户自己创建的漂流相机
     */
    //项目
    public List<Camera_> GetCamera_ownercrea() {
        if (List_Camera_ownercrea.isEmpty()) {
            List<Camera_> camera_list = new ArrayList<>();
            String token = User_Now.getUserNow().getUser().getToken();
            Call<Camera_return_upload.Camera_return_usermessage> call = mCamera_connector.GetCamera_mes_user(token);
            call.enqueue(new Callback<Camera_return_upload.Camera_return_usermessage>() {
                @Override
                public void onResponse(Call<Camera_return_upload.Camera_return_usermessage> call, Response<Camera_return_upload.Camera_return_usermessage> response) {
                    Camera_return_upload.Camera_return_usermessage message = response.body();
                    List<Camera_return_upload.Camera_return_usermessage2> list = response.body().getData();
                    for (int i = 0; i < list.size(); i++) {
                        Camera_ camera = new Camera_(list.get(i).getName(),
                                list.get(i).getTheme(),
                                list.get(i).getNumber(),
                                list.get(i).getCover(),
                                list.get(i).getWriter_number());
                        camera_list.add(camera);
                    }
                    setList_Camera_ownercrea(camera_list);
                }

                @Override
                public void onFailure(Call<Camera_return_upload.Camera_return_usermessage> call, Throwable t) {
                }
            });
            return camera_list;
        } else {
            return List_Camera_ownercrea;
        }
    }

    //项目名字
    public List<String> GetCamera_ownercrea_name() {
        if (getGetAllItems().getList_Camera_ownercrea().isEmpty())
            GetCamera_ownercrea();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < getList_Camera_ownercrea().size(); i++)
            list.add(getList_Camera_ownercrea().get(i).getName());
        return list;
    }

    //项目最大人数
    public List<Long> GetCamera_ownercrea_maxuser() {
        if (getGetAllItems().getList_Camera_ownercrea().isEmpty())
            GetCamera_ownercrea();
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < getGetAllItems().getList_Camera_ownercrea().size(); i++)
            list.add(getGetAllItems().getList_Camera_ownercrea().get(i).getMaxuser());
        return list;
    }

    //项目当前人数
    public List<Long> GetCamera_ownercrea_nowuser() {
        if (getGetAllItems().getList_Camera_ownercrea().isEmpty())
            GetCamera_ownercrea();
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < getGetAllItems().getList_Camera_ownercrea().size(); i++)
            list.add(getGetAllItems().getList_Camera_ownercrea().get(i).getNowuser());
        return list;
    }

    public void refreshMessage(int kind) {
        switch (kind) {
            case 1001: {

                break;
            }
            case 1002: {
                List_Camera_ownercrea.clear();
                List_Camera_userAll.clear();
                break;
            }
            case 1003: {

            }
            case 1004: {

            }
        }

    }

    public void setList_Camera_userAll(List<Camera_> list_Camera_userAll) {
        List_Camera_userAll = list_Camera_userAll;
    }

    public void setList_Camera_ownercrea(List<Camera_> list_Camera_ownercrea) {
        List_Camera_ownercrea = list_Camera_ownercrea;
    }

    public List<Camera_> getList_Camera_userAll() {
        return List_Camera_userAll;
    }

    public List<Camera_> getList_Camera_ownercrea() {
        return List_Camera_ownercrea;
    }
}
