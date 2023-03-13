package android.bignerdranch.drifting.Inviting;

import android.bignerdranch.drifting.Inviting.Loading.inviting_request;
import android.bignerdranch.drifting.Inviting.Loading.inviting_messageReturn;
import android.bignerdranch.drifting.Login.Login_LoginActivity;
import android.bignerdranch.drifting.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Inviting_Fragment extends Fragment {
    private ArrayList<inviting_messageReturn.data> mData;

    private TextView detail;
    private String detailStr = "";
    private RecyclerView mRecyclerView;
    private int drawing_num0 = 0;
    private int drawing_num1 = 0;
    private int novel_num0 = 0;
    private int novel_num1 = 0;
    private int camera_num0 = 0;
    private int camera_num1 = 0;
    private int book_num0 = 0;
    private int book_num1 = 0;
    public static Inviting_Fragment newInstance(){
        return new Inviting_Fragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inviting_fragment,null);
        detail = (TextView) view.findViewById(R.id.inviting_detail);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.inviting_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        String token = Login_LoginActivity.getToken();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://116.204.121.9:61583/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        inviting_request request = retrofit.create(inviting_request.class);

        Call<inviting_messageReturn> call0 = request.drawing_request(Login_LoginActivity.getToken());
        call0.enqueue(new Callback<inviting_messageReturn>() {
            @Override
            public void onResponse(@NonNull Call<inviting_messageReturn> call, @NonNull Response<inviting_messageReturn> response) {
                inviting_messageReturn request_return = response.body();
                if(response.body().getData() != null){
                    mData = request_return.getData();
                    //0生1熟
                    for(int i = 0;i < mData.size();i++){
                        switch (mData.get(i).getKind()){
                            case 0:{
                                drawing_num0++;
                                break;
                            }
                            case 1:{
                                drawing_num1++;
                                break;
                            }
                        }
                    }
                    if(drawing_num0 != 0){
                        detailStr += "来自随机的漂流画    X" + drawing_num0 +"\n";
                    }
                    if(drawing_num1 != 0){
                        detailStr += "来自密友的漂流画    X" + drawing_num1 +"\n";
                    }
                }else{
                    detailStr += "尚未收到漂流画邀请哦" + "\n";
                }
                detail.setText(detailStr);
            }

            @Override
            public void onFailure(@NonNull Call<inviting_messageReturn> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "获取邀请信息失败", Toast.LENGTH_SHORT).show();
            }
        });

        Call<inviting_messageReturn> call1 = request.novel_request(Login_LoginActivity.getToken());
        call1.enqueue(new Callback<inviting_messageReturn>() {
            @Override
            public void onResponse(@NonNull Call<inviting_messageReturn> call, @NonNull Response<inviting_messageReturn> response) {
                inviting_messageReturn request_return = response.body();
                if(response.body().getData() != null){
                    mData = request_return.getData();
                    //0生1熟
                    for(int i = 0;i < mData.size();i++){
                        switch (mData.get(i).getKind()){
                            case 0:{
                                novel_num0++;
                                break;
                            }
                            case 1:{
                                novel_num1++;
                                break;
                            }
                        }
                    }
                    if(novel_num0 != 0){
                        detailStr += "来自随机的漂流小说  X" + novel_num0 +"\n";
                    }
                    if(novel_num1 != 0){
                        detailStr += "来自密友的漂流小说  X" + novel_num1 +"\n";
                    }
                }else{
                    detailStr += "尚未收到漂流小说邀请哦" + "\n";
                }
                detail.setText(detailStr);
            }

            @Override
            public void onFailure(@NonNull Call<inviting_messageReturn> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "获取邀请信息失败", Toast.LENGTH_SHORT).show();
            }
        });

        Call<inviting_messageReturn> call2 = request.book_request(Login_LoginActivity.getToken());
        call2.enqueue(new Callback<inviting_messageReturn>() {
            @Override
            public void onResponse(@NonNull Call<inviting_messageReturn> call, @NonNull Response<inviting_messageReturn> response) {
                inviting_messageReturn request_return = response.body();
                if(response.body().getData() != null) {
                    mData = request_return.getData();
                    //0生1熟
                    for (int i = 0; i < mData.size(); i++) {
                        switch (mData.get(i).getKind()) {
                            case 0: {
                                book_num0++;
                                break;
                            }
                            case 1: {
                                book_num1++;
                                break;
                            }
                        }
                    }
                    if (book_num0 != 0) {
                        detailStr += "来自随机的漂流本   X" + novel_num0 + "\n";
                    }
                    if (book_num1 != 0) {
                        detailStr += "来自随机的漂流本   X" + novel_num1 + "\n";
                    }
                }else{
                    detailStr += "尚未收到漂流本邀请哦" + "\n";
                }
                detail.setText(detailStr);
            }
            @Override
            public void onFailure(@NonNull Call<inviting_messageReturn> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "获取邀请信息失败", Toast.LENGTH_SHORT).show();
            }
        });

        Call<inviting_messageReturn> call3 = request.camera_request(Login_LoginActivity.getToken());
        call3.enqueue(new Callback<inviting_messageReturn>() {
            @Override
            public void onResponse(@NonNull Call<inviting_messageReturn> call, @NonNull Response<inviting_messageReturn> response) {
                inviting_messageReturn request_return = response.body();
                if(response.body().getData() != null){
                    mData = request_return.getData();
                    //0生1熟
                    for(int i = 0;i < mData.size();i++){
                        switch (mData.get(i).getKind()){
                            case 0:{
                                camera_num0++;
                                break;
                            }
                            case 1:{
                                camera_num1++;
                                break;
                            }
                        }
                    }
                    if(camera_num0 != 0){
                        detailStr += "来自随机的漂流相机  X" + camera_num0 +"\n";
                    }
                    if(camera_num1 != 0){
                        detailStr += "来自密友的漂流相机  X" + camera_num1 +"\n";
                    }
                }else{
                    detailStr += "尚未收到漂流相册邀请哦" + "\n";
                }
                detail.setText(detailStr);
            }

            @Override
            public void onFailure(@NonNull Call<inviting_messageReturn> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "获取邀请信息失败", Toast.LENGTH_SHORT).show();
            }
        });


        return view;

    }


}
