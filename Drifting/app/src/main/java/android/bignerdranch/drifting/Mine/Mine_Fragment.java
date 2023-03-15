package android.bignerdranch.drifting.Mine;

import static android.app.Activity.RESULT_OK;
import static androidx.recyclerview.widget.RecyclerView.Adapter;
import static androidx.recyclerview.widget.RecyclerView.OnClickListener;

import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.User_items_Manager;
import android.bignerdranch.drifting.User.User_;
import android.bignerdranch.drifting.User.User_Now;
import android.bignerdranch.drifting.User.User_connector;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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
        mLayout_ing = (LinearLayout) view.findViewById(R.id.gerenlayout1);
        mLayout_over = (LinearLayout) view.findViewById(R.id.gerenlayout3);


        ActivityResultLauncher<Intent> launcher3 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getData() != null) {
                    // 得到图片的全路径
                    Bitmap bit = result.getData().getParcelableExtra("data");
                    String uri = null;
                    try {
                        uri = FileUtils.saveFile(getContext(), bit, new Date().getTime() + ".png", null);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("mine_bug", "头像保存失败");
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
        ActivityResultLauncher<Intent> launcher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    mNameText.setText(User_Now.getUserNow().getUser().getName());
                    mSignText.setText("个性签名:" + User_Now.getUserNow().getUser().getSignature());
                }
            }
        });
        ActivityResultLauncher<Intent> Refreshmanager = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK)
                    updateUI();
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
        mSignText.setText("个性签名:" + mUser.getSignature());
        mPortrait.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Intent intent = new Intent(getActivity(), User_items_Manager.class);
                intent.putExtra("manager_kind", 0);
                Refreshmanager.launch(intent);
            }
        });
        mLayout_over.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), User_items_Manager.class);
                intent.putExtra("manager_kind", 1);
                Refreshmanager.launch(intent);
            }
        });
        updateUI();
        return view;
    }

    public void updateUI() {
        DoingAdapter = new JinduAdaptering();//将案例组传给Adapter并创建Adapter
        mDoingNow.setAdapter(DoingAdapter);//将RecyclerView与Adapter绑定
        DoneAdapter = new JinduAdapterover();
        mDoneAgo.setAdapter(DoneAdapter);
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
        private List<Items> list;

        public JinduAdaptering() {
            list = GetAllItems.getGetAllItems().getList_Items_All_ing();
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
            return 0;
            //看ViewHolder的创建先后来定顺序。例如CrimeHolder先创建，0就代表CrimeHolder，1代表CrimeHolder2
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            String name = "《" + list.get(position).getName() + "》";
            String number = list.get(position).getNowamount() + "/" + list.get(position).getMaxamount();
            if (holder instanceof JinduHolder)
                ((JinduHolder) holder).bind(name, number);
            else if (holder instanceof JinduHolder2)
                ((JinduHolder2) holder).bind(name, number);
        }

        @Override
        public int getItemCount() {
            return list.size();//RecyclerView通过调用该方法确定一共有多少个Fragment
        }

    }

    private class JinduAdapterover extends Adapter<JinduHolder> {
        private List<Items> list;

        public JinduAdapterover() {
            list = GetAllItems.getGetAllItems().getList_Items_All_ed();
        }

        @NonNull
        @Override
        public JinduHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new JinduHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull JinduHolder holder, int position) {
            String name = "《" + this.list.get(position).getName() + "》";
            String number = list.get(position).getNowamount() + "/" + list.get(position).getMaxamount();
            holder.bind(name, number);
        }

        @Override
        public int getItemCount() {
            Log.d("manager_bug", list.size() + "");
            return list.size();//RecyclerView通过调用该方法确定一共有多少个Fragment
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
}