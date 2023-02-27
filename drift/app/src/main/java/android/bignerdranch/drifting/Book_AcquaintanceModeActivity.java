package android.bignerdranch.drifting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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

/**
 * 漂流本熟人模式创建项目
 */
public class Book_AcquaintanceModeActivity extends AppCompatActivity {
    private ImageView iv_image;
    private final int max_number = 9;
    private String name;
    private String title;
    private String person_number;
    private int number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_acquaintance_mode);

        iv_image = (ImageView) findViewById(R.id.iv_image);
        Button choose_cover = (Button) findViewById(R.id.acquaintance_choose_front_cover);
        Button start = (Button) findViewById(R.id.acquaintance_start);
        Button inviting_friends = (Button) findViewById(R.id.acquaintance_inviting_friends);
        EditText name_text = (EditText) findViewById(R.id.name_text);
        EditText title_text = (EditText) findViewById(R.id.title_text);
        EditText person_number_text = (EditText) findViewById(R.id.person_number_text);

        inviting_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Book_AcquaintanceModeActivity.this, "正在开发中", Toast.LENGTH_SHORT).show();
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
                            startActivity(new Intent(Book_AcquaintanceModeActivity.this, Book_Writing.class));
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
                        iv_image.setImageResource(R.drawable.yaoqing_background);
                        break;
                    }
                }
            }
        });
        builder.show();
    }
}