package android.bignerdranch.drifting.Book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bignerdranch.drifting.Book.ReturnAndReauest.Book_InvitingFriends_request;
import android.bignerdranch.drifting.Book.ReturnAndReauest.Book_InvitingFriends_return;
import android.bignerdranch.drifting.Book.ReturnAndReauest.Book_Just_join_request;
import android.bignerdranch.drifting.Book.ReturnAndReauest.Book_Participate_request;
import android.bignerdranch.drifting.Book.ReturnAndReauest.Book_Participate_return;
import android.bignerdranch.drifting.Book.ReturnAndReauest.Book_create_return;
import android.bignerdranch.drifting.Book.ReturnAndReauest.Book_refuseAndAgree_request;
import android.bignerdranch.drifting.Friends.FriendsBeInvitedID;
import android.bignerdranch.drifting.Main.Main_DiscoveringFragment;
import android.bignerdranch.drifting.Main.Main_MainActivity;
import android.bignerdranch.drifting.Mine.FileUtils;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.User_Now;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Book_Writing extends AppCompatActivity {

    private EditText bookWText;
    private Button book_create_ingEd;
    private Button mButtonofBac;
    private String book_participateTx;
    private ImageView bookCover;
    private String Savedcontacts;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_writing);
        bookWText = findViewById(R.id.book_writingText);
        book_create_ingEd = findViewById(R.id.book_creatingToed);
        bookCover = findViewById(R.id.book_cover);
        mButtonofBac = findViewById(R.id.cookw);

        //接收数据新建的漂流本。
        Intent intent = getIntent();
        Savedcontacts = intent.getStringExtra("contact");
        if(Savedcontacts!=null)
            bookWText.setText(Savedcontacts);
        String idn = intent.getStringExtra("data");
        String cover = intent.getStringExtra("cover");
        long file_id = Long.valueOf(idn);

       // String used = FileUtils.readFromXML("/driftingBook/"+idn+".txt");
       //if ( used != null) {bookWText.setText(used);}

        if (cover.contains("mini-project.muxixyz.com/drifting/covers/点构图.jpg"))
            bookCover.setImageResource(R.drawable.cover_1);
        else if (cover.contains("mini-project.muxixyz.com/drifting/covers/抓拍，广场上玩滑板的少年.jpg"))
            bookCover.setImageResource(R.drawable.cover_2);
        else if (cover.contains("mini-project.muxixyz.com/drifting/covers/三分线，冷清的广场.jpg"))
            bookCover.setImageResource(R.drawable.cover_3);
        else if (cover.contains("mini-project.muxixyz.com/drifting/covers/三分线构图.jpg"))
            bookCover.setImageResource(R.drawable.cover_4);
        else if (cover.contains("mini-project.muxixyz.com/drifting/covers/4.jpg"))
            bookCover.setImageResource(R.drawable.cover_5);
        else if (cover.contains("mini-project.muxixyz.com/drifting/covers/3.jpg"))
            bookCover.setImageResource(R.drawable.cover_6);
        else if (cover.contains("mini-project.muxixyz.com/drifting/covers/2.jpg"))
            bookCover.setImageResource(R.drawable.cover_7);
        else if (cover.contains("mini-project.muxixyz.com/drifting/covers/1.jpg"))
            bookCover.setImageResource(R.drawable.cover_8);
        else bookCover.setImageResource(R.drawable.cover_8);
        bookWText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                book_participateTx = s.toString();
            }
        });
        mButtonofBac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String SAVE_CONTACT_PATH = getApplication().getFilesDir().getAbsolutePath();
                File file= new File(SAVE_CONTACT_PATH,"DriftingNote");
                if(!file.exists())
                    file.mkdirs();
                File contactfile = new File(file,file_id+".txt");
                if(contactfile.exists())
                    contactfile.delete();
                try {
                    contactfile.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(contactfile.getAbsolutePath());
                fileOutputStream.write(book_participateTx.getBytes());
                fileOutputStream.close();
                Toast.makeText(Book_Writing.this,"保存成功",Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        /**
         * 发起者参与漂流项目
         */
        book_create_ingEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("http://116.204.121.9:61583/")
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();
                ApiNote bookParticipate = retrofit.create(ApiNote.class);
                Book_Participate_request book_participate_request = new Book_Participate_request();
                book_participate_request.setFile_id(file_id);
                book_participate_request.setThe_word(book_participateTx);
                book_participate_request.setWriter_id(Long.valueOf(User_Now.getUserNow().getUser().getId()));
                Call<Book_Participate_return> call = bookParticipate.participateNote(book_participate_request
                ,User_Now.getUserNow().getUser().getToken());
                call.enqueue(new Callback<Book_Participate_return>() {
                    @Override
                    public void onResponse(Call<Book_Participate_return> call, Response<Book_Participate_return> response) {
                        Toast.makeText(Book_Writing.this, "成功参与", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Book_Participate_return> call, Throwable t) {

                    }
                });

                /**
                 * 发起者创建的同时也邀请了好友
                 * 添加一个空指针判断
                 */
                if (FriendsBeInvitedID.getStudentsID() != null){
                    for (int i=0;i<FriendsBeInvitedID.getStudentsID().size();i++){
                        Book_InvitingFriends_request book_invitingFriends_request
                                = new Book_InvitingFriends_request();
                        book_invitingFriends_request.setFile_id(Integer.valueOf(idn));
                        book_invitingFriends_request.setHost_id(User_Now.getUserNow().getUser().getId());
                        book_invitingFriends_request.setFile_kind("漂流本");
                        book_invitingFriends_request.setFriend_id(FriendsBeInvitedID.getStudentsID().get(i));

                        Retrofit.Builder builder2 = new Retrofit.Builder()
                                .baseUrl("http://116.204.121.9:61583/")
                                .addConverterFactory(GsonConverterFactory.create());
                        Retrofit retrofit2 = builder.build();
                        ApiNote inviting = retrofit2.create(ApiNote.class);

                        Call<Book_InvitingFriends_return> call1 = inviting.InvitingFriendsParticipate(
                                book_invitingFriends_request,User_Now.getUserNow().getUser().getToken()
                        );
                        call1.enqueue(new Callback<Book_InvitingFriends_return>() {
                            @Override
                            public void onResponse(Call<Book_InvitingFriends_return> call, Response<Book_InvitingFriends_return> response) {
                            }
                            @Override
                            public void onFailure(Call<Book_InvitingFriends_return> call, Throwable t) {
                            }
                        });

                    }

                }

                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(new Intent(Book_Writing.this, Main_MainActivity.class));
                        finish();
                    }
                };
                timer.schedule(timerTask,1000);



            }
        });



        new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
            }
        }.start();

        ImageButton back =  findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}