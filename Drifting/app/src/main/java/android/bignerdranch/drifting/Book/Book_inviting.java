package android.bignerdranch.drifting.Book;

import android.bignerdranch.drifting.Book.ReturnAndReauest.Book_create_return;
import android.bignerdranch.drifting.Book.ReturnAndReauest.Book_refuseAndAgree_request;
import android.bignerdranch.drifting.Inviting.Loading.inviting_messageReturn;
import android.bignerdranch.drifting.Inviting.Loading.inviting_request;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.GetNameFormIDRequest;
import android.bignerdranch.drifting.User.User_Now;
import android.bignerdranch.drifting.User.User_connector;
import android.bignerdranch.drifting.User.User_name_getFormID_return;
import android.bignerdranch.drifting.detail_request.messageReturn;
import android.bignerdranch.drifting.detail_request.request;
import android.bignerdranch.drifting.detail_request.request_body;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *用户参加漂流项目
 */
public class Book_inviting extends AppCompatActivity {
    private TextView bookName;
    private TextView bookTheme;
    private TextView bookStartTime;
    private TextView bookHostName;
    private Button refuseBtn;
    private Button agreeBtn;
    private String bookCover; //漂流封面
    private String timeCreate; //漂流创建时间
    private String theme; //漂流主题
    private int number;//漂流人数
    private Integer hostId;
    private RecyclerView bookList;
    private BookListAdapter mBookListAdapter;
    private Book_refuseAndAgree_request mBook_refuse_request; //拒绝参加

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_inviting);

        initView();

        Intent intent = getIntent();
        String idn = intent.getStringExtra("file_id");
        long id = Long.valueOf(idn);


        Retrofit.Builder builder0 = new Retrofit.Builder()
                .baseUrl("http://116.204.121.9:61583/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit0 = builder0.build();

        //获得获得漂流本的其他内容
        inviting_request bookInviting = retrofit0.create(inviting_request.class);
        Call<inviting_messageReturn> inviting_requestCall =  bookInviting.book_request(User_Now.getUserNow().getUser().getToken());
        inviting_requestCall.enqueue(new Callback<inviting_messageReturn>() {
            @Override
            public void onResponse(Call<inviting_messageReturn> call, Response<inviting_messageReturn> response) {
                Log.i("获得的邀请信息",response.body().toString());
                Log.i("id",String.valueOf(id));
                for (int i = 0;i<response.body().getData().size();i++){
                    if (response.body().getData().get(i).getFile_id() == id){
                        theme = response.body().getData().get(i).getTheme();
                        bookCover = response.body().getData().get(i).getCover();
                        timeCreate = response.body().getData().get(i).getCreatedAt();
                        number = response.body().getData().get(i).getNumber();
                        Toast.makeText(Book_inviting.this, String.valueOf(response.body().getData().get(i).getFile_id()), Toast.LENGTH_SHORT).show();


                    }
                }


            }

            @Override
            public void onFailure(Call<inviting_messageReturn> call, Throwable t) {
                Toast.makeText(Book_inviting.this, "网络错误获得漂流列表表", Toast.LENGTH_SHORT).show();
            }
        });

        //设置背景图
        // try {
        //
        // }
        //获得单个漂流本信息
        Timer timer1= new Timer();
        TimerTask timerTask1 = new TimerTask() {
            @Override
            public void run() {

                request  request = retrofit0.create(android.bignerdranch.drifting.detail_request.request.class);
                Call<messageReturn> messageReturnCall = request.bookRequest(User_Now.getUserNow().getUser().getToken(),
                        new request_body(id));
                messageReturnCall.enqueue(new Callback<messageReturn>() {
                    @Override
                    public void onResponse(Call<messageReturn> call, Response<messageReturn> response) {
                        messageReturn messageReturn = response.body();
                        Log.i("ActivityOnResponse",messageReturn.toString());
                        bookName.setText(messageReturn.getData().getName());
                        hostId = Integer.valueOf(String.valueOf(messageReturn.getData().getOwnerID()));
                        bookTheme.setText(theme);
                        bookStartTime.setText(cutTime(timeCreate));
                        Log.i("ss",messageReturn.getData().getContacts().toString());
                        mBookListAdapter = new BookListAdapter(messageReturn.getData().getContacts());
                        bookList.setAdapter(mBookListAdapter);
                        //
                       mBook_refuse_request = new Book_refuseAndAgree_request(id,"漂流本"
                               ,messageReturn.getData().getOwnerID());



                        //获得漂流创建者的名字
                        GetNameFormIDRequest getNameFormIDRequest  = new GetNameFormIDRequest();
                        getNameFormIDRequest.setStudentID(messageReturn.getData().getOwnerID());

                        User_connector hostname = retrofit0.create(User_connector.class);
                        Call<User_name_getFormID_return> user_name_getFormID_returnCall =
                                hostname.getHostName(getNameFormIDRequest,User_Now.getUserNow().getUser().getToken());
                        user_name_getFormID_returnCall.enqueue(new Callback<User_name_getFormID_return>() {
                            @Override
                            public void onResponse(Call<User_name_getFormID_return> call, Response<User_name_getFormID_return> response) {
                             //Toast.makeText(Book_inviting.this, response.body().getData().getName(), Toast.LENGTH_SHORT).show();
                             bookHostName.setText(response.body().getData().getName());
                            }

                            @Override
                            public void onFailure(Call<User_name_getFormID_return> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<messageReturn> call, Throwable t) {
                        Toast.makeText(Book_inviting.this, "网络错误", Toast.LENGTH_SHORT).show();
                    }
                });

                //拒绝参加漂流本
                refuseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ApiNote refuse = retrofit0.create(ApiNote.class);
                        Call<Book_create_return> returnCall  = refuse.refuseJoinBook(mBook_refuse_request,
                                User_Now.getUserNow().getUser().getToken());
                        returnCall.enqueue(new Callback<Book_create_return>() {
                            @Override
                            public void onResponse(Call<Book_create_return> call, Response<Book_create_return> response) {
                            }
                            @Override
                            public void onFailure(Call<Book_create_return> call, Throwable t) {

                            }
                        });

                    }
                });
                //同意参加漂流项目
                agreeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent1 = new Intent(Book_inviting.this,Book_Writing.class);
                        intent1.putExtra("data",idn);
                        intent1.putExtra("cover",bookCover);

                        startActivity(intent1);

                    }
                });




            }
        };
        timer1.schedule(timerTask1,500);





    }

    public class BookListHolder extends RecyclerView.ViewHolder {

        private TextView join_name;
        private TextView content;
        private messageReturn.data.Contacts mContacts;


        public void bindw(messageReturn.data.Contacts contacts){
            mContacts  = contacts;
            GetNameFormIDRequest getNameFormIDRequest  = new GetNameFormIDRequest();
            getNameFormIDRequest.setStudentID(mContacts.getWriter_id());
            content.setText(mContacts.getThe_words());

            Retrofit.Builder builder  = new Retrofit.Builder()
                    .baseUrl("http://116.204.121.9:61583/")
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit  = builder.build();

            User_connector userConnector = retrofit.create(User_connector.class);
            Call<User_name_getFormID_return> user_name_getFormID_returnCall
                    = userConnector.getHostName(getNameFormIDRequest,
                    User_Now.getUserNow().getUser().getToken());
            user_name_getFormID_returnCall.enqueue(new Callback<User_name_getFormID_return>() {
                @Override
                public void onResponse(Call<User_name_getFormID_return> call, Response<User_name_getFormID_return> response) {
                    join_name.setText(response.body().getData().getName());
                }

                @Override
                public void onFailure(Call<User_name_getFormID_return> call, Throwable t) {

                }
            });
            if (mContacts.getThe_words() != null){
           content.setText(mContacts.getThe_words());
           }
        }

        public BookListHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.book_inviting_recycleview,parent,false));
//这个地方要加itemView，报错了好多次才遭到
            join_name = itemView.findViewById(R.id.parter_name);
            content =itemView.findViewById(R.id.body_oflist);
        }


    }
    public class BookListAdapter extends RecyclerView.Adapter<BookListHolder> {

        private ArrayList<messageReturn.data.Contacts> mContactsBook;

        public BookListAdapter(ArrayList<messageReturn.data.Contacts> contactsBook) {
            mContactsBook = contactsBook;
        }

        @Override
        public BookListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(Book_inviting.this);
            return new BookListHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(BookListHolder holder, int position) {
            messageReturn.data.Contacts contacts = mContactsBook.get(position);
            holder.bindw(contacts);
        }

        @Override
        public int getItemCount() {
            return mContactsBook.size();
        }
    }




    private void initView(){
        bookList = findViewById(R.id.book_list);
        bookList.setLayoutManager(new LinearLayoutManager(this));
        bookName = findViewById(R.id.book_name);
        bookHostName = findViewById(R.id.book_host_name);
        bookStartTime  =findViewById(R.id.start_time);
        bookTheme = findViewById(R.id.book_theme);
        refuseBtn = findViewById(R.id.refuse_btn);
        agreeBtn =findViewById(R.id.agreeToBtn);
    }

    private String cutTime(String s){
        String year = s.substring(0,4);
        String mouth = s.substring(5,7);
        String days = s.substring(8,10);
        return year+"年"+mouth+"月"+days+"日";
    }
    private static Bitmap getImage(String path) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if(conn. getResponseCode() == 200){
            InputStream inStream = conn. getInputStream();
            Bitmap bitmap = BitmapFactory. decodeStream(inStream) ;
            return bitmap;
        }else return null;
    }
}


