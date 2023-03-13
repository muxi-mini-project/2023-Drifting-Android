package android.bignerdranch.drifting.Novel;

import android.app.AlertDialog;
import android.bignerdranch.drifting.Login.Login_LoginActivity;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.User_Now;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Novel_Activity extends AppCompatActivity {
    private ImageButton back;
    private Button save;
    private Button upload;
    private EditText editor;
    private TextView words_num;
    private long newNovelId;
    private String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novel_activity);

        back = (ImageButton) findViewById(R.id.back);
        save = (Button) findViewById(R.id.save);
        upload = (Button) findViewById(R.id.upload);
        editor = (EditText) findViewById(R.id.editor);
        words_num = (TextView) findViewById(R.id.words_num);

        newNovelId = getIntent().getLongExtra("file_id", 0);

        editor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                text = s.toString();
                words_num.setText("字数统计：" + text.length() + "/2000");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        back.setOnClickListener(v -> {
            if (text.equals("")) {
                finish();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(Novel_Activity.this);
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setMessage("你还没有保存编辑的内容，是否保存并退出？");
                builder.setPositiveButton("是", (dialog, which) -> {
                    String path = getExternalFilesDir("DriftingNovel").toString();
                    Log.e("------path", path);
                    File files = new File(path);
                    if (!files.exists()) {
                        files.mkdirs();
                    }
                    try {
                        FileWriter fw = new FileWriter(path + File.separator + newNovelId + ".txt");
                        fw.write(text);
                        fw.close();
                        Toast.makeText(Novel_Activity.this, "文件写入成功", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                builder.setNegativeButton("否", null);
                builder.show();
            }
        });

        save.setOnClickListener(v -> {
            if (text.equals("")) {
                Toast.makeText(Novel_Activity.this, "请输入您的创作", Toast.LENGTH_SHORT).show();
            } else {
                String path = getExternalFilesDir("DriftingNovel").toString();
                Log.e("------path", path);
                File files = new File(path);
                if (!files.exists()) {
                    files.mkdirs();
                }
                try {
                    FileWriter fw = new FileWriter(path + File.separator + newNovelId + ".txt");
                    fw.write(text);
                    fw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            }

        });

        upload.setOnClickListener(v -> {
            if (Objects.equals(text, "")) {
                Toast.makeText(Novel_Activity.this, "请输入您的创作", Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(Novel_Activity.this);
                builder.setIcon(android.R.drawable.ic_dialog_info);
                builder.setMessage("确认上传？");
                builder.setPositiveButton("是", (dialog, which) -> {
                    Retrofit.Builder builder0 = new Retrofit.Builder()
                            .baseUrl("http://116.204.121.9:61583/")
                            .addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit = builder0.build();
                    Novel_Api novel_api = retrofit.create(Novel_Api.class);
                    Call<create_return> call = novel_api.upload(Login_LoginActivity.getToken(), new Novel_writingBody(newNovelId, text, User_Now.getUserNow().getUser().getId()));
                    call.enqueue(new Callback<create_return>() {
                        @Override
                        public void onResponse(Call<create_return> call, Response<create_return> response) {
                            create_return create_return = response.body();
                            if (create_return.getCode() == 200) {
                                Toast.makeText(Novel_Activity.this, "上传成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(Novel_Activity.this, "上传出现问题，请稍后重试", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<create_return> call, Throwable t) {
                            Toast.makeText(Novel_Activity.this, "上传出现问题，请稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
                builder.setNegativeButton("否", null);
                builder.show();
            }
        });
    }
}