package android.bignerdranch.drifting.Mine;

import android.bignerdranch.drifting.Camera.Camera_return_upload;
import android.bignerdranch.drifting.User.User_Now;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

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
    static int update;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 200 && update == 7) {
                List_Items_userAll = new ArrayList<>();
                List_Items_userAll.addAll(List_Camera_user_create);
                List_Items_userAll.addAll(List_Camera_user_attend);
                List_Items_userAll.addAll(List_Book_user_create);
                List_Items_userAll.addAll(List_Book_user_attend);
                List_Items_userAll.addAll(List_Drawing_user_create);
                List_Items_userAll.addAll(List_Drawing_user_attend);
                List_Items_userAll.addAll(List_Novel_user_create);
                List_Items_userAll.addAll(List_Novel_user_attend);
                List_Items_userAll_create.addAll(List_Camera_user_create);
                List_Items_userAll_create.addAll(List_Book_user_create);
                List_Items_userAll_create.addAll(List_Drawing_user_create);
                List_Items_userAll_create.addAll(List_Novel_user_create);
                List_Items_userAll_attend.addAll(List_Camera_user_attend);
                List_Items_userAll_attend.addAll(List_Book_user_attend);
                List_Items_userAll_attend.addAll(List_Drawing_user_attend);
                List_Items_userAll_attend.addAll(List_Novel_user_attend);
                Log.d("GetAllItems", "添加完成");
                Get_message();
                Log.d("GetAllItems", "管理器获取成功");
            }else
                update++;
            Log.d("GetAllItems", update + "");
        }
    };
    /**
     * 缓存，避免重复使用接口
     */
    //漂流相机
    List<Items> List_Items_userAll = new ArrayList<>();//用户参加的和自己创建的所有项目

    List<Items> List_Items_userAll_create = new ArrayList<>();//用户创建的所有项目
    List<Items> List_Items_userAll_attend = new ArrayList<>();//用户参加的所有项目

    List<Items> List_Items_All_ing = new ArrayList<>();//用户所有正在进行的项目
    List<Items> List_Items_All_ed = new ArrayList<>();//用户所有已经结束的项目

    List<Items> List_Items_All_create_ing = new ArrayList<>();//用户所有正在进行且由用户创建的项目
    List<Items> List_Items_All_attend_ing = new ArrayList<>();//用户所有正在进行且用户参与的项目
    List<Items> List_Items_All_create_ed = new ArrayList<>();//用户所有已经结束且由用户创建的项目
    List<Items> List_Items_All_attend_ed = new ArrayList<>();//用户所有已经结束且用户参与的项目

    List<Items> List_Camera_user_create = new ArrayList<>();//用户创建的漂流相机
    List<Items> List_Camera_user_attend = new ArrayList<>();//用户参加的漂流相机
    List<Items> List_Book_user_create = new ArrayList<>();//用户创建的漂流本
    List<Items> List_Book_user_attend = new ArrayList<>();//用户参加的漂流本
    List<Items> List_Drawing_user_create = new ArrayList<>();//用户创建的漂流画
    List<Items> List_Drawing_user_attend = new ArrayList<>();//用户参加的漂流画
    List<Items> List_Novel_user_create = new ArrayList<>();//用户创建的漂流小说
    List<Items> List_Novel_user_attend = new ArrayList<>();//用户参加的漂流小说

    static public GetAllItems getGetAllItems() {
        if (mGetAllItems == null)
            mGetAllItems = new GetAllItems();
        mGetAllItems.update = 0;
        return mGetAllItems;
    }

    static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://116.204.121.9:61583/")
            .addConverterFactory(GsonConverterFactory.create());
    static Retrofit retrofit = builder.build();
    static Mine_items sMine_items = retrofit.create(Mine_items.class);

    public void GetAll_Items() {
        if (List_Items_userAll == null || List_Items_userAll.size() == 0) {
            GetCamera_user_create();
            GetCamera_user_attend();
            GetBook_user_create();
            GetBook_user_attend();
            GetDrawing_user_create();
            GetDrawing_user_attend();
            GetNovel_user_create();
            GetNovel_user_attend();
        }
    }

    /**
     * 获取用户自己创建的漂流相机
     */
    //项目
    public void GetCamera_user_create() {
        List<Items> camera_list = new ArrayList<>();
        String token = User_Now.getUserNow().getUser().getToken();
        Call<Camera_return_upload.Items_return_usermessage> call = sMine_items.GetCamera_user_create(token);
        call.enqueue(new Callback<Camera_return_upload.Items_return_usermessage>() {
            @Override
            public void onResponse(Call<Camera_return_upload.Items_return_usermessage> call, Response<Camera_return_upload.Items_return_usermessage> response) {
                if (response.isSuccessful()) {
                    List<Camera_return_upload.Camera_return_usermessage2> list = response.body().getData();
                    if (list != null)
                        Log.d("manager_bug_list2", list.size() + "");
                    if (list != null)
                        for (int i = 0; i < list.size(); i++) {
                            Items item = new Items(list.get(i).getName(),
                                    list.get(i).getTheme(),
                                    list.get(i).getCover(),
                                    "漂流相机",
                                    list.get(i).getKind(),
                                    list.get(i).getWriter_number(),
                                    list.get(i).getNumber(),
                                    list.get(i).getCreatedAt(),
                                    list.get(i).getID(),
                                    list.get(i).getOwnerID());
                            camera_list.add(item);
                        }
                    List_Camera_user_create = camera_list;
                    Log.d("GetAllItems", "漂流相机1获取成功");
                    Message message = new Message();
                    message.what = 200;
                    mHandler.sendMessageAtTime(message,0);
                }
            }

            @Override
            public void onFailure(Call<Camera_return_upload.Items_return_usermessage> call, Throwable t) {
                Log.d("GetAllItems_camera", t.toString());
            }
        });
    }

    /**
     * 获取用户参加的漂流相机（不包含自己创建的）
     */
    public void GetCamera_user_attend() {
        List<Items> camera_list = new ArrayList<>();
        String token = User_Now.getUserNow().getUser().getToken();
        Call<Camera_return_upload.Items_return_usermessage> call = sMine_items.GetCamera_user_attend(token);
        call.enqueue(new Callback<Camera_return_upload.Items_return_usermessage>() {
            @Override
            public void onResponse(Call<Camera_return_upload.Items_return_usermessage> call, Response<Camera_return_upload.Items_return_usermessage> response) {
                if (response.isSuccessful()) {
                    List<Camera_return_upload.Camera_return_usermessage2> list = response.body().getData();
                    if (list != null)
                        Log.d("manager_bug_list2", list.size() + "");
                    if (list != null)
                        for (int i = 0; i < list.size(); i++) {
                            Items item = new Items(list.get(i).getName(),
                                    list.get(i).getTheme(),
                                    list.get(i).getCover(),
                                    "漂流相机",
                                    list.get(i).getKind(),
                                    list.get(i).getWriter_number(),
                                    list.get(i).getNumber(),
                                    list.get(i).getCreatedAt(),
                                    list.get(i).getID(),
                                    list.get(i).getOwnerID());
                            camera_list.add(item);
                        }
                    List_Camera_user_attend = camera_list;
                    Log.d("GetAllItems", "漂流相机2获取成功");
                    Message message = new Message();
                    message.what = 200;
                    mHandler.sendMessageAtTime(message,0);
                }
            }

            @Override
            public void onFailure(Call<Camera_return_upload.Items_return_usermessage> call, Throwable t) {
                Log.d("GetAllItems_camera", t.toString());
            }
        });
    }

    /**
     * 获取用户自己创建的漂流本
     */
    public void GetBook_user_create() {
        List<Items> book_list = new ArrayList<>();
        String token = User_Now.getUserNow().getUser().getToken();
        Call<Camera_return_upload.Items_return_usermessage> call = sMine_items.GetBook_user_create(token);
        call.enqueue(new Callback<Camera_return_upload.Items_return_usermessage>() {
            @Override
            public void onResponse(Call<Camera_return_upload.Items_return_usermessage> call, Response<Camera_return_upload.Items_return_usermessage> response) {
                if (response.isSuccessful()) {
                    List<Camera_return_upload.Camera_return_usermessage2> list = response.body().getData();
                    if (list != null)
                        for (int i = 0; i < list.size(); i++) {
                            Items item = new Items(list.get(i).getName(),
                                    list.get(i).getTheme(),
                                    list.get(i).getCover(),
                                    "漂流本",
                                    list.get(i).getKind(),
                                    list.get(i).getWriter_number(),
                                    list.get(i).getNumber(),
                                    list.get(i).getCreatedAt(),
                                    list.get(i).getID(),
                                    list.get(i).getOwnerID());
                            book_list.add(item);
                        }
                    List_Book_user_create = book_list;
                    Log.d("GetAllItems", "漂流本1获取成功");
                    Message message = new Message();
                    message.what = 200;
                    mHandler.sendMessageAtTime(message,0);
                }
            }

            @Override
            public void onFailure(Call<Camera_return_upload.Items_return_usermessage> call, Throwable t) {
                Log.d("GetAllItems_book", t.toString());
            }
        });
    }

    /**
     * 获取用户参加的漂流本
     */
    public void GetBook_user_attend() {
        List<Items> book_list = new ArrayList<>();
        String token = User_Now.getUserNow().getUser().getToken();
        Call<Camera_return_upload.Items_return_usermessage> call = sMine_items.GetBook_user_attend(token);
        call.enqueue(new Callback<Camera_return_upload.Items_return_usermessage>() {
            @Override
            public void onResponse(Call<Camera_return_upload.Items_return_usermessage> call, Response<Camera_return_upload.Items_return_usermessage> response) {
                if (response.isSuccessful()) {
                    List<Camera_return_upload.Camera_return_usermessage2> list = response.body().getData();
                    if (list != null)
                        for (int i = 0; i < list.size(); i++) {
                            Items item = new Items(list.get(i).getName(),
                                    list.get(i).getTheme(),
                                    list.get(i).getCover(),
                                    "漂流本",
                                    list.get(i).getKind(),
                                    list.get(i).getWriter_number(),
                                    list.get(i).getNumber(),
                                    list.get(i).getCreatedAt(),
                                    list.get(i).getID(),
                                    list.get(i).getOwnerID());
                            book_list.add(item);
                        }
                    List_Book_user_attend = book_list;
                    Log.d("GetAllItems", "漂流本2获取成功");
                    Message message = new Message();
                    message.what = 200;
                    mHandler.sendMessageAtTime(message,0);
                }
            }

            @Override
            public void onFailure(Call<Camera_return_upload.Items_return_usermessage> call, Throwable t) {
                Log.d("GetAllItems_book", t.toString());
            }
        });
    }

    /**
     * 获取用户创建的漂流画
     */
    public void GetDrawing_user_create() {
        List<Items> drawing_list = new ArrayList<>();
        String token = User_Now.getUserNow().getUser().getToken();
        Call<Camera_return_upload.Items_return_usermessage> call = sMine_items.GetDrawing_user_create(token);
        call.enqueue(new Callback<Camera_return_upload.Items_return_usermessage>() {
            @Override
            public void onResponse(Call<Camera_return_upload.Items_return_usermessage> call, Response<Camera_return_upload.Items_return_usermessage> response) {
                if (response.isSuccessful()) {
                    List<Camera_return_upload.Camera_return_usermessage2> list = response.body().getData();
                    if (list != null)
                        for (int i = 0; i < list.size(); i++) {
                            Items item = new Items(list.get(i).getName(),
                                    list.get(i).getTheme(),
                                    list.get(i).getCover(),
                                    "漂流画",
                                    list.get(i).getKind(),
                                    list.get(i).getWriter_number(),
                                    list.get(i).getNumber(),
                                    list.get(i).getCreatedAt(),
                                    list.get(i).getID(),
                                    list.get(i).getOwnerID());
                            drawing_list.add(item);
                        }
                    List_Drawing_user_create = drawing_list;
                    Log.d("GetAllItems", "漂流画1获取成功");
                    Message message = new Message();
                    message.what = 200;
                    mHandler.sendMessageAtTime(message,0);
                }
            }

            @Override
            public void onFailure(Call<Camera_return_upload.Items_return_usermessage> call, Throwable t) {
                Log.d("GetAllItems_drawing", t.toString());
            }
        });
    }

    /**
     * 获取用户参与的漂流画
     */
    public void GetDrawing_user_attend() {
        List<Items> drawing_list = new ArrayList<>();
        String token = User_Now.getUserNow().getUser().getToken();
        Call<Camera_return_upload.Items_return_usermessage> call = sMine_items.GetDrawing_user_attend(token);
        call.enqueue(new Callback<Camera_return_upload.Items_return_usermessage>() {
            @Override
            public void onResponse(Call<Camera_return_upload.Items_return_usermessage> call, Response<Camera_return_upload.Items_return_usermessage> response) {
                if (response.isSuccessful()) {
                    List<Camera_return_upload.Camera_return_usermessage2> list = response.body().getData();
                    if (list != null)
                        for (int i = 0; i < list.size(); i++) {
                            Items item = new Items(list.get(i).getName(),
                                    list.get(i).getTheme(),
                                    list.get(i).getCover(),
                                    "漂流画",
                                    list.get(i).getKind(),
                                    list.get(i).getWriter_number(),
                                    list.get(i).getNumber(),
                                    list.get(i).getCreatedAt(),
                                    list.get(i).getID(),
                                    list.get(i).getOwnerID());
                            drawing_list.add(item);
                        }
                    List_Drawing_user_attend = drawing_list;
                    Log.d("GetAllItems", "漂流画2获取成功");
                    Message message = new Message();
                    message.what = 200;
                    mHandler.sendMessageAtTime(message,0);
                }
            }

            @Override
            public void onFailure(Call<Camera_return_upload.Items_return_usermessage> call, Throwable t) {
                Log.d("GetAllItems_drawing", t.toString());
            }
        });
    }

    /**
     * 获取用户创建的漂流小说
     */
    public void GetNovel_user_create() {
        List<Items> novel_list = new ArrayList<>();
        String token = User_Now.getUserNow().getUser().getToken();
        Call<Camera_return_upload.Items_return_usermessage> call = sMine_items.GetNovel_user_create(token);
        call.enqueue(new Callback<Camera_return_upload.Items_return_usermessage>() {
            @Override
            public void onResponse(Call<Camera_return_upload.Items_return_usermessage> call, Response<Camera_return_upload.Items_return_usermessage> response) {
                if (response.isSuccessful()) {
                    List<Camera_return_upload.Camera_return_usermessage2> list = response.body().getData();
                    if (list != null)
                        for (int i = 0; i < list.size(); i++) {
                            Items item = new Items(list.get(i).getName(),
                                    list.get(i).getTheme(),
                                    list.get(i).getCover(),
                                    "漂流小说",
                                    list.get(i).getKind(),
                                    list.get(i).getWriter_number(),
                                    list.get(i).getNumber(),
                                    list.get(i).getCreatedAt(),
                                    list.get(i).getID(),
                                    list.get(i).getOwnerID());
                            novel_list.add(item);
                        }
                    List_Novel_user_create = novel_list;
                    Log.d("GetAllItems", "漂流小说1获取成功");
                    Message message = new Message();
                    message.what = 200;
                    mHandler.sendMessageAtTime(message,0);
                }
            }

            @Override
            public void onFailure(Call<Camera_return_upload.Items_return_usermessage> call, Throwable t) {
                Log.d("GetAllItems_novel", t.toString());
            }
        });
    }

    /**
     * 获取用户参与的漂流小说
     */
    public void GetNovel_user_attend() {
        List<Items> novel_list = new ArrayList<>();
        String token = User_Now.getUserNow().getUser().getToken();
        Call<Camera_return_upload.Items_return_usermessage> call = sMine_items.GetNovel_user_attend(token);
        call.enqueue(new Callback<Camera_return_upload.Items_return_usermessage>() {
            @Override
            public void onResponse(Call<Camera_return_upload.Items_return_usermessage> call, Response<Camera_return_upload.Items_return_usermessage> response) {
                if (response.isSuccessful()) {
                    List<Camera_return_upload.Camera_return_usermessage2> list = response.body().getData();
                    if (list != null)
                        for (int i = 0; i < list.size(); i++) {
                            Items item = new Items(list.get(i).getName(),
                                    list.get(i).getTheme(),
                                    list.get(i).getCover(),
                                    "漂流小说",
                                    list.get(i).getKind(),
                                    list.get(i).getWriter_number(),
                                    list.get(i).getNumber(),
                                    list.get(i).getCreatedAt(),
                                    list.get(i).getID(),
                                    list.get(i).getOwnerID());
                            novel_list.add(item);
                        }
                    List_Novel_user_attend = novel_list;
                    Log.d("GetAllItems", "漂流小说2获取成功");
                    Message message = new Message();
                    message.what = 200;
                    mHandler.sendMessageAtTime(message,0);
                }
            }

            @Override
            public void onFailure(Call<Camera_return_upload.Items_return_usermessage> call, Throwable t) {
                Log.d("GetAllItems_novel", t.toString());
            }
        });
    }

    /**
     * 新创建项目后引用来重新获取项目
     */
    public void refreshMessage() {
        update = 0;
        List_Items_userAll = new ArrayList<>();//用户参加的和自己创建的所有项目
        List_Items_userAll_create = new ArrayList<>();//用户创建的所有项目
        List_Items_userAll_attend = new ArrayList<>();//用户参加的所有项目
        List_Items_All_ing = new ArrayList<>();//用户所有正在进行的项目
        List_Items_All_ed = new ArrayList<>();//用户所有已经结束的项目
        List_Items_All_create_ing = new ArrayList<>();//用户所有正在进行且由用户创建的项目
        List_Items_All_attend_ing = new ArrayList<>();//用户所有正在进行且用户参与的项目
        List_Items_All_create_ed = new ArrayList<>();//用户所有已经结束且由用户创建的项目
        List_Items_All_attend_ed = new ArrayList<>();//用户所有已经结束且用户参与的项目
        List_Camera_user_create = new ArrayList<>();//用户创建的漂流相机
        List_Camera_user_attend = new ArrayList<>();//用户参加的漂流相机
        List_Book_user_create = new ArrayList<>();//用户创建的漂流本
        List_Book_user_attend = new ArrayList<>();//用户参加的漂流本
        List_Drawing_user_create = new ArrayList<>();//用户创建的漂流画
        List_Drawing_user_attend = new ArrayList<>();//用户参加的漂流画
        List_Novel_user_create = new ArrayList<>();//用户创建的漂流小说
        List_Novel_user_attend = new ArrayList<>();//用户参加的漂流小说
        GetAll_Items();
    }

    /**
     * 获取所有正在进行的项目
     */
    public void GetAll_items_ing() {
        List<Items> ing = new ArrayList<>();
        if (List_Items_userAll != null && List_Items_userAll.size() != 0)
            for (int i = 0; i < List_Items_userAll.size(); i++)
                if (!(List_Items_userAll.get(i).getNowamount() == List_Items_userAll.get(i).getMaxamount()))
                    ing.add(List_Items_userAll.get(i));
        List_Items_All_ing = ing;
    }

    /**
     * 获取所有已经结束的项目
     */
    public void GetAll_items_ed() {
        List<Items> ed = new ArrayList<>();
        if (List_Items_userAll != null && List_Items_userAll.size() != 0)
            for (int i = 0; i < List_Items_userAll.size(); i++)
                if (List_Items_userAll.get(i).getNowamount() >= List_Items_userAll.get(i).getMaxamount())
                    ed.add(List_Items_userAll.get(i));
        Log.d("manager_bug_number2", ed.size() + "");
        List_Items_All_ed = ed;
    }

    /**
     * 获取所有正在进行且由用户创建的项目
     */
    public void GetAll_items_create_ing() {
        List<Items> ing = new ArrayList<>();
        if (List_Items_userAll_create != null && List_Items_userAll_create.size() != 0)
            for (int i = 0; i < List_Items_userAll_create.size(); i++)
                if (!(List_Items_userAll_create.get(i).getNowamount() == List_Items_userAll_create.get(i).getMaxamount()))
                    ing.add(List_Items_userAll_create.get(i));
        List_Items_All_create_ing = ing;
    }

    /**
     * 获取所有已经结束且由用户创建的项目
     */
    public void GetAll_items_create_ed() {
        List<Items> ed = new ArrayList<>();
        if (List_Items_userAll_create != null && List_Items_userAll_create.size() != 0)
            for (int i = 0; i < List_Items_userAll_create.size(); i++)
                if (List_Items_userAll_create.get(i).getNowamount() >= List_Items_userAll_create.get(i).getMaxamount())
                    ed.add(List_Items_userAll_create.get(i));
        List_Items_All_create_ed = ed;
    }

    /**
     * 获取所有正在进行且用户参与的项目
     */
    public void GetAll_items_attend_ing() {
        List<Items> ing = new ArrayList<>();
        if (List_Items_userAll_attend != null && List_Items_userAll_attend.size() != 0)
            for (int i = 0; i < List_Items_userAll_attend.size(); i++)
                if (!(List_Items_userAll_attend.get(i).getNowamount() == List_Items_userAll_attend.get(i).getMaxamount()))
                    ing.add(List_Items_userAll_attend.get(i));
        List_Items_All_attend_ing = ing;
    }

    /**
     * 获取所有已经结束且用户参与的项目
     */
    public void GetAll_items_attend_ed() {
        List<Items> ed = new ArrayList<>();
        if (List_Items_userAll_attend != null && List_Items_userAll_attend.size() != 0)
            for (int i = 0; i < List_Items_userAll_attend.size(); i++)
                if (List_Items_userAll_attend.get(i).getNowamount() >= List_Items_userAll_attend.get(i).getMaxamount())
                    ed.add(List_Items_userAll_attend.get(i));
        List_Items_All_attend_ed = ed;
    }

    /**
     * 获取管理器所需内容
     */
    public void Get_message() {
        GetAll_items_ing();
        GetAll_items_ed();
        GetAll_items_create_ing();
        GetAll_items_create_ed();
        GetAll_items_attend_ing();
        GetAll_items_attend_ed();
    }

    public List<Items> getList_Items_All_ing() {
        return List_Items_All_ing;
    }

    public List<Items> getList_Items_All_ed() {
        return List_Items_All_ed;
    }

    public List<Items> getList_Items_All_create_ing() {
        return List_Items_All_create_ing;
    }

    public List<Items> getList_Items_All_attend_ing() {
        return List_Items_All_attend_ing;
    }

    public List<Items> getList_Items_All_create_ed() {
        return List_Items_All_create_ed;
    }

    public List<Items> getList_Items_All_attend_ed() {
        return List_Items_All_attend_ed;
    }

    public List<Items> getList_Camera_userAll() {
        return List_Items_userAll;
    }

    public List<Items> getList_Camera_user_create() {
        return List_Camera_user_create;
    }

    public List<Items> getList_Items_userAll() {
        return List_Items_userAll;
    }
}