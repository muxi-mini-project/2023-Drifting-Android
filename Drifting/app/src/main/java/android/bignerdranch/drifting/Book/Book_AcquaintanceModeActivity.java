package android.bignerdranch.drifting.Book;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.bignerdranch.drifting.Book.ReturnAndReauest.Book_Participate_request;
import android.bignerdranch.drifting.Book.ReturnAndReauest.Book_create_request;
import android.bignerdranch.drifting.Book.ReturnAndReauest.Book_create_return;
import android.bignerdranch.drifting.Inviting.Inviting_Friends;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.User_Now;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Book_AcquaintanceModeActivity extends AppCompatActivity {
    private ImageView iv_image;
    private final int max_number = 9;
    private String name;
    private String title;
    private String person_number;
    private int number;
    private Button choose_cover;
    private String mCoverURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drifting_acquaintance_mode);

        iv_image = (ImageView) findViewById(R.id.iv_image);
        choose_cover = findViewById(R.id.acquaintance_choose_front_cover);
        Button start = (Button) findViewById(R.id.acquaintance_start);
        Button inviting_friends = (Button) findViewById(R.id.acquaintance_inviting_friends);
        EditText name_text = (EditText) findViewById(R.id.name_text);
        EditText title_text = (EditText) findViewById(R.id.title_text);
        EditText person_number_text = (EditText) findViewById(R.id.person_number_text);

        inviting_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Book_AcquaintanceModeActivity.this, Inviting_Friends.class));
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name == null || title == null || person_number == null){
                    Toast.makeText(Book_AcquaintanceModeActivity.this, "请输入名字，主题和人数", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(isNumeric(person_number)) {
                        number = Integer.parseInt(person_number);
                        if(number <= max_number){
                            Book_create_request book_create_request = new Book_create_request();
                            book_create_request.setName(name);
                            book_create_request.setKind(1);
                            book_create_request.setNumber(Integer.valueOf(person_number));
                            book_create_request.setTheme(title);
                            book_create_request.setCover(mCoverURL);

                            Retrofit.Builder builder = new Retrofit.Builder()
                                    .baseUrl("http://116.204.121.9:61583/")
                                            .addConverterFactory(GsonConverterFactory.create());
                            Retrofit retrofit = builder.build();
                            ApiNote bookcreate = retrofit.create(ApiNote.class);
                            Call<Book_create_return> call = bookcreate.bookCreate(book_create_request
                                    ,User_Now.getUserNow().getUser().getToken());
                            call.enqueue(new Callback<Book_create_return>() {
                                @Override
                                public void onResponse(Call<Book_create_return> call, Response<Book_create_return> response) {
                                    Book_create_return book_create_return = response.body();
                                    Toast.makeText(Book_AcquaintanceModeActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Book_AcquaintanceModeActivity.this, Book_Writing.class);
                                    intent.putExtra("data",response.body().getData());
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<Book_create_return> call, Throwable t) {

                                }
                            });


                        }
                        else {
                            Toast.makeText(Book_AcquaintanceModeActivity.this, "请输入正确的人数", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(Book_AcquaintanceModeActivity.this, "请输入正确的人数", Toast.LENGTH_SHORT).show();
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
        title_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                title = s.toString();
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
                String s = String.valueOf(uri);
                choose_cover.setText(s);

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
        final String[] choices = {"封面1", "封面2", "封面3", "封面4", "封面5", "封面6", "封面7", "封面8"};
        //    设置一个下拉的列表选择项
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: {
                        iv_image.setImageResource(R.drawable.cover_1);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/点构图.jpg";
                        break;
                    }
                    case 1: {
                        iv_image.setImageResource(R.drawable.cover_2);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/抓拍，广场上玩滑板的少年.jpg";
                        break;
                    }
                    case 2: {
                        iv_image.setImageResource(R.drawable.cover_3);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/三分线，冷清的广场.jpg";
                        break;
                    }
                    case 3: {
                        iv_image.setImageResource(R.drawable.cover_4);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/三分线构图.jpg";
                        break;
                    }
                    case 4: {
                        iv_image.setImageResource(R.drawable.cover_5);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/4.jpg";
                        break;
                    }
                    case 5: {
                        iv_image.setImageResource(R.drawable.cover_6);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/3.jpg";
                        break;
                    }
                    case 6: {
                        iv_image.setImageResource(R.drawable.cover_7);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/2.jpg";
                        break;
                    }
                    case 7: {
                        iv_image.setImageResource(R.drawable.cover_8);
                        mCoverURL = "mini-project.muxixyz.com/drifting/covers/1.jpg";
                        break;
                    }
                }
            }
        });
        builder.show();
    }
}