package android.bignerdranch.drifting.Main;

import android.bignerdranch.drifting.Book.Book_DriftingBookActivity;
import android.bignerdranch.drifting.Book.Book_InvitingActivity;
import android.bignerdranch.drifting.Camera.Camera_InvitingActivity;
import android.bignerdranch.drifting.Camera.Camera_create;
import android.bignerdranch.drifting.Drawing.Drawing_InvitingActivity;
import android.bignerdranch.drifting.Drawing.Drawing_switch_mode;
import android.bignerdranch.drifting.Inviting.Loading.inviting_messageReturn;
import android.bignerdranch.drifting.Inviting.Loading.inviting_request;
import android.bignerdranch.drifting.Novel.Novel_InvitingActivity;
import android.bignerdranch.drifting.Novel.Novel_switch_mode;
import android.bignerdranch.drifting.R;
import android.bignerdranch.drifting.User.User_Now;
import android.bignerdranch.drifting.detail_request.messageReturn;
import android.bignerdranch.drifting.detail_request.request;
import android.bignerdranch.drifting.detail_request.request_body;
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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main_DiscoveringFragment extends Fragment {
    public static Main_DiscoveringFragment newInstance() {
        return new Main_DiscoveringFragment();
    }

    private RecyclerView mRecyclerView;
    private InvitingAdapter mAdapter;
    private static ArrayList<messageReturn> mMessageReturns = new ArrayList<>();
    public static ArrayList<messageReturn> getMessageReturns() {
        return mMessageReturns;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discovering_fragment, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.discovering_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ImageButton refresh = (ImageButton) view.findViewById(R.id.refresh);

        mMessageReturns.clear();
        //线性布局视图绑定
        ConstraintLayout drifting_book = (ConstraintLayout) view.findViewById(R.id.drifting_book);
        ConstraintLayout drifting_camera = (ConstraintLayout) view.findViewById(R.id.drifting_camera);
        ConstraintLayout drifting_novel = (ConstraintLayout) view.findViewById(R.id.drifting_novel);
        ConstraintLayout drifting_drawing = (ConstraintLayout) view.findViewById(R.id.drifting_drawing);

        //线性布局视图点击响应
        drifting_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Book_DriftingBookActivity.class));
            }
        });
        drifting_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Camera_create.class));
            }
        });
        drifting_novel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Novel_switch_mode.class));
            }
        });
        drifting_drawing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Drawing_switch_mode.class));
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageReturns.clear();
                Retrofit.Builder builder0 = new Retrofit.Builder()
                        .baseUrl("http://116.204.121.9:61583/")
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit0 = builder0.build();
                inviting_request request = retrofit0.create(inviting_request.class);
                //漂流画请求
                Call<inviting_messageReturn> call0 = request.drawing_request(User_Now.getUserNow().getUser().getToken());
                call0.enqueue(new Callback<inviting_messageReturn>() {
                    @Override
                    public void onResponse(@NonNull Call<inviting_messageReturn> call, @NonNull Response<inviting_messageReturn> response) {
                        inviting_messageReturn request_return = response.body();
                        if (request_return.getData() != null) {
                            Log.i("ActivityOnResponse", request_return.toString());
                            Log.i("ActivityOnResponse", request_return.getData().toString());
                            for (int i = 0; i < request_return.getData().size(); i++) {
                                long id = request_return.getData().get(i).getFile_id();
                                request request = retrofit0.create(android.bignerdranch.drifting.detail_request.request.class);
                                Call<messageReturn> messageReturnCall = request.drawingRequest(User_Now.getUserNow().getUser().getToken(), new request_body(id));
                                int finalI = i;
                                messageReturnCall.enqueue(new Callback<messageReturn>() {
                                    @Override
                                    public void onResponse(@NonNull Call<messageReturn> call, @NonNull Response<messageReturn> response) {
                                        Log.i("onResponse", response.body().toString());
                                        messageReturn messageReturn = response.body();
                                        messageReturn.setZhonglei("漂流画");
                                        if (request_return.getData().get(finalI).getKind() == 0) {
                                            messageReturn.setInviter("随机");
                                        } else if (request_return.getData().get(finalI).getKind() == 1) {
                                            messageReturn.setInviter("密友");
                                        }
                                        messageReturn.setId(request_return.getData().get(finalI).getFile_id());
                                        Log.i("onResponse", messageReturn.toString());
                                        mMessageReturns.add(messageReturn);
                                        updateUI();
                                    }

                                    @Override
                                    public void onFailure(Call<messageReturn> call, Throwable t) {
                                        Toast.makeText(getContext(), "请求邀请数据失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<inviting_messageReturn> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), "获取邀请信息失败", Toast.LENGTH_SHORT).show();
                    }
                });

                //漂流本请求
                Call<inviting_messageReturn> call1 = request.book_request(User_Now.getUserNow().getUser().getToken());
                call1.enqueue(new Callback<inviting_messageReturn>() {
                    @Override
                    public void onResponse(@NonNull Call<inviting_messageReturn> call, @NonNull Response<inviting_messageReturn> response) {
                        inviting_messageReturn request_return = response.body();
                        if (request_return.getData() != null) {
                            Log.i("ActivityOnResponse", request_return.toString());
                            Log.i("ActivityOnResponse", request_return.getData().toString());
                            for (int i = 0; i < request_return.getData().size(); i++) {
                                long id = request_return.getData().get(i).getFile_id();
                                request request = retrofit0.create(android.bignerdranch.drifting.detail_request.request.class);
                                Call<messageReturn> messageReturnCall = request.bookRequest(User_Now.getUserNow().getUser().getToken(), new request_body(id));
                                int finalI = i;
                                messageReturnCall.enqueue(new Callback<messageReturn>() {
                                    @Override
                                    public void onResponse(@NonNull Call<messageReturn> call, @NonNull Response<messageReturn> response) {
                                        messageReturn messageReturn = response.body();
                                        messageReturn.setZhonglei("漂流本");
                                        if (request_return.getData().get(finalI).getKind() == 0) {
                                            messageReturn.setInviter("随机");
                                        } else if (request_return.getData().get(finalI).getKind() == 1) {
                                            messageReturn.setInviter("密友");
                                        }
                                        messageReturn.setId(request_return.getData().get(finalI).getFile_id());
                                        mMessageReturns.add(messageReturn);
                                        Log.i("onResponse", mMessageReturns.toString());
                                        updateUI();
                                    }

                                    @Override
                                    public void onFailure(Call<messageReturn> call, Throwable t) {
                                        Toast.makeText(getContext(), "请求邀请数据失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }


                    }

                    @Override
                    public void onFailure(@NonNull Call<inviting_messageReturn> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), "获取邀请信息失败", Toast.LENGTH_SHORT).show();
                    }
                });

                //漂流小说请求
                Call<inviting_messageReturn> call2 = request.novel_request(User_Now.getUserNow().getUser().getToken());
                call2.enqueue(new Callback<inviting_messageReturn>() {
                    @Override
                    public void onResponse(@NonNull Call<inviting_messageReturn> call, @NonNull Response<inviting_messageReturn> response) {
                        inviting_messageReturn request_return = response.body();
                        if (request_return.getData() != null) {
                            Log.i("ActivityOnResponse", request_return.toString());
                            Log.i("ActivityOnResponse", request_return.getData().toString());
                            for (int i = 0; i < request_return.getData().size(); i++) {
                                long id = request_return.getData().get(i).getFile_id();
                                request request = retrofit0.create(android.bignerdranch.drifting.detail_request.request.class);
                                Call<messageReturn> messageReturnCall = request.novelRequest(User_Now.getUserNow().getUser().getToken(), new request_body(id));
                                int finalI = i;
                                messageReturnCall.enqueue(new Callback<messageReturn>() {
                                    @Override
                                    public void onResponse(@NonNull Call<messageReturn> call, @NonNull Response<messageReturn> response) {
                                        messageReturn messageReturn = response.body();
                                        messageReturn.setZhonglei("漂流小说");
                                        if (request_return.getData().get(finalI).getKind() == 0) {
                                            messageReturn.setInviter("随机");
                                        } else if (request_return.getData().get(finalI).getKind() == 1) {
                                            messageReturn.setInviter("密友");
                                        }
                                        messageReturn.setId(request_return.getData().get(finalI).getFile_id());
                                        mMessageReturns.add(messageReturn);
                                        Log.i("onResponse", mMessageReturns.toString());
                                        updateUI();
                                    }

                                    @Override
                                    public void onFailure(Call<messageReturn> call, Throwable t) {
                                        Toast.makeText(getContext(), "请求邀请数据失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<inviting_messageReturn> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), "获取邀请信息失败", Toast.LENGTH_SHORT).show();
                    }
                });

                //漂流相机请求
                Call<inviting_messageReturn> call3 = request.camera_request(User_Now.getUserNow().getUser().getToken());
                call3.enqueue(new Callback<inviting_messageReturn>() {
                    @Override
                    public void onResponse(@NonNull Call<inviting_messageReturn> call, @NonNull Response<inviting_messageReturn> response) {
                        inviting_messageReturn request_return = response.body();
                        if (request_return.getData() != null) {
                            Log.i("ActivityOnResponse", request_return.toString());
                            Log.i("ActivityOnResponse", request_return.getData().toString());
                            for (int i = 0; i < request_return.getData().size(); i++) {
                                long id = request_return.getData().get(i).getFile_id();
                                request request = retrofit0.create(android.bignerdranch.drifting.detail_request.request.class);
                                Call<messageReturn> messageReturnCall = request.cameraRequest(User_Now.getUserNow().getUser().getToken(), new request_body(id));
                                int finalI = i;
                                messageReturnCall.enqueue(new Callback<messageReturn>() {
                                    @Override
                                    public void onResponse(@NonNull Call<messageReturn> call, @NonNull Response<messageReturn> response) {
                                        messageReturn messageReturn = response.body();
                                        messageReturn.setZhonglei("漂流相机");
                                        if (request_return.getData().get(finalI).getKind() == 0) {
                                            messageReturn.setInviter("随机");
                                        } else if (request_return.getData().get(finalI).getKind() == 1) {
                                            messageReturn.setInviter("密友");
                                        }
                                        messageReturn.setId(request_return.getData().get(finalI).getFile_id());
                                        mMessageReturns.add(messageReturn);
                                        Log.i("onResponse", mMessageReturns.toString());
                                        updateUI();
                                    }

                                    @Override
                                    public void onFailure(Call<messageReturn> call, Throwable t) {
                                        Toast.makeText(getContext(), "请求邀请数据失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<inviting_messageReturn> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), "获取邀请信息失败", Toast.LENGTH_SHORT).show();
                    }
                });
                if(mAdapter != null){
                    mAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getContext(), "暂无新邀请哦", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //获取漂流计划内容
        Retrofit.Builder builder0 = new Retrofit.Builder()
                .baseUrl("http://116.204.121.9:61583/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit0 = builder0.build();
        inviting_request request = retrofit0.create(inviting_request.class);

        //漂流画请求
        Call<inviting_messageReturn> call0 = request.drawing_request(User_Now.getUserNow().getUser().getToken());
        call0.enqueue(new Callback<inviting_messageReturn>() {
            @Override
            public void onResponse(@NonNull Call<inviting_messageReturn> call, @NonNull Response<inviting_messageReturn> response) {
                inviting_messageReturn request_return = response.body();
                if (request_return.getData() != null) {
                    Log.i("ActivityOnResponse", request_return.toString());
                    Log.i("ActivityOnResponse", request_return.getData().toString());
                    for (int i = 0; i < request_return.getData().size(); i++) {
                        long id = request_return.getData().get(i).getFile_id();
                        request request = retrofit0.create(android.bignerdranch.drifting.detail_request.request.class);
                        Call<messageReturn> messageReturnCall = request.drawingRequest(User_Now.getUserNow().getUser().getToken(), new request_body(id));
                        int finalI = i;
                        messageReturnCall.enqueue(new Callback<messageReturn>() {
                            @Override
                            public void onResponse(@NonNull Call<messageReturn> call, @NonNull Response<messageReturn> response) {
                                Log.i("onResponse", response.body().toString());
                                messageReturn messageReturn = response.body();
                                messageReturn.setZhonglei("漂流画");
                                if (request_return.getData().get(finalI).getKind() == 0) {
                                    messageReturn.setInviter("随机");
                                } else if (request_return.getData().get(finalI).getKind() == 1) {
                                    messageReturn.setInviter("密友");
                                }
                                messageReturn.setId(request_return.getData().get(finalI).getFile_id());
                                Log.i("onResponse", messageReturn.toString());
                                mMessageReturns.add(messageReturn);
                                updateUI();
                            }

                            @Override
                            public void onFailure(Call<messageReturn> call, Throwable t) {
                                Toast.makeText(getContext(), "请求邀请数据失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<inviting_messageReturn> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "获取邀请信息失败", Toast.LENGTH_SHORT).show();
            }
        });

        //漂流本请求
        Call<inviting_messageReturn> call1 = request.book_request(User_Now.getUserNow().getUser().getToken());
        call1.enqueue(new Callback<inviting_messageReturn>() {
            @Override
            public void onResponse(@NonNull Call<inviting_messageReturn> call, @NonNull Response<inviting_messageReturn> response) {
                inviting_messageReturn request_return = response.body();
                if (request_return.getData() != null) {
                    Log.i("ActivityOnResponse", request_return.toString());
                    Log.i("ActivityOnResponse", request_return.getData().toString());
                    for (int i = 0; i < request_return.getData().size(); i++) {
                        long id = request_return.getData().get(i).getFile_id();
                        request request = retrofit0.create(android.bignerdranch.drifting.detail_request.request.class);
                        Call<messageReturn> messageReturnCall = request.bookRequest(User_Now.getUserNow().getUser().getToken(), new request_body(id));
                        int finalI = i;
                        messageReturnCall.enqueue(new Callback<messageReturn>() {
                            @Override
                            public void onResponse(@NonNull Call<messageReturn> call, @NonNull Response<messageReturn> response) {
                                messageReturn messageReturn = response.body();
                                messageReturn.setZhonglei("漂流本");
                                if (request_return.getData().get(finalI).getKind() == 0) {
                                    messageReturn.setInviter("随机");
                                } else if (request_return.getData().get(finalI).getKind() == 1) {
                                    messageReturn.setInviter("密友");
                                }
                                messageReturn.setId(request_return.getData().get(finalI).getFile_id());
                                mMessageReturns.add(messageReturn);
                                Log.i("onResponse", mMessageReturns.toString());
                                updateUI();
                            }

                            @Override
                            public void onFailure(Call<messageReturn> call, Throwable t) {
                                Toast.makeText(getContext(), "请求邀请数据失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }


            }

            @Override
            public void onFailure(@NonNull Call<inviting_messageReturn> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "获取邀请信息失败", Toast.LENGTH_SHORT).show();
            }
        });

        //漂流小说请求
        Call<inviting_messageReturn> call2 = request.novel_request(User_Now.getUserNow().getUser().getToken());
        call2.enqueue(new Callback<inviting_messageReturn>() {
            @Override
            public void onResponse(@NonNull Call<inviting_messageReturn> call, @NonNull Response<inviting_messageReturn> response) {
                inviting_messageReturn request_return = response.body();
                if (request_return.getData() != null) {
                    Log.i("ActivityOnResponse", request_return.toString());
                    Log.i("ActivityOnResponse", request_return.getData().toString());
                    for (int i = 0; i < request_return.getData().size(); i++) {
                        long id = request_return.getData().get(i).getFile_id();
                        request request = retrofit0.create(android.bignerdranch.drifting.detail_request.request.class);
                        Call<messageReturn> messageReturnCall = request.novelRequest(User_Now.getUserNow().getUser().getToken(), new request_body(id));
                        int finalI = i;
                        messageReturnCall.enqueue(new Callback<messageReturn>() {
                            @Override
                            public void onResponse(@NonNull Call<messageReturn> call, @NonNull Response<messageReturn> response) {
                                messageReturn messageReturn = response.body();
                                messageReturn.setZhonglei("漂流小说");
                                if (request_return.getData().get(finalI).getKind() == 0) {
                                    messageReturn.setInviter("随机");
                                } else if (request_return.getData().get(finalI).getKind() == 1) {
                                    messageReturn.setInviter("密友");
                                }
                                messageReturn.setId(request_return.getData().get(finalI).getFile_id());
                                mMessageReturns.add(messageReturn);
                                Log.i("onResponse", mMessageReturns.toString());
                                updateUI();
                            }

                            @Override
                            public void onFailure(Call<messageReturn> call, Throwable t) {
                                Toast.makeText(getContext(), "请求邀请数据失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<inviting_messageReturn> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "获取邀请信息失败", Toast.LENGTH_SHORT).show();
            }
        });

        //漂流相机请求
        Call<inviting_messageReturn> call3 = request.camera_request(User_Now.getUserNow().getUser().getToken());
        call3.enqueue(new Callback<inviting_messageReturn>() {
            @Override
            public void onResponse(@NonNull Call<inviting_messageReturn> call, @NonNull Response<inviting_messageReturn> response) {
                inviting_messageReturn request_return = response.body();
                if (request_return.getData() != null) {
                    Log.i("ActivityOnResponse", request_return.toString());
                    Log.i("ActivityOnResponse", request_return.getData().toString());
                    for (int i = 0; i < request_return.getData().size(); i++) {
                        long id = request_return.getData().get(i).getFile_id();
                        request request = retrofit0.create(android.bignerdranch.drifting.detail_request.request.class);
                        Call<messageReturn> messageReturnCall = request.cameraRequest(User_Now.getUserNow().getUser().getToken(), new request_body(id));
                        int finalI = i;
                        messageReturnCall.enqueue(new Callback<messageReturn>() {
                            @Override
                            public void onResponse(@NonNull Call<messageReturn> call, @NonNull Response<messageReturn> response) {
                                messageReturn messageReturn = response.body();
                                messageReturn.setZhonglei("漂流相机");
                                if (request_return.getData().get(finalI).getKind() == 0) {
                                    messageReturn.setInviter("随机");
                                } else if (request_return.getData().get(finalI).getKind() == 1) {
                                    messageReturn.setInviter("密友");
                                }
                                messageReturn.setId(request_return.getData().get(finalI).getFile_id());
                                mMessageReturns.add(messageReturn);
                                Log.i("onResponse", mMessageReturns.toString());
                                updateUI();
                            }

                            @Override
                            public void onFailure(Call<messageReturn> call, Throwable t) {
                                Toast.makeText(getContext(), "请求邀请数据失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<inviting_messageReturn> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "获取邀请信息失败", Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }

    private void updateUI() {
        mAdapter = new InvitingAdapter(mMessageReturns);
        mRecyclerView.setAdapter(mAdapter);
    }

    private class InvitingHolder extends RecyclerView.ViewHolder {
        private messageReturn mInviting;
        private ImageButton mImageButton;
        private TextView mTextView;

        public InvitingHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.inviting_container, parent, false));

            mImageButton = (ImageButton) itemView.findViewById(R.id.inviting_link);
            mTextView = (TextView) itemView.findViewById(R.id.inviting_title);
        }

        public void bind(messageReturn inviting) {
            mInviting = inviting;
            String type = mInviting.getZhonglei();
            String inviter = mInviting.getInviter();

            mTextView.setText("来自" + inviter + "的" + type);
            mImageButton.setImageResource(R.drawable.login_interface);
            mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (type) {
                        case "漂流本": {
                            Intent intent = new Intent(getContext(), Book_InvitingActivity.class);
                            intent.putExtra("file_id",mInviting.getId());
                            intent.putExtra("position",mInviting.getPosition());
                            startActivity(intent);
                            break;
                        }
                        case "漂流相机": {
                            Intent intent = new Intent(getContext(), Camera_InvitingActivity.class);
                            intent.putExtra("file_id",mInviting.getId());
                            intent.putExtra("position",mInviting.getPosition());
                            startActivity(intent);
                            break;
                        }
                        case "漂流画": {
                            Intent intent = new Intent(getContext(),Drawing_InvitingActivity.class);
                            intent.putExtra("file_id",mInviting.getId());
                            intent.putExtra("position",mInviting.getPosition());
                            startActivity(intent);
                            break;
                        }
                        case "漂流小说": {
                            Intent intent = new Intent(getContext(), Novel_InvitingActivity.class);
                            intent.putExtra("file_id",mInviting.getId());
                            intent.putExtra("position",mInviting.getPosition());
                            startActivity(intent);
                            break;
                        }
                    }
                }
            });
        }

    }

    private class InvitingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private ArrayList<messageReturn> mInviting_list;

        public InvitingAdapter(ArrayList<messageReturn> inviting_list) {
            mInviting_list = inviting_list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new InvitingHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            messageReturn inviting = mInviting_list.get(position);
            inviting.setPosition(position);
            ((InvitingHolder) holder).bind(inviting);
        }

        @Override
        public int getItemCount() {
            return mInviting_list.size();
        }
    }
}
