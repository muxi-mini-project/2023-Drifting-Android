package android.bignerdranch.drifting.Main;

import android.bignerdranch.drifting.Book.Book_DriftingBookActivity;
import android.bignerdranch.drifting.Camera.Camera_Start;
import android.bignerdranch.drifting.Drawing.DrawingDetailRequest.Drawing_InvitingActivity;
import android.bignerdranch.drifting.Drawing.DrawingDetailRequest.messageReturn;
import android.bignerdranch.drifting.Drawing.DrawingDetailRequest.request;
import android.bignerdranch.drifting.Drawing.DrawingDetailRequest.request_body;
import android.bignerdranch.drifting.Drawing.Drawing_switch_mode;
import android.bignerdranch.drifting.Inviting.Inviting_;
import android.bignerdranch.drifting.Inviting.Inviting_Lab;
import android.bignerdranch.drifting.Inviting.Loading.user_drawing_request_return;
import android.bignerdranch.drifting.Login.Login_LoginActivity;
import android.bignerdranch.drifting.R;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main_DiscoveringFragment extends Fragment {
    public static Main_DiscoveringFragment newInstance(){
        return new Main_DiscoveringFragment();
    }

    private RecyclerView mRecyclerView;
    private InvitingAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discovering_fragment,null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.discovering_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        updateUI();

        //线性布局视图绑定
        ConstraintLayout drifting_book = (ConstraintLayout) view.findViewById(R.id.drifting_book);
        ConstraintLayout drifting_camera = (ConstraintLayout) view.findViewById(R.id.drifting_camera);
        ConstraintLayout drifting_novel = (ConstraintLayout) view.findViewById(R.id.drifting_novel);
        ConstraintLayout drifting_drawing = (ConstraintLayout) view.findViewById(R.id.drifting_drawing);

        //线性布局视图点击响应
        drifting_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(requireActivity(), "book clicked",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), Book_DriftingBookActivity.class));
            }
        });
        drifting_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(requireActivity(),"camera clicked",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), Camera_Start.class));
            }
        });
        drifting_novel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(requireActivity(),"开发中",Toast.LENGTH_SHORT).show();
            }
        });
        drifting_drawing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Drawing_switch_mode.class));
            }
        });

        /**
         * 这里有错误
         * java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.String java.util.ArrayList.toString()' on a null object reference
         *         at android.bignerdranch.drifting.Main.Main_DiscoveringFragment$5.onResponse(Main_DiscoveringFragment.java:106)
         *         面前还不知道怎么改
         */
        /*Retrofit.Builder builder0 = new Retrofit.Builder()
                .baseUrl("http://116.204.121.9:61583/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit0 = builder0.build();
        android.bignerdranch.drifting.Inviting.Loading.user_drawing_request user_drawing_request
                = retrofit0.create(android.bignerdranch.drifting.Inviting.Loading.user_drawing_request.class);

        Call<user_drawing_request_return> call
                = user_drawing_request.request(Login_LoginActivity.getToken());

        call.enqueue(new Callback<user_drawing_request_return>() {
            @Override
            public void onResponse(@NonNull Call<user_drawing_request_return> call, @NonNull Response<user_drawing_request_return> response) {
                android.bignerdranch.drifting.Inviting.Loading.user_drawing_request_return request_return = response.body();
                Log.i("ActivityOnResponse",request_return.toString());
                Log.i("ActivityOnResponse",request_return.getData().toString());
                for(int i = 0;i < request_return.getData().size();i++){
                    long id = request_return.getData().get(i).getFile_id();
                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl("http://116.204.121.9:61583/")
                            .addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit = builder.build();
                    request request = retrofit.create(android.bignerdranch.drifting.Drawing.DrawingDetailRequest.request.class);
                    Call<android.bignerdranch.drifting.Drawing.DrawingDetailRequest.messageReturn> messageReturnCall = request.Request(Login_LoginActivity.getToken(),new request_body(id));
                    messageReturnCall.enqueue(new Callback<messageReturn>() {
                        @Override
                        public void onResponse(@NonNull Call<messageReturn> call, @NonNull Response<messageReturn> response) {

                            messageReturn messageReturn = response.body();
                            Log.i("onResponse",messageReturn.toString());
                        }

                        @Override
                        public void onFailure(Call<messageReturn> call, Throwable t) {
                            Toast.makeText(getContext(), "请求邀请数据失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void onFailure(@NonNull Call<user_drawing_request_return> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "获取邀请信息失败", Toast.LENGTH_SHORT).show();
            }
        });


         */

        return view;

    }

    private void updateUI(){
        Inviting_Lab invitingLab = Inviting_Lab.get(getActivity());
        List<Inviting_> inviting_list = invitingLab.getInvitings();
        mAdapter = new InvitingAdapter(inviting_list);
        mRecyclerView.setAdapter(mAdapter);
    }

    public interface ItemOnClickListener{
        public void onClick(View view,int i);
    }

    private class InvitingHolder extends RecyclerView.ViewHolder{
        private Inviting_ mInviting;
        private ImageButton mImageButton;
        private TextView mTextView;

        public InvitingHolder(LayoutInflater inflater,ViewGroup parent){
            super(inflater.inflate(R.layout.inviting_container,parent,false));

            mImageButton = (ImageButton) itemView.findViewById(R.id.inviting_link);
            mTextView = (TextView) itemView.findViewById(R.id.inviting_title);
        }

        public void bind(Inviting_ inviting){
            mInviting = inviting;
            int type = mInviting.getInviting_type();
            String inviting_type = "";
            switch (type){
                case 0:{
                    inviting_type = "书";
                    break;
                }
                case 1:{
                    inviting_type = "相机";
                    break;
                }
                case 2:{
                    inviting_type = "画";
                    break;
                }
                case 3:{
                    inviting_type = "小说";
                    break;
                }
            }

            mTextView.setText("来自"+mInviting.getInviter()+"的漂流"+inviting_type);
            mImageButton.setImageResource(R.drawable.login_interface);

            mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT).show();
                    switch (type){
                        case 0:{
                            //inviting_type = "书";
                            break;
                        }
                        case 1:{
                            //inviting_type = "相机";
                            break;
                        }
                        case 2:{
                            startActivity(new Intent(getContext(),Drawing_InvitingActivity.class));
                            break;
                        }
                        case 3:{
                            //inviting_type = "小说";
                            break;
                        }
                    }
                }
            });
        }

    }

    private class InvitingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private List<Inviting_> mInviting_list;

        public InvitingAdapter(List<Inviting_> inviting_list){
            mInviting_list = inviting_list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new InvitingHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Inviting_ inviting = mInviting_list.get(position);
            ((InvitingHolder)holder).bind(inviting);
        }

        @Override
        public int getItemCount() {
            return mInviting_list.size();
        }
        class MyViewHolder extends RecyclerView.ViewHolder{

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }


}
