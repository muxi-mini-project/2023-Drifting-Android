package android.bignerdranch.drifting.Mine;

import static android.app.Activity.RESULT_OK;
import static android.bignerdranch.drifting.Mine.GetAllItems.mCamera_connector;
import static androidx.recyclerview.widget.RecyclerView.Adapter;
import static androidx.recyclerview.widget.RecyclerView.OnClickListener;

import android.annotation.SuppressLint;
import android.bignerdranch.drifting.Camera.Camera_;
import android.bignerdranch.drifting.Camera.Camera_return_upload;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.Mine.User.USer_items_Manager;
import android.bignerdranch.drifting.Mine.User.User_;
import android.bignerdranch.drifting.Mine.User.User_Now;
import android.bignerdranch.drifting.Mine.User.User_connector;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 个人界面
 */
public class Mine_Fragment extends Fragment {
    //正在进行以及已经结束面板
    RecyclerView mDoingNow;
    Adapter DoingAdapter = null;
    RecyclerView mDoneAgo;
    Adapter DoneAdapter = null;

    Button mZiliaoButton;
    TextView mSignText;
    TextView mSexText;
    TextView mNameText;
    ImageView mPortrait;
    User_ mUser;
    LinearLayout mLayout_ing;
    LinearLayout mLayout_over;
    static List<Items> List_ItemsAll = new ArrayList<>();
    Handler mHandler = new Handler(){
      @SuppressLint("HandlerLeak")
      @Override
      public void handleMessage(Message msg){
          if(msg.what == 200){
              List<String> Namesing = new ArrayList<>();
              List<Long> Nowusering = new ArrayList<>();
              List<Long> Maxusering = new ArrayList<>();
              for(int i = 0; i< List_ItemsAll.size(); i++){
                  Namesing.add(List_ItemsAll.get(i).getName());
                  Nowusering.add(List_ItemsAll.get(i).getNowamount());
                  Maxusering.add(List_ItemsAll.get(i).getMaxamount());
              }
              updateUI(Namesing,Nowusering,Maxusering);
          }
      }
    };

    public class User_returnformAvatar {
        private Object message;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mUser = User_Now.getUserNow().getUser();
        View view = inflater.inflate(R.layout.mine_fragment, container, false);

        mZiliaoButton = (Button) view.findViewById(R.id.ziliao_button);
        mNameText = (TextView) view.findViewById(R.id.name_text);
        mSexText = (TextView) view.findViewById(R.id.sex_text);
        mSignText = (TextView) view.findViewById(R.id.sign_text);
        mPortrait = (ImageView) view.findViewById(R.id.portrait);
        mDoingNow = (RecyclerView) view.findViewById(R.id.gerenjinduing_view);
        mDoingNow.setLayoutManager(new LinearLayoutManager(getContext()));
        mDoneAgo = (RecyclerView) view.findViewById(R.id.gerenjinduover_view);
        mDoneAgo.setLayoutManager(new LinearLayoutManager(getContext()));
        mLayout_ing = (LinearLayout)view.findViewById(R.id.gerenlayout1);
        mLayout_over = (LinearLayout)view.findViewById(R.id.gerenlayout3);
        ActivityResultLauncher<Intent> launcher3 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getData() != null) {
                    // 得到图片的全路径
                    Bitmap bit = result.getData().getParcelableExtra("data");
                    String uri = null;
                    try {
                        uri = FileUtils.saveFile(getContext(),bit, new Date().getTime() + ".png", null);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("mine_bug","头像保存失败");
                    }
                    if (result.getResultCode() == RESULT_OK) {
                        mPortrait.setImageBitmap(bit);
                        mUser.setPortrait(uri);
                        User_Now.getUserNow().getUser().setavatar(bit);
                        putavatar(User_Now.getUserNow().getUser().getPortrait(), mUser.getToken());
                    }
                }
            }
        });
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    // 从相册返回的数据
                    if (result.getData() != null) {

                        Uri uri1 = result.getData().getData();
                        Intent intent = new Intent("com.android.camera.action.CROP");
                        intent.setDataAndType(uri1, "image/*");
                        intent.putExtra("crop", "true");
                        intent.putExtra("aspectX", 1);
                        intent.putExtra("aspectY", 1);
                        intent.putExtra("outputX", 150);
                        intent.putExtra("outputY", 150);
                        intent.putExtra("return-data", true);
                        launcher3.launch(intent);

                    }
                }
            }
        });
//        ActivityResultLauncher<Intent> launcher1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                    isRefuse = !Environment.isExternalStorageManager();
//                }
//            }
//        });
        ActivityResultLauncher<Intent> launcher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    mNameText.setText(User_Now.getUserNow().getUser().getName());
                    mSignText.setText("个性签名:"+User_Now.getUserNow().getUser().getSignature());
                }
            }
        });

        if (User_Now.getUserNow().getUser().getavatar() == null) {
            try {
                mPortrait.setImageBitmap(FileUtils.getImage(User_Now.getUserNow().getUser().getPortrait(), FileUtils.AVATAR));
                User_Now.getUserNow().getUser().setavatar(FileUtils.getImage(User_Now.getUserNow().getUser().getPortrait(), FileUtils.AVATAR));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            mPortrait.setImageBitmap(User_Now.getUserNow().getUser().getavatar());
        }
        mNameText.setText(mUser.getName());
        mSexText.setText("性别:" + mUser.getSex());
        mSignText.setText( "个性签名:"+mUser.getSignature());
        mPortrait.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !isRefuse) {// android 11  且 不是已经被拒绝
//                    // 先判断有没有权限
//                    if (!Environment.isExternalStorageManager()) {
//                        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
//                        intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
//                        launcher1.launch(intent);
//                    } else
//                        Havpower = true;
//                }
//                if (Havpower) {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    launcher.launch(intent);
               // }
            }
        });
        mZiliaoButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Mine_BianJiActivity.class);
                launcher2.launch(intent);
            }
        });
        mLayout_ing.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getActivity(), USer_items_Manager.class));
            }
        });
        mLayout_over.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), USer_items_Manager.class));
            }
        });
        GetCamera_ownercrea();
        return view;
    }

    public void updateUI(List<String> Namesing,List<Long> Nowusering,List<Long> Maxusering) {
        DoingAdapter = new JinduAdaptering(Namesing,Nowusering,Maxusering);//将案例组传给Adapter并创建Adapter
        mDoingNow.setAdapter(DoingAdapter);//将RecyclerView与Adapter绑定
//            if (DoneAdapter == null) {
//                DoneAdapter = new JinduAdapterover();
//                mDoneAgo.setAdapter(DoneAdapter);
    }
    /**
     * 上传图片
     */
    private void putavatar(String uri, String token) {
        File file = new File(uri);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://116.204.121.9:61583/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        User_connector user_connector = retrofit.create(User_connector.class);
        RequestBody requestFile =
                RequestBody.create(file, MediaType.parse("multipart/form-data"));
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
        Call<User_returnformAvatar> call = user_connector.putUseravatar(body, token);
        call.enqueue(new Callback<User_returnformAvatar>() {
            @Override
            public void onResponse(Call<User_returnformAvatar> call, Response<User_returnformAvatar> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
                    File file1 = new File(uri);
                    User_Now.getUserNow().getUser().setPortrait("http://mini-project.muxixyz.com/drifting/user_avatar/" + file1.getName());
                    file1.delete();
                } else
                    Toast.makeText(getContext(), "保存失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User_returnformAvatar> call, Throwable t) {
                Toast.makeText(getContext(), "错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 以下为Adapter和Holder
     */
    private class JinduAdaptering extends Adapter<RecyclerView.ViewHolder> {
        private List<String> namesing;
        private List<Long> nowusering;
        private List<Long> maxusering;

        //    private final List<Boolean> Ifusering = GetAllItems.get(mUser.getUUID(), getContext()).getAllIfuserunderway();
        public JinduAdaptering(List<String> Namesing,List<Long> Nowusering,List<Long> Maxusering) {
            namesing = Namesing;
            nowusering = Nowusering;
            maxusering = Maxusering;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            if (viewType == 0)
                return new JinduHolder(layoutInflater, parent);
            else
                return new JinduHolder2(layoutInflater, parent);
        }

        @Override
        public int getItemViewType(int position) {
            // return Ifusering.get(position) ? 1 : 0;
            return 1;
            //看ViewHolder的创建先后来定顺序。例如CrimeHolder先创建，0就代表CrimeHolder，1代表CrimeHolder2
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            String name = "《" + namesing.get(position) + "》";
            String number = nowusering.get(position) + "/" + maxusering.get(position);
            if (holder instanceof JinduHolder)
                ((JinduHolder) holder).bind(name, number);
            else if (holder instanceof JinduHolder2)
                ((JinduHolder2) holder).bind(name, number);
        }

        @Override
        public int getItemCount() {
            return namesing.size();//RecyclerView通过调用该方法确定一共有多少个Fragment
        }

    }
        private class JinduAdapterover extends Adapter<JinduHolder> {
        private final List<String> name = new ArrayList<>();
        private final List<Integer> Maxuserover = new ArrayList<>();

        public JinduAdapterover() {
        }

        @NonNull
        @Override
        public JinduHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new JinduHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull JinduHolder holder, int position) {
            String name = "《" + this.name.get(position) + "》";
            String number = Maxuserover.get(position) + "/" + Maxuserover.get(position);
            holder.bind(name, number);
        }

        @Override
        public int getItemCount() {
            return name.size();//RecyclerView通过调用该方法确定一共有多少个Fragment
        }
    }
    private static class JinduHolder extends RecyclerView.ViewHolder {
        private TextView mJinduname;
        private TextView mJindurenshu;

        public JinduHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.mine_jindu1, parent, false));
            mJinduname = (TextView) itemView.findViewById(R.id.jinduname_text);
            mJindurenshu = (TextView) itemView.findViewById(R.id.jindurenshu_text);
        }

        public void bind(String name, String number) {
            mJinduname.setText(name);
            mJindurenshu.setText(number);
        }
    }
    private class JinduHolder2 extends RecyclerView.ViewHolder {
        private TextView mJinduname;
        private TextView mJindurenshu;

        public JinduHolder2(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.mine_jindu2, parent, false));
            mJinduname = (TextView) itemView.findViewById(R.id.jinduname_text);
            mJindurenshu = (TextView) itemView.findViewById(R.id.jindurenshu_text);

        }

        public void bind(String name, String number) {
            mJinduname.setText(name);
            mJindurenshu.setText(number);
        }
    }

    /**
     * 获取自己创建的漂流相机
     * @return
     */
    public void GetCamera_ownercrea() {
        if (List_ItemsAll.isEmpty()) {
            List<Items> camera_list = new ArrayList<>();
            String token = User_Now.getUserNow().getUser().getToken();
            Call<Camera_return_upload.Camera_return_usermessage> call = mCamera_connector.GetCamera_mes_user(token);
            call.enqueue(new Callback<Camera_return_upload.Camera_return_usermessage>() {
                @Override
                public void onResponse(Call<Camera_return_upload.Camera_return_usermessage> call, Response<Camera_return_upload.Camera_return_usermessage> response) {
                    Camera_return_upload.Camera_return_usermessage message = response.body();
                    List<Camera_return_upload.Camera_return_usermessage2> list = response.body().getData();
                    for (int i = 0; i < list.size(); i++) {
                        Items item = new Items(list.get(i).getName(),
                                list.get(i).getTheme(),
                                list.get(i).getCover(),
                                "漂流相机",
                                list.get(i).getKind(),
                                list.get(i).getWriter_number(),
                                list.get(i).getNumber());
                        camera_list.add(item);
                    }
                    List_ItemsAll = camera_list;
                    Log.d("Mine_bug","漂流相机获取成功");
                    Message message1 = new Message();
                    message1.what = 200;
                    mHandler.sendMessage(message1);
                }

                @Override
                public void onFailure(Call<Camera_return_upload.Camera_return_usermessage> call, Throwable t) {
                }
            });
        } else {
            Message message1 = new Message();
            message1.what = 200;
            mHandler.sendMessage(message1);
            Log.d("Mine_bug","漂流相机获取成功");
        }
    }
}