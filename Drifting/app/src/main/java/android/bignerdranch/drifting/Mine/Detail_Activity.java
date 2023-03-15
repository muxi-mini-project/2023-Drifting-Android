package android.bignerdranch.drifting.Mine;

import android.bignerdranch.drifting.Login.Login_LoginActivity;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.detail_request.*;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Detail_Activity extends AppCompatActivity {

    private String type;
    private long file_id = 0;
    private long host_id;

    private ImageButton back;
    private ImageView cover;
    private TextView name;
    private TextView theme;
    private TextView num;
    private TextView creator;
    private TextView date;
    private RecyclerView content_view;
    private messageReturn mMessageReturn;
    private ContentAdapter_image mAdapter_image;
    private ContentAdapter_text mAdapter_text;
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://116.204.121.9:61583/")
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        type = getIntent().getStringExtra("type");
        file_id = getIntent().getLongExtra("file_id", 0);
        List<Items> list = GetAllItems.getGetAllItems().getList_Items_All_ed();

        if (type.equals("漂流画") || type.equals("漂流相机")) {
            setContentView(R.layout.drawing_camera_completed_detail);
        } else {
            setContentView(R.layout.novel_book_completed_detail);
        }

        back = (ImageButton) findViewById(R.id.back);
        cover = (ImageView) findViewById(R.id.cover);

        name = (TextView) findViewById(R.id.name);
        theme = (TextView) findViewById(R.id.theme);
        num = (TextView) findViewById(R.id.num);
        creator = (TextView) findViewById(R.id.creator);
        date = (TextView) findViewById(R.id.date);

        content_view = (RecyclerView) findViewById(R.id.content);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Detail_Activity.this);
        content_view.setLayoutManager(linearLayoutManager);

        switch (type) {
            case "漂流画": {
                for(int i = 0;i < list.size();i++){
                    if(list.get(i).getId() == file_id && list.get(i).getItem_kind().equals("漂流画")){
                        String url = "http://" + list.get(i).getCover();
                        Glide.with(Detail_Activity.this)
                                .load(url)
                                .into(cover);
                        host_id = list.get(i).getCreatorID();
                        name.setText("标题：" + list.get(i).getName());
                        theme.setText("主题：" + list.get(i).getTheme());
                        num.setText("收集数量：" + list.get(i).getMaxamount());
                        date.setText("创建日期：" + list.get(i).getCreate_time().substring(0, 10));
                        request idRequest = retrofit.create(request.class);
                        Call<getIdMessageReturn> idMessageReturnCall = idRequest.idRequest(Login_LoginActivity.getToken(), new getIdRequestBody(host_id));
                        idMessageReturnCall.enqueue(new Callback<getIdMessageReturn>() {
                            @Override
                            public void onResponse(Call<getIdMessageReturn> call, Response<getIdMessageReturn> response) {
                                getIdMessageReturn messageReturn = response.body();
                                creator.setText("创建者：" + messageReturn.getData().getName());
                            }

                            @Override
                            public void onFailure(Call<getIdMessageReturn> call, Throwable t) {
                                creator.setText("创建者：获取失败");
                            }
                        });
                        request detailRequest = retrofit.create(request.class);
                        Call<messageReturn> messageReturnCall = detailRequest.drawingRequest(Login_LoginActivity.getToken(),new request_body(file_id));
                        messageReturnCall.enqueue(new Callback<messageReturn>() {
                            @Override
                            public void onResponse(Call<messageReturn> call, Response<messageReturn> response) {
                                mMessageReturn = response.body();
                                updateContacts_image();
                            }
                            @Override
                            public void onFailure(Call<messageReturn> call, Throwable t) {
                                Toast.makeText(Detail_Activity.this, "加载内容失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                break;
            }
            case "漂流相机": {
                for(int i = 0;i < list.size();i++){
                    if(list.get(i).getId() == file_id && list.get(i).getItem_kind().equals("漂流相机")){
                        String url = "http://" + list.get(i).getCover();
                        Glide.with(Detail_Activity.this)
                                .load(url)
                                .into(cover);
                        host_id = list.get(i).getCreatorID();
                        name.setText("标题：" + list.get(i).getName());
                        theme.setText("主题：" + list.get(i).getTheme());
                        num.setText("收集数量：" + list.get(i).getMaxamount());
                        date.setText("创建日期：" + list.get(i).getCreate_time().substring(0, 10));
                        request idRequest = retrofit.create(request.class);
                        Call<getIdMessageReturn> idMessageReturnCall = idRequest.idRequest(Login_LoginActivity.getToken(), new getIdRequestBody(host_id));
                        idMessageReturnCall.enqueue(new Callback<getIdMessageReturn>() {
                            @Override
                            public void onResponse(Call<getIdMessageReturn> call, Response<getIdMessageReturn> response) {
                                getIdMessageReturn messageReturn = response.body();
                                creator.setText("创建者：" + messageReturn.getData().getName());
                            }

                            @Override
                            public void onFailure(Call<getIdMessageReturn> call, Throwable t) {
                                creator.setText("创建者：获取失败");
                            }
                        });
                        request detailRequest = retrofit.create(request.class);
                        Call<messageReturn> messageReturnCall = detailRequest.cameraRequest(Login_LoginActivity.getToken(),new request_body(file_id));
                        messageReturnCall.enqueue(new Callback<messageReturn>() {
                            @Override
                            public void onResponse(Call<messageReturn> call, Response<messageReturn> response) {
                                mMessageReturn = response.body();
                                updateContacts_image();
                            }
                            @Override
                            public void onFailure(Call<messageReturn> call, Throwable t) {
                                Toast.makeText(Detail_Activity.this, "加载内容失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                break;
            }
            case "漂流本":
                for(int i = 0;i < list.size();i++){
                    if(list.get(i).getId() == file_id && list.get(i).getItem_kind().equals("漂流本")){
                        String url = "http://" + list.get(i).getCover();
                        Glide.with(Detail_Activity.this)
                                .load(url)
                                .into(cover);
                        host_id = list.get(i).getCreatorID();
                        name.setText("标题：" + list.get(i).getName());
                        theme.setText("主题：" + list.get(i).getTheme());
                        num.setText("收集数量：" + list.get(i).getMaxamount());
                        date.setText("创建日期：" + list.get(i).getCreate_time().substring(0, 10));
                        request idRequest = retrofit.create(request.class);
                        Call<getIdMessageReturn> idMessageReturnCall = idRequest.idRequest(Login_LoginActivity.getToken(), new getIdRequestBody(host_id));
                        idMessageReturnCall.enqueue(new Callback<getIdMessageReturn>() {
                            @Override
                            public void onResponse(Call<getIdMessageReturn> call, Response<getIdMessageReturn> response) {
                                getIdMessageReturn messageReturn = response.body();
                                creator.setText("创建者：" + messageReturn.getData().getName());
                            }

                            @Override
                            public void onFailure(Call<getIdMessageReturn> call, Throwable t) {
                                creator.setText("创建者：获取失败");
                            }
                        });
                        request detailRequest = retrofit.create(request.class);
                        Call<messageReturn> messageReturnCall = detailRequest.bookRequest(Login_LoginActivity.getToken(),new request_body(file_id));
                        messageReturnCall.enqueue(new Callback<messageReturn>() {
                            @Override
                            public void onResponse(Call<messageReturn> call, Response<messageReturn> response) {
                                mMessageReturn = response.body();
                                updateContacts_text();
                            }
                            @Override
                            public void onFailure(Call<messageReturn> call, Throwable t) {
                                Toast.makeText(Detail_Activity.this, "加载内容失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                break;
            case "漂流小说":
                for(int i = 0;i < list.size();i++){
                    if(list.get(i).getId() == file_id && list.get(i).getItem_kind().equals("漂流小说")){
                        String url = "http://" + list.get(i).getCover();
                        Glide.with(Detail_Activity.this)
                                .load(url)
                                .into(cover);
                        host_id = list.get(i).getCreatorID();
                        name.setText("标题：" + list.get(i).getName());
                        theme.setText("主题：" + list.get(i).getTheme());
                        num.setText("收集数量：" + list.get(i).getMaxamount());
                        date.setText("创建日期：" + list.get(i).getCreate_time().substring(0, 10));
                        request idRequest = retrofit.create(request.class);
                        Call<getIdMessageReturn> idMessageReturnCall = idRequest.idRequest(Login_LoginActivity.getToken(), new getIdRequestBody(host_id));
                        idMessageReturnCall.enqueue(new Callback<getIdMessageReturn>() {
                            @Override
                            public void onResponse(Call<getIdMessageReturn> call, Response<getIdMessageReturn> response) {
                                getIdMessageReturn messageReturn = response.body();
                                creator.setText("创建者：" + messageReturn.getData().getName());
                            }

                            @Override
                            public void onFailure(Call<getIdMessageReturn> call, Throwable t) {
                                creator.setText("创建者：获取失败");
                            }
                        });
                        request detailRequest = retrofit.create(request.class);
                        Call<messageReturn> messageReturnCall = detailRequest.novelRequest(Login_LoginActivity.getToken(),new request_body(file_id));
                        messageReturnCall.enqueue(new Callback<messageReturn>() {
                            @Override
                            public void onResponse(Call<messageReturn> call, Response<messageReturn> response) {
                                mMessageReturn = response.body();
                                updateContacts_text();
                            }
                            @Override
                            public void onFailure(Call<messageReturn> call, Throwable t) {
                                Toast.makeText(Detail_Activity.this, "加载内容失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                break;
        }

    }

    public void updateContacts_image(){
        mAdapter_image = new ContentAdapter_image(mMessageReturn.getData().getContacts());
        content_view.setAdapter(mAdapter_image);
    }
    public void updateContacts_text(){
        mAdapter_text = new ContentAdapter_text(mMessageReturn.getData().getContacts());
        content_view.setAdapter(mAdapter_text);
    }

    private class ContentHolder_image extends RecyclerView.ViewHolder{
        private ImageView image1;
        private ImageView image2;

        public ContentHolder_image(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.drawing_camera_completed_detail_item, parent, false));

            image1 = (ImageView) itemView.findViewById(R.id.image1);
            image2 = (ImageView) itemView.findViewById(R.id.image2);
        }

        public void bind(messageReturn.data.Contacts contacts1,messageReturn.data.Contacts contacts2){
            Glide.with(Detail_Activity.this).load(contacts1.getThe_words()).into(image1);
            Glide.with(Detail_Activity.this).load(contacts2.getThe_words()).into(image2);
        }

        public void bind(messageReturn.data.Contacts contacts1){
            Glide.with(Detail_Activity.this).load("http://"+contacts1.getThe_words()).into(image1);
        }
    }

    private class ContentHolder_text extends RecyclerView.ViewHolder{
        private TextView writer;
        private TextView contents;

        public ContentHolder_text(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.novel_book_completed_detail_item, parent, false));

            writer = (TextView) itemView.findViewById(R.id.writer);
            contents = (TextView) itemView.findViewById(R.id.contents);
        }

        public void bind(messageReturn.data.Contacts contacts){
            String writer_id;
            request idRequest = retrofit.create(request.class);
            Call<getIdMessageReturn> idMessageReturnCall = idRequest.idRequest(Login_LoginActivity.getToken(), new getIdRequestBody(host_id));
            idMessageReturnCall.enqueue(new Callback<getIdMessageReturn>() {
                @Override
                public void onResponse(Call<getIdMessageReturn> call, Response<getIdMessageReturn> response) {
                    getIdMessageReturn messageReturn = response.body();
                    writer.setText("作者：" + messageReturn.getData().getName());
                    contents.setText(contacts.getThe_words());
                }
                @Override
                public void onFailure(Call<getIdMessageReturn> call, Throwable t) {
                    writer.setText("作者：获取失败");
                }
            });
        }
    }

    private class ContentAdapter_image extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private ArrayList<messageReturn.data.Contacts> mContacts;
        public ContentAdapter_image(ArrayList<messageReturn.data.Contacts> mContacts) {
            this.mContacts = mContacts;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(Detail_Activity.this);
            return new ContentHolder_image(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if(mMessageReturn.getData().getContacts().size() - 2 * (position + 1) >= 0){
                messageReturn.data.Contacts contacts1 = mContacts.get(2 * position);
                messageReturn.data.Contacts contacts2 = mContacts.get(2 * position + 1);
                ((ContentHolder_image)holder).bind(contacts1,contacts2);
            }else{
                messageReturn.data.Contacts contacts1 = mContacts.get(2 * position);
                ((ContentHolder_image)holder).bind(contacts1);
            }

        }

        @Override
        public int getItemCount() {
            Log.d("detail_bug",mMessageReturn.getData().getContacts().size()/2+"");
            return mMessageReturn.getData().getContacts().size()/2%2 == 0?mMessageReturn.getData().getContacts().size():
                    mMessageReturn.getData().getContacts().size()+1;
        }
    }

    private class ContentAdapter_text extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private ArrayList<messageReturn.data.Contacts> mContacts;
        public ContentAdapter_text(ArrayList<messageReturn.data.Contacts> mContacts) {
            this.mContacts = mContacts;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(Detail_Activity.this);
            return new ContentHolder_text(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            messageReturn.data.Contacts contacts = mContacts.get(position);
            ((ContentHolder_text)holder).bind(contacts);
        }

        @Override
        public int getItemCount() {
            return mMessageReturn.getData().getContacts().size();
        }
    }
}