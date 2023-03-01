package android.bignerdranch.drifting.Mine;

import static android.app.Activity.RESULT_OK;
import static androidx.recyclerview.widget.RecyclerView.Adapter;
import static androidx.recyclerview.widget.RecyclerView.OnClickListener;

import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.User_;
import android.bignerdranch.drifting.User.User_Now;
import android.bignerdranch.drifting.User.User_connector;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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
    Adapter DoingAdapter;
    RecyclerView mDoneAgo;
    Adapter DoneAdapter;

    Button mZiliaoButton;
    TextView mSignText;
    TextView mSexText;
    TextView mNameText;
    ImageView mPortrait;
    User_ mUser;
    boolean isRefuse = false;
    boolean Havpower = false;

    public class User_returnformAvatar{
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
        mDoingNow.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDoneAgo = (RecyclerView) view.findViewById(R.id.gerenjinduover_view);
        mDoneAgo.setLayoutManager(new LinearLayoutManager(getActivity()));
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    // 从相册返回的数据
                    if (result.getData() != null) {
                        // 得到图片的全路径
                        Bitmap bit = SaveFile.getPhotoBitmap(getContext(), result.getData());
                        String uri = null;
                        try {
                            uri = SaveFile.saveFile(getContext(), bit, "myicon.JPG", "icon");
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "保存失败", Toast.LENGTH_SHORT).show();
                        }
                        mPortrait.setImageBitmap(bit);
                        StringBuffer stringBuffer = new StringBuffer(uri);
                        stringBuffer.insert(4,"s");
                        mUser.setPortrait(stringBuffer.toString());
                        putavatar(uri, mUser.getToken());
                    }
                }
            }
        });
        ActivityResultLauncher<Intent> launcher1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    // 检查是否有权限
                    // 授权成功
                    // 授权失败
                    isRefuse = !Environment.isExternalStorageManager();
                }
            }
        });
        ActivityResultLauncher<Intent> launcher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    mNameText.setText(mUser.getName());
                    mSignText.setText("个性签名:" + mUser.getSignature());
                }
            }
        });
//        if (mUser.getPortrait() != null) {
//            Bitmap bitmap = SaveFile.getDiskBitmap(mUser.getPortrait());
//            mPortrait.setImageBitmap(bitmap);
//        }
        try {
            mPortrait.setImageBitmap(getImage(mUser.getPortrait()));
        }catch (Exception e){
            e.printStackTrace();
        }
        mNameText.setText(mUser.getName());
        mSexText.setText("性别:" + mUser.getSex());
        mSignText.setText(mSignText.getText() + mUser.getSignature());
        mPortrait.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !isRefuse) {// android 11  且 不是已经被拒绝
                    // 先判断有没有权限
                    if (!Environment.isExternalStorageManager()) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                        intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
                        launcher1.launch(intent);
                    } else
                        Havpower = true;
                }
                if (Havpower) {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    launcher.launch(intent);
                }
            }
        });
        mZiliaoButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "正在施工", Toast.LENGTH_SHORT).show();
                //Intent intent = new Intent(getActivity(),Mine_BianJiFragment.class);
                //launcher2.launch(intent);
            }
        });
        //     updateUI();
        return view;
    }

    //  public void updateUI() {
//        if (DoingAdapter == null) {
//            DoingAdapter = new JinduAdaptering();//将案例组传给Adapter并创建Adapter
//            mDoingNow.setAdapter(DoingAdapter);//将RecyclerView与Adapter绑定
//            if (DoneAdapter == null) {
//                DoneAdapter = new JinduAdapterover();
//                mDoneAgo.setAdapter(DoneAdapter);
    //         }
    //    }
    // }

    private void putavatar(String uri, String token) {
        File file = new File(uri);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://116.204.121.9:61583/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        User_connector user_connector = retrofit.create(User_connector.class);
        RequestBody requestFile =
                RequestBody.create(file,MediaType.parse("multipart/form-data"));
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("avatar", file.getName(), requestFile);
        Call<User_returnformAvatar> call = user_connector.putUseravatar(body, token);
        call.enqueue(new Callback<User_returnformAvatar>() {
            @Override
            public void onResponse(Call<User_returnformAvatar> call, Response<User_returnformAvatar> response) {
                if(response.isSuccessful())
                Toast.makeText(getContext(), "保存成功", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getContext(), "保存失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User_returnformAvatar> call, Throwable t) {
                Toast.makeText(getContext(), "错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static Bitmap getImage(String path) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if(conn. getResponseCode() == 200){
            InputStream inStream = conn. getInputStream();
            Bitmap bitmap = BitmapFactory. decodeStream(inStream) ;
            return bitmap;
        }
        return null;
    }
    /**
     * 以下为Adapter和Holder
     */
//    private class JinduAdaptering extends Adapter<ViewHolder> {
//        private final List<String> namesing = GetAllItems.get(mUser.getUUID(), getContext()).getAllNameunderway();
//        private final List<Integer> nowusering = GetAllItems.get(mUser.getUUID(), getContext()).getAllnowuserunderway();
//        private final List<Integer> Maxusering = GetAllItems.get(mUser.getUUID(), getContext()).getAllMaxuserunderway();
//        private final List<Boolean> Ifusering = GetAllItems.get(mUser.getUUID(), getContext()).getAllIfuserunderway();
//        public JinduAdaptering() {
//        }
//
//        @NonNull
//        @Override
//        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
//            if (viewType == 0)
//                return new JinduHolder(layoutInflater, parent);
//            else
//                return new JinduHolder2(layoutInflater, parent);
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//            return Ifusering.get(position) ? 1 : 0;
//            //看ViewHolder的创建先后来定顺序。例如CrimeHolder先创建，0就代表CrimeHolder，1代表CrimeHolder2
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//            String name = "《" + namesing.get(position) + "》";
//            String number = nowusering.get(position) + "/" + Maxusering.get(position);
//            if (holder instanceof JinduHolder)
//                ((JinduHolder) holder).bind(name, number);
//            else if (holder instanceof JinduHolder2)
//                ((JinduHolder2) holder).bind(name, number);
//        }
//
//        @Override
//        public int getItemCount() {
//            return namesing.size();//RecyclerView通过调用该方法确定一共有多少个Fragment
//        }
//
//    }
//
//    private class JinduAdapterover extends Adapter<JinduHolder> {
//        private final List<String> namesover = GetAllItems.get(mUser.getUUID(), getContext()).getAllNameover();
//        private final List<Integer> nowuserover = GetAllItems.get(mUser.getUUID(), getContext()).getAllnowuserover();
//        private final List<Integer> Maxuserover = GetAllItems.get(mUser.getUUID(), getContext()).getAllMaxuserover();
//
//        public JinduAdapterover() {
//        }
//
//        @NonNull
//        @Override
//        public JinduHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
//            return new JinduHolder(layoutInflater, parent);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull JinduHolder holder, int position) {
//            String name = "《" + namesover.get(position) + "》";
//            String number = nowuserover.get(position) + "/" + Maxuserover.get(position);
//            holder.bind(name, number);
//        }
//
//        @Override
//        public int getItemCount() {
//            return namesover.size();//RecyclerView通过调用该方法确定一共有多少个Fragment
//        }
//    }
//    private static class JinduHolder extends ViewHolder {
//        private TextView mJinduname;
//        private TextView mJindurenshu;
//
//        public JinduHolder(LayoutInflater inflater, ViewGroup parent) {
//            super(inflater.inflate(R.layout.mine_jindu1, parent, false));
//            mJinduname = (TextView) itemView.findViewById(R.id.jinduname_text);
//            mJindurenshu = (TextView) itemView.findViewById(R.id.jindurenshu_text);
//        }
//
//        public void bind(String name, String number) {
//            mJinduname.setText(name);
//            mJindurenshu.setText(number);
//        }
//    }
//
//    private class JinduHolder2 extends ViewHolder {
//        private TextView mJinduname;
//        private TextView mJindurenshu;
//
//        public JinduHolder2(LayoutInflater inflater, ViewGroup parent) {
//            super(inflater.inflate(R.layout.mine_jindu2, parent, false));
//            mJinduname = (TextView) itemView.findViewById(R.id.jinduname_text);
//            mJindurenshu = (TextView) itemView.findViewById(R.id.jindurenshu_text);
//
//        }
//
//        public void bind(String name, String number) {
//            mJinduname.setText(name);
//            mJindurenshu.setText(number);
//        }
//    }

}