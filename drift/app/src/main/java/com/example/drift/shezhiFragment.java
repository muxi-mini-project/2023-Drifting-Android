package com.example.drift;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.UUID;

/**
 * 设置界面
 */
public class shezhiFragment extends Fragment {
    Button mFenxiangText;
    Button mTongzhiText;
    Button mYijianText;
    Button mGengxinText;
    Button mGuanyuText;
    Button mTuichuButton;
    final String GET_USER = "user_id";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shezhi, container, false);
        mFenxiangText = (Button) view.findViewById(R.id.fenxiang_text);
        mTongzhiText = (Button) view.findViewById(R.id.tongzhi_text);
        mYijianText = (Button) view.findViewById(R.id.yijian_text);
        mGengxinText = (Button) view.findViewById(R.id.gengxin_text);
        mGuanyuText = (Button) view.findViewById(R.id.guanyu_text);
        mTuichuButton = (Button) view.findViewById(R.id.tuichu_button);
        UUID mUUID = (UUID) getArguments().getSerializable(GET_USER);
        User user = UserLab.get(getContext()).getUser(mUUID);
        mTuichuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new Builder(getContext());
                builder.setIcon(android.R.drawable.ic_dialog_info);//设置图标
                builder.setTitle("退出");//设置对话框的标题
                builder.setMessage("确定要退出当前账号吗？");//设置对话框的内容
                builder.setPositiveButton("确定", new OnClickListener() {  //这个是设置确定按钮

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(getContext(), "退出成功", Toast.LENGTH_SHORT).show();
                        //退出当前用户
                        UserNow.getUserNow().setUser(null);
                        //跳转登录界面，清空Activity栈
                        //Intent intent = new Intent(getActivity().getClass(),login_Activity.class)
                        //      .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        //startActivity(intent);
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
        mYijianText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new Builder(getContext());
                builder.setIcon(android.R.drawable.ic_dialog_info);
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
        mTongzhiText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new Builder(getContext());
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setTitle("通知栏提醒");
                if (user.isIfTongZhi()) {
                    builder.setMessage("你已开启通知" + '\n' + "要关闭它吗?");
                    builder.setPositiveButton("确定", new OnClickListener() {  //这个是设置确定按钮

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Toast.makeText(getContext(), "已关闭通知提醒", Toast.LENGTH_SHORT).show();
                            user.setIfTongZhi(false);
                        }
                    });
                    builder.setNegativeButton("取消", new OnClickListener() {  //取消按钮

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    });
                } else {
                    builder.setMessage("要开启通知提醒吗?");
                    builder.setPositiveButton("确定", new OnClickListener() {  //这个是设置确定按钮

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Toast.makeText(getContext(), "已开启通知提醒", Toast.LENGTH_SHORT).show();
                            user.setIfTongZhi(true);
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
        mGuanyuText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new Builder(getContext());
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setTitle("关于我们");
                //builder.setMessage("制作团队:木犀互联网技术团队" + '\n');
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