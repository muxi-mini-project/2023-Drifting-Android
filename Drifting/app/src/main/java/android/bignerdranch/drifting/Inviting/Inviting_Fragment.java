package android.bignerdranch.drifting.Inviting;

import android.annotation.SuppressLint;
import android.bignerdranch.drifting.Inviting.Loading.user_drawing_request;
import android.bignerdranch.drifting.Inviting.Loading.user_drawing_request_return;
import android.bignerdranch.drifting.Login.Login_LoginActivity;
import android.bignerdranch.drifting.Main.Main_DiscoveringFragment;
import android.bignerdranch.drifting.Main.Main_MainActivity;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.User_Now;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Inviting_Fragment extends Fragment {
    private ArrayList<user_drawing_request_return.data> mData;

    private TextView detail;
    private String detailStr = "";
    private RecyclerView mRecyclerView;

    public static Inviting_Fragment newInstance() {
        return new Inviting_Fragment();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inviting_fragment, null);
        detail = (TextView) view.findViewById(R.id.inviting_detail);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.inviting_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);


        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://116.204.121.9:61583/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        android.bignerdranch.drifting.Inviting.Loading.user_drawing_request user_drawing_request
                = retrofit.create(android.bignerdranch.drifting.Inviting.Loading.user_drawing_request.class);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Call<user_drawing_request_return> call
                        = user_drawing_request.request(User_Now.getUserNow().getUser().getToken());
                call.enqueue(new Callback<user_drawing_request_return>() {
                    @Override
                    public void onResponse(@NonNull Call<user_drawing_request_return> call, @NonNull Response<user_drawing_request_return> response) {
                        user_drawing_request_return request_return = response.body();
                        if (response.body().getData() != null) {
                            mData = request_return.getData();
                            //0生1熟
                            int drawing_num0 = 0;
                            int drawing_num1 = 0;
                            int novel_num0 = 0;
                            int novel_num1 = 0;
                            int camera_num0 = 0;
                            int camera_num1 = 0;
                            int book_num0 = 0;
                            int book_num1 = 0;
                            for (int i = 0; i < mData.size(); i++) {
                                switch (mData.get(i).getFileKind()) {
                                    case "漂流画": {
                                        switch (mData.get(i).getKind()) {
                                            case 0: {
                                                drawing_num0++;
                                                break;
                                            }
                                            case 1: {
                                                drawing_num1++;
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                    case "漂流小说": {
                                        switch (mData.get(i).getKind()) {
                                            case 0: {
                                                novel_num0++;
                                                break;
                                            }
                                            case 1: {
                                                novel_num1++;
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                    case "漂流相机": {
                                        switch (mData.get(i).getKind()) {
                                            case 0: {
                                                camera_num0++;
                                                break;
                                            }
                                            case 1: {
                                                camera_num1++;
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                    case "漂流本": {
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
                                        break;
                                    }
                                }
                            }
                            if (drawing_num0 != 0) {
                                detailStr += "来自随机的漂流画    X" + drawing_num0 + "\n";
                            }
                            if (drawing_num1 != 0) {
                                detailStr += "来自密友的漂流画    X" + drawing_num1 + "\n";
                            }
                            if (novel_num0 != 0) {
                                detailStr += "来自随机的漂流小说  X" + novel_num0 + "\n";
                            }
                            if (novel_num1 != 0) {
                                detailStr += "来自密友的漂流小说  X" + novel_num1 + "\n";
                            }
                            if (camera_num0 != 0) {
                                detailStr += "来自随机的漂流相机  X" + camera_num0 + "\n";
                            }
                            if (camera_num1 != 0) {
                                detailStr += "来自密友的漂流相机  X" + camera_num1 + "\n";
                            }
                            if (book_num0 != 0) {
                                detailStr += "来自随机的漂流本  X" + novel_num0 + "\n";
                            }
                            if (book_num1 != 0) {
                                detailStr += "来自随机的漂流本  X" + novel_num1;
                            }
                        } else {
                            detailStr = "尚未收到邀请哦";
                        }

                        detail.setText(detailStr);
                        //Log.i("onResponse",request_return.toString());
                    }

                    @Override
                    public void onFailure(@NonNull Call<user_drawing_request_return> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), "获取邀请信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 1000);
        return view;

    }


}
