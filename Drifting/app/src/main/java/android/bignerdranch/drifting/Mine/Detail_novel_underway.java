package android.bignerdranch.drifting.Mine;

import android.app.AlertDialog;
import android.bignerdranch.drifting.Login.Login_LoginActivity;
import android.bignerdranch.drifting.Novel.Novel_Activity;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.User_Now;
import android.bignerdranch.drifting.detail_request.getIdMessageReturn;
import android.bignerdranch.drifting.detail_request.getIdRequestBody;
import android.bignerdranch.drifting.detail_request.messageReturn;
import android.bignerdranch.drifting.detail_request.request;
import android.bignerdranch.drifting.detail_request.request_body;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Detail_novel_underway extends AppCompatActivity {
    private Long id;
    private Long ownerID;
    List<String> words;
    RecyclerView mRecyclerView;
    TextView mOwner_text;
    Button mContinue;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 200){
            updateUI(words);
                GetOwnerMessage(ownerID,User_Now.getUserNow().getUser().getToken());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novel_underway_detail);
        id = getIntent().getLongExtra("file_id", 0);
        Log.d("detail_bug",id+"");
        mOwner_text = findViewById(R.id.novel_detail_owner);
        mContinue = findViewById(R.id.underway_continue);
        mRecyclerView = (RecyclerView) findViewById(R.id.underway_content);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(Detail_novel_underway.this));
        words = new ArrayList<>();
        GetItemMessage(id,User_Now.getUserNow().getUser().getToken());
        mContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_novel_underway.this, Novel_Activity.class);
                intent.putExtra("file_id",id);
                File file = new File(getExternalFilesDir("DriftingNovel").toString()+File.separator + id + ".txt");
                if(!file.exists())
                startActivity(intent);
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("提示:");
                    builder.setIcon(R.drawable.logo);//设置图标
                    builder.setMessage("检测到您有保存的内容\n要继续上一次创作吗?");
                    builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try{
                            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
                            byte[] b = new byte[fis.available()];
                            fis.read(b);
                            String readStr = new String(b);
                            intent.putExtra("last_content",readStr);
                            startActivity(intent);
                        }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            file.delete();
                            startActivity(intent);
                        }
                    });
                builder.show();
                }
            }
        });
    }

    private void updateUI(List<String> text) {
       RecyclerView.Adapter mAdapter = new Adapter(text);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void GetItemMessage(Long id, String token) {
        List<String> Msg = new ArrayList<>();
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://116.204.121.9:61583/");
        Retrofit retrofit = builder.build();
        request ItemRequest = retrofit.create(request.class);
        Call<messageReturn> call = ItemRequest.novelRequest(token, new request_body(id));
        call.enqueue(new Callback<messageReturn>() {
            @Override
            public void onResponse(Call<messageReturn> call, Response<messageReturn> response) {
                if (response.isSuccessful()) {

                    if (response.body().getData() != null && response.body().getData().getContacts() != null) {
                        ArrayList<messageReturn.data.Contacts> contacts = response.body().getData().getContacts();
                        for (int i = 0; i < contacts.size(); i++)
                            Msg.add(contacts.get(i).getThe_words());
                        words = Msg;
                        ownerID = response.body().getData().getOwnerID();
                        Message message = new Message();
                        message.what = 200;
                        mHandler.sendMessageAtTime(message, 0);
                    }
                }
            }

            @Override
            public void onFailure(Call<messageReturn> call, Throwable t) {

            }
        });
    }

    private void GetOwnerMessage(Long id, String token) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://116.204.121.9:61583/");
        Retrofit retrofit = builder.build();
        request idRequest = retrofit.create(request.class);
        Call<getIdMessageReturn> idMessageReturnCall = idRequest.idRequest(token, new getIdRequestBody(id));
        idMessageReturnCall.enqueue(new Callback<getIdMessageReturn>() {
            @Override
            public void onResponse(Call<getIdMessageReturn> call, Response<getIdMessageReturn> response) {
                getIdMessageReturn messageReturn = response.body();
                mOwner_text.setText("创建者:" + response.body().getData().getName());
            }

            @Override
            public void onFailure(Call<getIdMessageReturn> call, Throwable t) {
            }
        });
    }

    private class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<String> text;

        public Adapter(List<String> text) {
            this.text = text;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(Detail_novel_underway.this);
            return new Detail_novel_underway.Holder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((Holder) holder).bind(text.get(position));
        }

        @Override
        public int getItemCount() {
            return text.size();
        }
    }

    private class Holder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private TextView writer;

        public Holder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.novel_book_completed_detail_item, parent, false));
            mTextView = (TextView) itemView.findViewById(R.id.contents);
            writer = (TextView) itemView.findViewById(R.id.writer);
        }

        public void bind(String text) {
            writer.setText("");
            mTextView.setText(text);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 是否触发按键为back键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_OK);
            onBackPressed();
            return true;
        } else {// 如果不是back键正常响应
            return super.onKeyDown(keyCode, event);
        }
    }
}
