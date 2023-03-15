package android.bignerdranch.drifting.Main;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.bignerdranch.drifting.Login.Login_LoginActivity0;
import android.bignerdranch.drifting.Main.checkingNew.checking_new;
import android.bignerdranch.drifting.Main.checkingNew.info_return;
import android.bignerdranch.drifting.Novel.Novel_Activity;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.User_Now;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileWriter;
import java.util.Objects;

import okhttp3.internal.http.RetryAndFollowUpInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 设置界面
 */
public class Main_SettingsFragment extends Fragment {
    public ConstraintLayout share;
    public ConstraintLayout information;
    public ConstraintLayout advice;
    public ConstraintLayout upload;
    public ConstraintLayout about;
    public ConstraintLayout quit;
    public TextView informationtext;
    final String FRAGMENT_KEY = "id";


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        share = (ConstraintLayout) view.findViewById(R.id.share_app);
        information = (ConstraintLayout) view.findViewById(R.id.information);
        advice = (ConstraintLayout) view.findViewById(R.id.advice);
        upload = (ConstraintLayout) view.findViewById(R.id.upload);
        about = (ConstraintLayout) view.findViewById(R.id.about);
        quit = (ConstraintLayout) view.findViewById(R.id.quit);
        informationtext = (TextView)view.findViewById(R.id.informationtext);
        if(User_Now.getUserNow().getUser().isIfTongZhi())
        informationtext.setText("通知栏提醒(已开启)");
        else
            informationtext.setText("通知栏提醒(已关闭)");

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("http://116.204.121.9:61583/")
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();
                checking_new checking_new = retrofit.create(checking_new.class);
                Call<info_return> call = checking_new.checking_new(User_Now.getUserNow().getUser().getToken());
                call.enqueue(new Callback<info_return>() {
                    @Override
                    public void onResponse(Call<info_return> call, Response<info_return> response) {
                        info_return info_return = response.body();
                        if(Objects.equals(getVerName(requireContext()), info_return.getData().getVersion())){
                            Toast.makeText(requireContext(), "当前已是最新版本", Toast.LENGTH_SHORT).show();
                        }else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                            builder.setIcon(android.R.drawable.ic_dialog_info);
                            builder.setMessage("检测到有新版本，是否前往浏览器更新？");
                            builder.setPositiveButton("是", (dialog, which) -> {
                                Uri uri = Uri.parse("http://" + info_return.getData().getAddr());
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                intent.setData(uri);
                                startActivity(intent);
                            });
                            builder.setNegativeButton("否", null);
                            builder.show();

                        }
                    }

                    @Override
                    public void onFailure(Call<info_return> call, Throwable t) {

                    }
                });
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Builder builder = new Builder(getContext());
                builder.setIcon(R.drawable.logo);//设置图标
                builder.setTitle("退出");//设置对话框的标题
                builder.setMessage("确定要退出当前账号吗？");//设置对话框的内容
                builder.setPositiveButton("确定", new OnClickListener() {  //这个是设置确定按钮

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(getContext(), "退出成功", Toast.LENGTH_SHORT).show();
                        //退出当前用户
                        File file = new File(getContext().getFilesDir().getAbsolutePath());
                        File drifting = new File(file,"Drifting");
                        File target = new File(drifting,"mytoken.txt");
                        if(target.exists())
                            target.delete();
                        //跳转登录界面，清空Activity栈
                        Intent intent = new Intent(requireContext(), Login_LoginActivity0.class)
                              .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("取消", new OnClickListener() {  //取消按钮

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                builder.show();  //必须show一下才能看到对话框
            }
        });
        advice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Builder builder = new Builder(getContext());
                builder.setIcon(R.drawable.logo);
                builder.setTitle("意见反馈");
                builder.setMessage("请联系QQ:");
                builder.setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Builder builder = new Builder(getContext());
                builder.setIcon(R.drawable.logo);
                builder.setTitle("通知栏提醒");
                if (User_Now.getUserNow().getUser().isIfTongZhi()) {
                    builder.setMessage("关闭通知提醒");
                    builder.setPositiveButton("确定", new OnClickListener() {  //这个是设置确定按钮

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            User_Now.getUserNow().getUser().setIfTongZhi(false);
                            informationtext.setText("通知栏提醒(已关闭)");
                        }
                    });
                    builder.setNegativeButton("取消", new OnClickListener() {  //取消按钮

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    });
                } else {
                    builder.setMessage("开启通知提醒");
                    builder.setPositiveButton("确定", new OnClickListener() {  //这个是设置确定按钮

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            User_Now.getUserNow().getUser().setIfTongZhi(true);
                            informationtext.setText("通知栏提醒(已开启)");
                        }
                    });
                    builder.setNegativeButton("取消", new OnClickListener() {  //取消按钮

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    });
                }
                builder.show();
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Builder builder = new Builder(getContext());
                builder.setIcon(R.drawable.logo);
                builder.setTitle("关于我们");
                builder.setMessage("制作团队:木犀互联网技术团队" + '\n');
                //+"产品设计:张舒涵、叶晓芸"+'\n'+
                // +"技术指导:王子忱、林炜昊、王祎博、张耘硕、陈敬文");

                builder.setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });
        return view;
    }
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }
}