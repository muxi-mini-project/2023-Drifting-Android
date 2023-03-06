package android.bignerdranch.drifting.Drawing;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.bignerdranch.drifting.Book.Book_AcquaintanceModeActivity;
import android.bignerdranch.drifting.Book.Book_Writing;
import android.bignerdranch.drifting.Camera.Camera_Activity;
import android.bignerdranch.drifting.Camera.Camera_Start;
import android.bignerdranch.drifting.Login.Login_LoginActivity;
import android.bignerdranch.drifting.R;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Drawing_AcquaintanceModeActivity extends AppCompatActivity {
    private ImageView iv_image;
    private String cover_url;
    private final int max_number = 9;
    private String name;
    private String theme;
    private int person_number = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acquaintance_mode);

        iv_image = (ImageView) findViewById(R.id.iv_image);
        Button choose_cover = (Button) findViewById(R.id.acquaintance_choose_front_cover);
        Button start = (Button) findViewById(R.id.acquaintance_start);
        Button inviting_friends = (Button) findViewById(R.id.acquaintance_inviting_friends);
        EditText name_text = (EditText) findViewById(R.id.name_text);
        EditText theme_text = (EditText) findViewById(R.id.title_text);
        EditText person_number_text = (EditText) findViewById(R.id.person_number_text);

        cover_url = "mini-project.muxixyz.com/drifting/covers/1.jpg";

        inviting_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Drawing_AcquaintanceModeActivity.this, "正在开发中", Toast.LENGTH_SHORT).show();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name == null || theme == null || person_number == 0){
                    Toast.makeText(Drawing_AcquaintanceModeActivity.this, "请输入名字，主题和人数", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(person_number <= max_number){
                        Retrofit.Builder builder = new Retrofit.Builder()
                                .baseUrl("http://116.204.121.9:61583/")
                                .addConverterFactory(GsonConverterFactory.create());
                        Retrofit retrofit = builder.build();
                        create_message message = new create_message(cover_url,1,name,person_number,theme);
                        Api api = retrofit.create(Api.class);
                        Call<create_return> call = api.create(Login_LoginActivity.getToken(),message);
                        call.enqueue(new Callback<create_return>() {
                            @Override
                            public void onResponse(Call<create_return> call, Response<create_return> response) {
                                create_return create_return = response.body();
                                if(create_return != null){
                                    if(create_return.getCode() == 200){
                                        Toast.makeText(Drawing_AcquaintanceModeActivity.this, "创建成功！", Toast.LENGTH_SHORT).show();
                                        Log.i("onResponse",create_return.toString());
                                        startActivity(new Intent(Drawing_AcquaintanceModeActivity.this, Camera_Activity.class));
                                    }
                                }else{
                                    Toast.makeText(Drawing_AcquaintanceModeActivity.this, "创建失败", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<create_return> call, Throwable t) {
                                Toast.makeText(Drawing_AcquaintanceModeActivity.this, "创建失败", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    else {
                        Toast.makeText(Drawing_AcquaintanceModeActivity.this, "请输入正确的人数", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        choose_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowChoices();
            }
        });

        name_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name = s.toString();
            }
        });
        theme_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                theme = s.toString();
            }
        });
        person_number_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                person_number = Integer.parseInt(s.toString());
            }
        });
    }
    private boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                iv_image.setImageURI(uri);
            }
        }
        if (requestCode == 1) {
            Bundle bundle = data.getExtras();
            iv_image.setImageBitmap((Bitmap) bundle.get("data"));

        }

    }

    private void ShowChoices() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Holo_Light_Dialog);
        //    指定下拉列表的显示数据
        final String[] choices = {"本地相册", "漂流相册", "拍摄","使用默认封面"};
        //    设置一个下拉的列表选择项
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: {
                        Intent intent = new Intent(Intent.ACTION_PICK, null);
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                        startActivityForResult(intent, 2);
                    }
                    case 1: {
                        break;
                    }
                    case 2: {
                        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 1);
                        break;
                    }
                    case 3:{

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext(), android.R.style.Theme_Holo_Light_Dialog);
                        final String[] choose_cover = {"背景1","背景2","背景3","背景4","背景5","背景6","背景7","背景8"};
                        builder.setItems(choose_cover, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:{
                                        cover_url = "mini-project.muxixyz.com/drifting/covers/点构图.jpg";
                                        break;
                                    }
                                    case 1:{
                                        cover_url = "mini-project.muxixyz.com/drifting/covers/抓拍，广场上玩滑板的少年.jpg";
                                        break;
                                    }
                                    case 2:{
                                        cover_url = "mini-project.muxixyz.com/drifting/covers/三分线，冷清的广场.jpg";
                                        break;
                                    }
                                    case 3:{
                                        cover_url = "mini-project.muxixyz.com/drifting/covers/三分线构图.jpg";
                                        break;
                                    }
                                    case 4:{
                                        cover_url = "mini-project.muxixyz.com/drifting/covers/4.jpg";
                                        break;
                                    }
                                    case 5:{
                                        cover_url = "mini-project.muxixyz.com/drifting/covers/3.jpg";
                                        break;
                                    }
                                    case 6:{
                                        cover_url = "mini-project.muxixyz.com/drifting/covers/2.jpg";
                                        break;
                                    }
                                    case 7:{
                                        cover_url = "mini-project.muxixyz.com/drifting/covers/1.jpg";
                                        break;
                                    }
                                }
                            }
                        });
                        //iv_image.setImageResource(R.drawable.yaoqing_background);
                        break;
                    }
                }
            }
        });
        builder.show();
    }
}