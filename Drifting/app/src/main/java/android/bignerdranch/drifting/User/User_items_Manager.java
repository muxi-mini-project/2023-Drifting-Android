package android.bignerdranch.drifting.User;

import android.app.AlertDialog;
import android.bignerdranch.drifting.Book.Book_Writing;
import android.bignerdranch.drifting.Camera.Camera_Activity;
import android.bignerdranch.drifting.Camera.Camera_Start;
import android.bignerdranch.drifting.Camera.Camera_return_upload;
import android.bignerdranch.drifting.Drawing.Drawing_Activity;
import android.bignerdranch.drifting.Mine.Detail_Activity;
import android.bignerdranch.drifting.Mine.Detail_novel_underway;
import android.bignerdranch.drifting.Mine.GetAllItems;
import android.bignerdranch.drifting.Mine.Items;
import android.bignerdranch.drifting.Mine.Mine_items;
import android.bignerdranch.drifting.R;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class User_items_Manager extends AppCompatActivity {
    Button mAllitems;
    Button mMyitems;
    Button mAttenditems;
    TextView mReturn;
    RecyclerView mItems_Manager;
    RecyclerView.Adapter mAll_ing;
    RecyclerView.Adapter mAll_ed;
    RecyclerView.Adapter mCreate_ing;
    RecyclerView.Adapter mCreate_ed;
    RecyclerView.Adapter mAttend_ing;
    RecyclerView.Adapter mAttend_ed;
    int manager_kind;
    String Now_manager;//默认进入ALL界面，分为ALL、MINE、ATTEND三种
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 200)
                updateview();
        }
    };
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK)
                updateview();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_manager);
        mAllitems = findViewById(R.id.all_items);
        mMyitems = findViewById(R.id.my_items);
        mAttenditems = findViewById(R.id.attend_items);
        mReturn = findViewById(R.id.return_Minefragment);
        mItems_Manager = findViewById(R.id.items_manager);
        mItems_Manager.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        manager_kind = getIntent().getIntExtra("manager_kind", 0);
        Log.d("manager_bug_kind", manager_kind + "");
        if (manager_kind == 0) {
            mAll_ing = new Manager_All_ing();
            mCreate_ing = new Manager_create_Adapting();
            mAttend_ing = new Manager_attend_Adapting();
            mItems_Manager.setAdapter(mAll_ing);
            Now_manager = "ALL";
        } else if (manager_kind == 1) {
            mAll_ed = new Manager_All_ed();
            mCreate_ed = new Manager_create_Adapted();
            mAttend_ed = new Manager_attend_Adapted();
            mItems_Manager.setAdapter(mAll_ed);
            Now_manager = "ALL";
        }

        mAllitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceView("ALL", manager_kind);
                Now_manager = "ALL";
            }
        });
        mAttenditems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceView("ATTEND", manager_kind);
                Now_manager = "ATTEND";
            }
        });
        mMyitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceView("MINE", manager_kind);
                Now_manager = "MINE";
            }
        });
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    /**
     * @param change       ALL、MINE、ATTEND
     * @param manager_kind 0/1
     */
    private void replaceView(String change, int manager_kind) {
        if (!change.equals(Now_manager)) {
            if (manager_kind == 0)
                switch (change) {
                    case "ALL":
                        mItems_Manager.setAdapter(mAll_ing);
                        break;
                    case "MINE":
                        mItems_Manager.setAdapter(mCreate_ing);
                        break;
                    case "ATTEND":
                        mItems_Manager.setAdapter(mAttend_ing);
                        break;
                }
            else if (manager_kind == 1)
                switch (change) {
                    case "ALL":
                        mItems_Manager.setAdapter(mAll_ed);
                        break;
                    case "MINE":
                        mItems_Manager.setAdapter(mCreate_ed);
                        break;
                    case "ATTEND":
                        mItems_Manager.setAdapter(mAttend_ed);
                        break;
                }
        }
    }

    public void updateview() {
        if (manager_kind == 0) {
            mAll_ing = new Manager_All_ing();
            mCreate_ing = new Manager_create_Adapting();
            mAttend_ing = new Manager_attend_Adapting();
            mItems_Manager.setAdapter(mAll_ing);
        } else if (manager_kind == 1) {
            mAll_ed = new Manager_All_ed();
            mCreate_ed = new Manager_create_Adapted();
            mAttend_ed = new Manager_attend_Adapted();
            mItems_Manager.setAdapter(mAll_ed);
        }
    }

    /**
     * 以下为holder和adapter
     */
    private class Manager_create_Adapting extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<Items> list;

        public Manager_create_Adapting() {
            list = GetAllItems.getGetAllItems().getList_Items_All_create_ing();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(User_items_Manager.this);
            return new Manager_Holder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            String name = list.get(position).getName();
            String number = list.get(position).getNowamount() + "/" + list.get(position).getMaxamount();
            String time = list.get(position).getCreate_time();
            String kind = list.get(position).getItem_kind();
            Long id = list.get(position).getId();
            ((Manager_Holder) holder).bind(name, number, time, kind, id, position, list.get(position).getCover());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private class Manager_attend_Adapting extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<Items> list;

        public Manager_attend_Adapting() {
            list = GetAllItems.getGetAllItems().getList_Items_All_attend_ing();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(User_items_Manager.this);
            return new Manager_Holder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            String name = list.get(position).getName();
            String number = list.get(position).getNowamount() + "/" + list.get(position).getMaxamount();
            String time = list.get(position).getCreate_time();
            String kind = list.get(position).getItem_kind();
            Long id = list.get(position).getId();
            ((Manager_Holder) holder).bind(name, number, time, kind, id, position, list.get(position).getCover());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private class Manager_create_Adapted extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<Items> list;

        public Manager_create_Adapted() {
            list = GetAllItems.getGetAllItems().getList_Items_All_create_ed();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(User_items_Manager.this);
            return new Manager_Holder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            String name = list.get(position).getName();
            String number = list.get(position).getNowamount() + "/" + list.get(position).getMaxamount();
            String time = list.get(position).getCreate_time();
            String kind = list.get(position).getItem_kind();
            Long id = list.get(position).getId();
            ((Manager_Holder) holder).bind(name, number, time, kind, id, position, list.get(position).getCover());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private class Manager_attend_Adapted extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<Items> list;

        public Manager_attend_Adapted() {
            list = GetAllItems.getGetAllItems().getList_Items_All_attend_ed();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(User_items_Manager.this);
            return new Manager_Holder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            String name = list.get(position).getName();
            String number = list.get(position).getNowamount() + "/" + list.get(position).getMaxamount();
            String time = list.get(position).getCreate_time();
            String kind = list.get(position).getItem_kind();
            Long id = list.get(position).getId();
            ((Manager_Holder) holder).bind(name, number, time, kind, id, position, list.get(position).getCover());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private class Manager_All_ing extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<Items> list;

        public Manager_All_ing() {
            list = GetAllItems.getGetAllItems().getList_Items_All_ing();

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(User_items_Manager.this);
            return new Manager_Holder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            String name = list.get(position).getName();
            String number = list.get(position).getNowamount() + "/" + list.get(position).getMaxamount();
            String time = list.get(position).getCreate_time();
            String kind = list.get(position).getItem_kind();
            Long id = list.get(position).getId();
            ((Manager_Holder) holder).bind(name, number, time, kind, id, position, list.get(position).getCover());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private class Manager_All_ed extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<Items> list;

        public Manager_All_ed() {
            list = GetAllItems.getGetAllItems().getList_Items_All_ed();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(User_items_Manager.this);
            return new Manager_Holder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            String name = list.get(position).getName();
            String number = list.get(position).getNowamount() + "/" + list.get(position).getMaxamount();
            String time = list.get(position).getCreate_time();
            String kind = list.get(position).getItem_kind();
            Long id = list.get(position).getId();
            ((Manager_Holder) holder).bind(name, number, time, kind, id, position, list.get(position).getCover());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private class Manager_Holder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mAccount;
        private TextView mTime;
        private ImageView mKind;
        private Long ID;
        private RelativeLayout relativeLayout;
        private String kind;
        private int position;
        private String cover;

        public Manager_Holder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_manager_list, parent, false));
            mName = (TextView) itemView.findViewById(R.id.manager_name_text);
            mAccount = (TextView) itemView.findViewById(R.id.manager_account_text);
            mTime = (TextView) itemView.findViewById(R.id.manager_settime_text);
            mKind = (ImageView) itemView.findViewById(R.id.manager_kind_image);

            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.manager_list_relativelayout);
            relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("提示:");
                    builder.setIcon(R.drawable.logo);//设置图标
                    builder.setMessage("确定要删除该项目吗？" + '\n' +
                            "删除后该项目内容不再可见");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  //这个是设置确定按钮

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            switch (kind) {
                                case "漂流相机":
                                    Delete_Camera(ID, User_Now.getUserNow().getUser().getToken());
                                    break;
                                case "漂流本":
                                    ;
                                    Delete_Note(ID, User_Now.getUserNow().getUser().getToken());
                                    break;
                                case "漂流小说":
                                    ;
                                    Delete_Novel(ID, User_Now.getUserNow().getUser().getToken());
                                    break;
                                case "漂流画":
                                    ;
                                    Delete_Drawing(ID, User_Now.getUserNow().getUser().getToken());
                                    break;
                                default:
                                    Toast.makeText(v.getContext(), "未确定项目类型", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                            TimerTask timerTask = new TimerTask() {
                                @Override
                                public void run() {
                                    Message message = new Message();
                                    message.what = 200;
                                    mHandler.sendMessage(message);
                                }
                            };
                            new Timer().schedule(timerTask, 300);
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    });
                    builder.show();
                    return true;
                }
            });
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (manager_kind == 0)//项目仍在进行中
                        switch (kind) {
                            case "漂流相机":
                                Intent intent = new Intent(User_items_Manager.this, Camera_Start.class);
                                intent.putExtra("camera_id", ID.intValue());
                                startActivity(intent);
                                break;
                            case "漂流本":
                                File file = new File(getApplication().getFilesDir().getAbsolutePath() + "/DriftingNote/" + ID.toString() + ".txt");
                                Intent intent1 = new Intent(User_items_Manager.this, Book_Writing.class);
                                intent1.putExtra("data", ID.toString());
                                intent1.putExtra("cover", cover);
                                if (file.exists()) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                    builder.setIcon(R.drawable.logo);
                                    builder.setTitle("提示");
                                    builder.setMessage("检测到您有保存的内容\n要继续创作吗?");
                                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            try {
                                                FileInputStream fis = new FileInputStream(file.getAbsolutePath());
                                                byte[] b = new byte[fis.available()];
                                                fis.read(b);
                                                String readStr = new String(b);
                                                intent1.putExtra("contact",readStr);
                                                startActivity(intent1);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            file.delete();
                                            startActivity(intent1);
                                        }
                                    });
                                    builder.show();
                                }else
                                    startActivity(intent1);

                                break;
                            case "漂流小说":
                                Intent intent2 = new Intent(User_items_Manager.this, Detail_novel_underway.class);
                                intent2.putExtra("file_id", ID);
                                launcher.launch(intent2);
                                break;
                            case "漂流画":
                                Intent intent3 = new Intent(User_items_Manager.this, Drawing_Activity.class);
                                intent3.putExtra("file_id", ID);
                                startActivity(intent3);
                                break;
                            default:
                                break;
                        }
                    else//项目已经结束
                        switch (kind) {
                            case "漂流相机":
                                Intent intent = new Intent(User_items_Manager.this, Detail_Activity.class);
                                intent.putExtra("file_id", ID);
                                intent.putExtra("type", "漂流相机");
                                startActivity(intent);
                                break;
                            case "漂流本":
                                Intent intent1 = new Intent(User_items_Manager.this, Detail_Activity.class);
                                intent1.putExtra("file_id", ID);
                                intent1.putExtra("type", "漂流本");
                                startActivity(intent1);
                                break;
                            case "漂流小说":
                                Intent intent2 = new Intent(User_items_Manager.this, Detail_Activity.class);
                                intent2.putExtra("file_id", ID);
                                intent2.putExtra("type", "漂流小说");
                                startActivity(intent2);
                                break;
                            case "漂流画":
                                Intent intent3 = new Intent(User_items_Manager.this, Detail_Activity.class);
                                intent3.putExtra("file_id", ID);
                                intent3.putExtra("type", "漂流画");
                                startActivity(intent3);
                                break;
                            default:
                                break;
                        }
                }
            });
        }

        public void bind(String name, String number, String time, String kind, Long id, int position, String cover) {
            Log.d("manager_bug", name + "....get");
            mName.setText("名称:《" + name + "》");
            mAccount.setText("进度:" + number);
            mTime.setText("创建时间:" + time);
            this.cover = cover;
            switch (kind) {
                case "漂流相机":
                    mKind.setImageResource(R.drawable.camera);
                    break;
                case "漂流本":
                    mKind.setImageResource(R.drawable.book);
                    break;
                case "漂流小说":
                    mKind.setImageResource(R.drawable.novel);
                    break;
                case "漂流画":
                    mKind.setImageResource(R.drawable.drawing);
                    break;
                default:
                    break;
            }
            this.kind = kind;
            ID = id;
            this.position = position;
        }
    }

    private void Delete_Camera(Long id, String token) {
        Camera_return_upload.Delete_upload body = new Camera_return_upload.Delete_upload();
        body.setId(id);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://116.204.121.9:61583/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        Mine_items mine_items = retrofit.create(Mine_items.class);
        Call<Camera_return_upload.Delete_return> call = mine_items.Delete_Camera(body, token);
        call.enqueue(new Callback<Camera_return_upload.Delete_return>() {
            @Override
            public void onResponse(Call<Camera_return_upload.Delete_return> call, Response<Camera_return_upload.Delete_return> response) {
                if (response.isSuccessful())
                    Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                GetAllItems.getGetAllItems().refreshMessage();
            }

            @Override
            public void onFailure(Call<Camera_return_upload.Delete_return> call, Throwable t) {
                Log.d("itmes_bug", t.toString());
            }
        });
    }

    private void Delete_Note(Long id, String token) {
        Camera_return_upload.Delete_upload body = new Camera_return_upload.Delete_upload();
        body.setId(id);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://116.204.121.9:61583/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        Mine_items mine_items = retrofit.create(Mine_items.class);
        Call<Camera_return_upload.Delete_return> call = mine_items.Delete_Note(body, token);
        call.enqueue(new Callback<Camera_return_upload.Delete_return>() {
            @Override
            public void onResponse(Call<Camera_return_upload.Delete_return> call, Response<Camera_return_upload.Delete_return> response) {
                if (response.isSuccessful())
                    Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                GetAllItems.getGetAllItems().refreshMessage();
            }

            @Override
            public void onFailure(Call<Camera_return_upload.Delete_return> call, Throwable t) {
                Log.d("itmes_bug", t.toString());
            }
        });
    }

    private void Delete_Novel(Long id, String token) {
        Camera_return_upload.Delete_upload body = new Camera_return_upload.Delete_upload();
        body.setId(id);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://116.204.121.9:61583/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        Mine_items mine_items = retrofit.create(Mine_items.class);
        Call<Camera_return_upload.Delete_return> call = mine_items.Delete_Novel(body, token);
        call.enqueue(new Callback<Camera_return_upload.Delete_return>() {
            @Override
            public void onResponse(Call<Camera_return_upload.Delete_return> call, Response<Camera_return_upload.Delete_return> response) {
                if (response.isSuccessful())
                    Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                GetAllItems.getGetAllItems().refreshMessage();
            }

            @Override
            public void onFailure(Call<Camera_return_upload.Delete_return> call, Throwable t) {
                Log.d("itmes_bug", t.toString());
            }
        });
    }

    private void Delete_Drawing(Long id, String token) {
        Camera_return_upload.Delete_upload body = new Camera_return_upload.Delete_upload();
        body.setId(id);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://116.204.121.9:61583/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        Mine_items mine_items = retrofit.create(Mine_items.class);
        Call<Camera_return_upload.Delete_return> call = mine_items.Delete_Drawing(body, token);
        call.enqueue(new Callback<Camera_return_upload.Delete_return>() {
            @Override
            public void onResponse(Call<Camera_return_upload.Delete_return> call, Response<Camera_return_upload.Delete_return> response) {
                if (response.isSuccessful())
                    Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();
                GetAllItems.getGetAllItems().refreshMessage();
            }

            @Override
            public void onFailure(Call<Camera_return_upload.Delete_return> call, Throwable t) {
                Log.d("itmes_bug", t.toString());
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 是否触发按键为back键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_OK);
            onBackPressed();
            return true;
        } else {// 如果不是back键正常响应
            return super.onKeyDown(keyCode, event);
        }
    }

    private String cutTime(String s) {
        String year = s.substring(0, 4);
        String mouth = s.substring(5, 7);
        String days = s.substring(8, 10);
        return year + "年" + mouth + "月" + days + "日";
    }
}
