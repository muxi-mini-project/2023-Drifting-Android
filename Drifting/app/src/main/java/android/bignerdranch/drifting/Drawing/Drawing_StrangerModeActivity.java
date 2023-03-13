package android.bignerdranch.drifting.Drawing;

import android.app.AlertDialog;
import android.bignerdranch.drifting.Login.Login_LoginActivity;
import android.bignerdranch.drifting.R;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Drawing_StrangerModeActivity extends AppCompatActivity {
    private ImageView iv_image;
    private String cover_url;
    private String name;
    private String theme;
    private String person_number;
    private long newDrawingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stranger_mode);

        iv_image = (ImageView) findViewById(R.id.iv_image);
        Button choose_cover = (Button) findViewById(R.id.stranger_choose_front_cover);
        Button start = (Button) findViewById(R.id.stranger_start);
        EditText name_text = (EditText) findViewById(R.id.name_text);
        EditText theme_text = (EditText) findViewById(R.id.title_text);
        EditText person_number_text = (EditText) findViewById(R.id.person_number_text);

        cover_url = "mini-project.muxixyz.com/drifting/covers/1.jpg";

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name == null || theme == null || person_number == null || person_number.equals("")) {
                    Toast.makeText(Drawing_StrangerModeActivity.this, "请输入名字，主题和人数", Toast.LENGTH_SHORT).show();
                } else {
                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl("http://116.204.121.9:61583/")
                            .addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit = builder.build();
                    create_message message = new create_message(cover_url, 0, name, Integer.parseInt(person_number), theme);
                    Drawing_Api drawingApi = retrofit.create(Drawing_Api.class);
                    Call<create_return> call = drawingApi.create(Login_LoginActivity.getToken(), message);
                    call.enqueue(new Callback<create_return>() {
                        @Override
                        public void onResponse(Call<create_return> call, Response<create_return> response) {
                            create_return create_return = response.body();
                            if (create_return != null) {
                                if (create_return.getCode() == 200) {
                                    newDrawingId = create_return.getData();
                                    Toast.makeText(Drawing_StrangerModeActivity.this, "创建成功！", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Drawing_StrangerModeActivity.this, Drawing_Activity.class);
                                    intent.putExtra("file_id", newDrawingId);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Toast.makeText(Drawing_StrangerModeActivity.this, "创建失败", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<create_return> call, Throwable t) {
                            Toast.makeText(Drawing_StrangerModeActivity.this, "创建失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        choose_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseCover();
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
                person_number = s.toString();
            }
        });
    }

    private void ChooseCover() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, android.R.style.Theme_Holo_Light_Dialog);
        //    指定下拉列表的显示数据
        final String[] choices = {"封面1", "封面2", "封面3", "封面4", "封面5", "封面6", "封面7", "封面8"};
        //    设置一个下拉的列表选择项
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: {
                        iv_image.setImageResource(R.drawable.cover_1);
                        cover_url = "mini-project.muxixyz.com/drifting/covers/点构图.jpg";
                        break;
                    }
                    case 1: {
                        iv_image.setImageResource(R.drawable.cover_2);
                        cover_url = "mini-project.muxixyz.com/drifting/covers/抓拍，广场上玩滑板的少年.jpg";
                        break;
                    }
                    case 2: {
                        iv_image.setImageResource(R.drawable.cover_3);
                        cover_url = "mini-project.muxixyz.com/drifting/covers/三分线，冷清的广场.jpg";
                        break;
                    }
                    case 3: {
                        iv_image.setImageResource(R.drawable.cover_4);
                        cover_url = "mini-project.muxixyz.com/drifting/covers/三分线构图.jpg";
                        break;
                    }
                    case 4: {
                        iv_image.setImageResource(R.drawable.cover_5);
                        cover_url = "mini-project.muxixyz.com/drifting/covers/4.jpg";
                        break;
                    }
                    case 5: {
                        iv_image.setImageResource(R.drawable.cover_6);
                        cover_url = "mini-project.muxixyz.com/drifting/covers/3.jpg";
                        break;
                    }
                    case 6: {
                        iv_image.setImageResource(R.drawable.cover_7);
                        cover_url = "mini-project.muxixyz.com/drifting/covers/2.jpg";
                        break;
                    }
                    case 7: {
                        iv_image.setImageResource(R.drawable.cover_8);
                        cover_url = "mini-project.muxixyz.com/drifting/covers/1.jpg";
                        break;
                    }
                }
            }
        });
        builder.show();
    }
}