package android.bignerdranch.drifting.Friends;



import android.bignerdranch.drifting.Inviting.Loading.inviting_messageReturn;
import android.bignerdranch.drifting.Mine.User.User_Now;
import android.bignerdranch.drifting.Mine.User.User_connector;
import android.bignerdranch.drifting.R;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FriendsNotingActivity extends AppCompatActivity {

    private ArrayList<inviting_messageReturn.data> mData;
    private RecyclerView mRecyclerView;
    private AllListAdapter mAllListAdapter;
    private List<String> name = new ArrayList<>();
    private List<Integer> number = new ArrayList<>();
    private List<Integer> writer_number = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notingus);

        mRecyclerView = findViewById(R.id.Ongoing);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://116.204.121.9:61583/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit  = builder.build();
        User_connector userConnector = retrofit.create(User_connector.class);

        //自己创建的漂流本项目
        Call<inviting_messageReturn> call1 = userConnector.getMyBook(User_Now.getUserNow().getUser().getToken());
        call1.enqueue(new Callback<inviting_messageReturn>() {
            @Override
            public void onResponse(Call<inviting_messageReturn> call, Response<inviting_messageReturn> response) {

                ArrayList<inviting_messageReturn.data> data = response.body().getData();
                for (int i = 0;i< data.size();i++){

                    name.add(data.get(i).getName());
                    number.add(data.get(i).getNumber());
                    writer_number.add(data.get(i).getWriter_number());
                    Log.i("数据",data.get(i).getName());
                    update();

                }
                Log.i("shu",name.toString());
                Log.i("ss",number.toString());
            }

            @Override
            public void onFailure(Call<inviting_messageReturn> call, Throwable t) {
                Toast.makeText(FriendsNotingActivity.this, "网络请求数据失败", Toast.LENGTH_SHORT).show();

            }
        });
        //自己创建的漂流相机
        Call<inviting_messageReturn> call2 = userConnector.getMyPicture(User_Now.getUserNow().getUser().getToken());
        call2.enqueue(new Callback<inviting_messageReturn>() {
            @Override
            public void onResponse(Call<inviting_messageReturn> call, Response<inviting_messageReturn> response) {

                ArrayList<inviting_messageReturn.data> data = response.body().getData();
                for (int i = 0;i< data.size();i++){

                    name.add(data.get(i).getName());
                    number.add(data.get(i).getNumber());
                    writer_number.add(data.get(i).getWriter_number());
                    Log.i("数据",data.get(i).getName());
                    update();

                }
                Log.i("shu",name.toString());
                Log.i("ss",number.toString());
            }

            @Override
            public void onFailure(Call<inviting_messageReturn> call, Throwable t) {
                Toast.makeText(FriendsNotingActivity.this, "网络请求数据失败", Toast.LENGTH_SHORT).show();

            }
        });
        //自己创建的漂流画
        Call<inviting_messageReturn> call3 = userConnector.getMyDraw(User_Now.getUserNow().getUser().getToken());
        call3.enqueue(new Callback<inviting_messageReturn>() {
            @Override
            public void onResponse(Call<inviting_messageReturn> call, Response<inviting_messageReturn> response) {

                ArrayList<inviting_messageReturn.data> data = response.body().getData();
                for (int i = 0;i< data.size();i++){

                    name.add(data.get(i).getName());
                    number.add(data.get(i).getNumber());
                    writer_number.add(data.get(i).getWriter_number());
                    Log.i("数据",data.get(i).getName());
                    update();

                }
                Log.i("shu",name.toString());
                Log.i("ss",number.toString());
            }

            @Override
            public void onFailure(Call<inviting_messageReturn> call, Throwable t) {
                Toast.makeText(FriendsNotingActivity.this, "网络请求数据失败", Toast.LENGTH_SHORT).show();

            }
        });
        //自己创建的漂流小说
        Call<inviting_messageReturn> call4 = userConnector.getMyNovel(User_Now.getUserNow().getUser().getToken());
        call4.enqueue(new Callback<inviting_messageReturn>() {
            @Override
            public void onResponse(Call<inviting_messageReturn> call, Response<inviting_messageReturn> response) {

                ArrayList<inviting_messageReturn.data> data = response.body().getData();
                for (int i = 0;i< data.size();i++){

                    name.add(data.get(i).getName());
                    number.add(data.get(i).getNumber());
                    writer_number.add(data.get(i).getWriter_number());
                    Log.i("数据",data.get(i).getName());
                    update();

                }
                Log.i("shu",name.toString());
                Log.i("ss",number.toString());
            }

            @Override
            public void onFailure(Call<inviting_messageReturn> call, Throwable t) {
                Toast.makeText(FriendsNotingActivity.this, "网络请求数据失败", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void update(){
        mAllListAdapter = new AllListAdapter(name,number,writer_number);
        mRecyclerView.setAdapter(mAllListAdapter);

    }


    public class AllListHolder extends RecyclerView.ViewHolder {

        private TextView name_of_all;
        private TextView WillOver;
        private String mName;
        private Integer mNumber;
        private Integer mWriter_number;
        public void bindw(String name0, Integer integer1, Integer integer) {
            mName = name0;
            mNumber = integer1;
            mWriter_number = integer;
            name_of_all.setText(name0);
            WillOver.setText("进度"+mWriter_number+"/"+mNumber);

        }

        public AllListHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.noticing_piece,parent,false));
//这个地方要加itemView，报错了好多次才遭到
            name_of_all = itemView.findViewById(R.id.nameOfAll);
            WillOver = itemView.findViewById(R.id.WillOver);
        }



    }
    public class AllListAdapter extends RecyclerView.Adapter<AllListHolder> {

        private List<String> name = new ArrayList<>();
        private List<Integer> number = new ArrayList<>();
        private List<Integer> writer_number = new ArrayList<>();

        public AllListAdapter(List<String> name, List<Integer> number, List<Integer> writer_number) {
            this.name = name;
            this.number = number;
            this.writer_number = writer_number;
        }

        @Override
        public AllListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FriendsNotingActivity.this);
            return new AllListHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(AllListHolder holder, int position) {
            String name0 = name.get(position);
            Integer integer1 = number.get(position);
            Integer integer = writer_number.get(position);
            holder.bindw(name0,integer1,integer);
        }

        @Override
        public int getItemCount() {
            return name.size();
        }
    }
}
