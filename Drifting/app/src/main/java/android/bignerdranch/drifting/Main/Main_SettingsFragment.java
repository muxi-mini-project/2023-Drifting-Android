package android.bignerdranch.drifting.Main;

import android.app.AlertDialog.Builder;
import android.bignerdranch.drifting.Login.Login_LoginActivity0;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.Mine.User.User_Now;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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
                        File file =new File(getContext().getFilesDir().getAbsolutePath());
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
}