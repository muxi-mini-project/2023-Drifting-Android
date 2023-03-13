package android.bignerdranch.drifting.Main;

import android.bignerdranch.drifting.Book.ApiNote;
import android.bignerdranch.drifting.Book.Book_DriftingBookActivity;
import android.bignerdranch.drifting.Book.Book_inviting;
import android.bignerdranch.drifting.Camera.Camera_Inviting;
import android.bignerdranch.drifting.Camera.Camera_create;
import android.bignerdranch.drifting.Drawing.Drawing_InvitingActivity;
import android.bignerdranch.drifting.Mine.User.User_Now;
import android.bignerdranch.drifting.detail_request.messageReturn;
import android.bignerdranch.drifting.detail_request.request;
import android.bignerdranch.drifting.detail_request.request_body;
import android.bignerdranch.drifting.Drawing.Drawing_switch_mode;
import android.bignerdranch.drifting.Inviting.Loading.inviting_request;
import android.bignerdranch.drifting.Inviting.Loading.inviting_messageReturn;
import android.bignerdranch.drifting.R;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
    private ArrayList<messageReturn> mMessageReturns = new ArrayList<>();
    private ArrayList<inviting_messageReturn.data> mDataArrayList = new ArrayList<>();
    private int numberLine = 0;//判断次数，
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discovering_fragment, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.discovering_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);


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
                Toast.makeText(requireActivity(), "开发中", Toast.LENGTH_SHORT).show();
            }
        });
        drifting_drawing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Drawing_switch_mode.class));
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
                    Log.i("ActivityOnResponse画", request_return.toString());
                    Log.i("ActivityOnResponse画", request_return.getData().toString());
                    for (int i = 0; i < request_return.getData().size(); i++) {
                        long id = request_return.getData().get(i).getFile_id();
                        request request = retrofit0.create(android.bignerdranch.drifting.detail_request.request.class);
                        Call<messageReturn> messageReturnCall = request.drawingRequest(User_Now.getUserNow().getUser().getToken(), new request_body(id));
                        int finalI = i;
                        messageReturnCall.enqueue(new Callback<messageReturn>() {
                            @Override
                            public void onResponse(@NonNull Call<messageReturn> call, @NonNull Response<messageReturn> response) {
                                Log.i("onResponse画", response.body().toString());
                                messageReturn messageReturn = response.body();
                                messageReturn.setZhonglei("漂流画");
                                if (request_return.getData().get(finalI).getKind() == 0) {
                                    messageReturn.setInviter("随机");
                                } else if (request_return.getData().get(finalI).getKind() == 1) {
                                    messageReturn.setInviter("密友");
                                }
                                mDataArrayList.add(request_return.getData().get(finalI));
                                Log.i("onResponse画", messageReturn.toString());
                                mMessageReturns.add(messageReturn);
                                updateUI();numberLine++;
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
                    Log.i("ActivityOnResponse本", request_return.toString());
                    Log.i("ActivityOnResponse本", request_return.getData().toString());
                    for (int i = 0; i < request_return.getData().size(); i++) {
                        long id = request_return.getData().get(i).getFile_id();//漂流项目id
                        request request = retrofit0.create(android.bignerdranch.drifting.detail_request.request.class);
                        Call<messageReturn> messageReturnCall = request.bookRequest(User_Now.getUserNow().getUser().getToken(), new request_body(id));
                        int finalI = i;
                        messageReturnCall.enqueue(new Callback<messageReturn>() {
                            @Override
                            public void onResponse(@NonNull Call<messageReturn> call, @NonNull Response<messageReturn> response) {
                                messageReturn messageReturn = response.body();//获得了当个漂流本内容的实例
                                messageReturn.setZhonglei("漂流本");
                                if (request_return.getData().get(finalI).getKind() == 0) {
                                    messageReturn.setInviter("随机");
                                } else if (request_return.getData().get(finalI).getKind() == 1) {
                                    messageReturn.setInviter("密友");
                                }
                                mDataArrayList.add(request_return.getData().get(finalI));
                                mMessageReturns.add(messageReturn);
                                Log.i("onResponse", mMessageReturns.toString());
                                updateUI();numberLine++;
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
                    Log.i("ActivityOnResponse小说", request_return.toString());
                    Log.i("ActivityOnResponse小说", request_return.getData().toString());
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
                                mDataArrayList.add(request_return.getData().get(finalI));
                                mMessageReturns.add(messageReturn);
                                Log.i("onResponse", mMessageReturns.toString());
                                updateUI();numberLine++;
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
                    Log.i("ActivityOnResponse相机", request_return.toString());
                    Log.i("ActivityOnResponse相机", request_return.getData().toString());
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
                                mDataArrayList.add(request_return.getData().get(finalI));
                                mMessageReturns.add(messageReturn);
                                Log.i("onResponse", mMessageReturns.toString());
                                updateUI();numberLine++;
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
        updateUI();

        /**
         *
         */

        Timer timer1 = new Timer();
        TimerTask timerTask1 = new TimerTask() {
            @Override
            public void run() {
               Log.i("次数",String.valueOf(numberLine));
               if (numberLine<10){
                   Random random2 = new Random();
                   for (int i = 0;i<(10-numberLine);i++){
                       int a = random2.nextInt(4); //a为0，1，2，3决定是哪一个漂流项目
                       Log.i("a",String.valueOf(a));
                       Retrofit.Builder builder = new Retrofit.Builder()
                               .baseUrl("http://116.204.121.9:61583/")
                               .addConverterFactory(GsonConverterFactory.create());
                       Retrofit retrofit = builder.build();
                       ApiNote random = retrofit.create(ApiNote.class);

                       switch (a){
                           case 0:{
                               Call<inviting_messageReturn.data> call0 = random.getRandomBook(User_Now
                                       .getUserNow().getUser().getToken());
                               call0.enqueue(new Callback<inviting_messageReturn.data>() {
                                   @Override
                                   public void onResponse(Call<inviting_messageReturn.data> call, Response<inviting_messageReturn.data> response) {
                                       inviting_messageReturn.data return_data =response.body();
                                       long id = return_data.getFile_id();
                                       request request1 = retrofit.create(android.bignerdranch.drifting.detail_request.request.class);
                                       Call<messageReturn> messageReturnCall = request1.bookRequest(User_Now.getUserNow().getUser().getToken(),
                                              new request_body(id));
                                       messageReturnCall.enqueue(new Callback<messageReturn>() {
                                           @Override
                                           public void onResponse(Call<messageReturn> call, Response<messageReturn> response) {
                                               messageReturn messageReturn = response.body();//获得了当个漂流本内容的实例
                                               messageReturn.setZhonglei("漂流本");
                                               if (return_data.getKind() == 0) {
                                                   messageReturn.setInviter("随机");
                                               } else if (return_data.getKind() == 1) {
                                                   messageReturn.setInviter("密友");
                                               }
                                               mDataArrayList.add(return_data);
                                               mMessageReturns.add(messageReturn);
                                               updateUI();

                                           }

                                           @Override
                                           public void onFailure(Call<messageReturn> call, Throwable t) {
                                               Toast.makeText(getContext(), "请求数据获得失败", Toast.LENGTH_SHORT).show();

                                           }
                                       });

                                   }

                                   @Override
                                   public void onFailure(Call<inviting_messageReturn.data> call, Throwable t) {
                                       Toast.makeText(getContext(), "请求数据获得失败", Toast.LENGTH_SHORT).show();

                                   }
                               });

                           }//漂流本
                           case 1:{
                               Call<inviting_messageReturn.data> call1 = random.getRandomPicture(User_Now
                                       .getUserNow().getUser().getToken());
                               call1.enqueue(new Callback<inviting_messageReturn.data>() {
                                   @Override
                                   public void onResponse(Call<inviting_messageReturn.data> call, Response<inviting_messageReturn.data> response) {
                                       inviting_messageReturn.data return_data = response.body();
                                       long id = return_data.getFile_id();
                                       request request1 = retrofit.create(android.bignerdranch.drifting.detail_request.request.class);
                                       Call<messageReturn> messageReturnCall = request1.cameraRequest(User_Now.getUserNow().getUser().getToken(),
                                               new request_body(id));
                                       messageReturnCall.enqueue(new Callback<messageReturn>() {
                                           @Override
                                           public void onResponse(Call<messageReturn> call, Response<messageReturn> response) {
                                               messageReturn messageReturn = response.body();//获得了当个漂流本内容的实例
                                               messageReturn.setZhonglei("漂流相机");
                                               if (return_data.getKind() == 0) {
                                                   messageReturn.setInviter("随机");
                                               } else if (return_data.getKind() == 1) {
                                                   messageReturn.setInviter("密友");
                                               }
                                               mDataArrayList.add(return_data);
                                               mMessageReturns.add(messageReturn);
                                               updateUI();
                                           }

                                           @Override
                                           public void onFailure(Call<messageReturn> call, Throwable t) {
                                               Toast.makeText(getContext(), "请求数据获得失败", Toast.LENGTH_SHORT).show();

                                           }
                                       });
                                   }

                                   @Override
                                   public void onFailure(Call<inviting_messageReturn.data> call, Throwable t) {
                                       Toast.makeText(getContext(), "请求数据获得失败", Toast.LENGTH_SHORT).show();

                                   }
                               });

                           }//相机
                           case 2:{
                               Call<inviting_messageReturn.data> call2 = random.getRandomDraw(User_Now
                                       .getUserNow().getUser().getToken());
                               call2.enqueue(new Callback<inviting_messageReturn.data>() {
                                   @Override
                                   public void onResponse(Call<inviting_messageReturn.data> call, Response<inviting_messageReturn.data> response) {
                                       inviting_messageReturn.data return_data = response.body();
                                       long id = return_data.getFile_id();
                                       request request1 = retrofit.create(android.bignerdranch.drifting.detail_request.request.class);
                                       Call<messageReturn> messageReturnCall = request1.drawingRequest(User_Now.getUserNow().getUser().getToken(),
                                               new request_body(id));
                                       messageReturnCall.enqueue(new Callback<messageReturn>() {
                                           @Override
                                           public void onResponse(Call<messageReturn> call, Response<messageReturn> response) {
                                               messageReturn messageReturn = response.body();//获得了当个漂流本内容的实例
                                               messageReturn.setZhonglei("漂流画");
                                               if (return_data.getKind() == 0) {
                                                   messageReturn.setInviter("随机");
                                               } else if (return_data.getKind() == 1) {
                                                   messageReturn.setInviter("密友");
                                               }
                                               mDataArrayList.add(return_data);
                                               mMessageReturns.add(messageReturn);
                                               updateUI();
                                           }

                                           @Override
                                           public void onFailure(Call<messageReturn> call, Throwable t) {
                                               Toast.makeText(getContext(), "请求数据获得失败", Toast.LENGTH_SHORT).show();

                                           }
                                       });
                                   }

                                   @Override
                                   public void onFailure(Call<inviting_messageReturn.data> call, Throwable t) {
                                       Toast.makeText(getContext(), "请求数据获得失败", Toast.LENGTH_SHORT).show();

                                   }
                               });
                           }//
                           case 3:{
                               Call<inviting_messageReturn.data> call3 = random.getRandomNovel(User_Now
                                       .getUserNow().getUser().getToken());
                               call3.enqueue(new Callback<inviting_messageReturn.data>() {
                                   @Override
                                   public void onResponse(Call<inviting_messageReturn.data> call, Response<inviting_messageReturn.data> response) {
                                       inviting_messageReturn.data return_data = response.body();
                                       long id = return_data.getFile_id();
                                       request request1 = retrofit.create(android.bignerdranch.drifting.detail_request.request.class);
                                       Call<messageReturn> messageReturnCall = request1.novelRequest(User_Now.getUserNow().getUser().getToken(),
                                               new request_body(id));
                                       messageReturnCall.enqueue(new Callback<messageReturn>() {
                                           @Override
                                           public void onResponse(Call<messageReturn> call, Response<messageReturn> response) {
                                               messageReturn messageReturn = response.body();//获得了当个漂流本内容的实例
                                               messageReturn.setZhonglei("漂流小说");
                                               if (return_data.getKind() == 0) {
                                                   messageReturn.setInviter("随机");
                                               } else if (return_data.getKind() == 1) {
                                                   messageReturn.setInviter("密友");
                                               }
                                               mDataArrayList.add(return_data);
                                               mMessageReturns.add(messageReturn);
                                               updateUI();
                                           }

                                           @Override
                                           public void onFailure(Call<messageReturn> call, Throwable t) {
                                               Toast.makeText(getContext(), "请求数据获得失败", Toast.LENGTH_SHORT).show();

                                           }
                                       });
                                   }

                                   @Override
                                   public void onFailure(Call<inviting_messageReturn.data> call, Throwable t) {
                                       Toast.makeText(getContext(), "请求数据获得失败", Toast.LENGTH_SHORT).show();

                                   }
                               });
                           }
                       }//选项结束

                   }

               }

            }
        };
        timer1.schedule(timerTask1,1000);
        return view;

    }

    private void updateUI() {
        mAdapter = new InvitingAdapter(mMessageReturns,mDataArrayList);
        mRecyclerView.setAdapter(mAdapter);


    }

    private class InvitingHolder extends RecyclerView.ViewHolder {
        private messageReturn mInviting;
        private inviting_messageReturn.data mData;
        private ImageButton mImageButton;
        private TextView mTextView;

        public InvitingHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.inviting_container, parent, false));

            mImageButton = (ImageButton) itemView.findViewById(R.id.inviting_link);
            mTextView = (TextView) itemView.findViewById(R.id.inviting_title);
        }

        public void bind(messageReturn inviting,inviting_messageReturn.data inviting_messageReturn) {
            mInviting = inviting;
            mData =  inviting_messageReturn;
            String type = mInviting.getZhonglei();
            String inviter = mInviting.getInviter();
            if (mData.getCover() != null){
                if (mData.getCover().contains("mini-project.muxixyz.com/drifting/covers/点构图.jpg"))
                    mImageButton.setImageResource(R.drawable.cover_1);
                else if (mData.getCover().contains("mini-project.muxixyz.com/drifting/covers/抓拍，广场上玩滑板的少年.jpg"))
                    mImageButton.setImageResource(R.drawable.cover_2);
                else if (mData.getCover().contains("mini-project.muxixyz.com/drifting/covers/三分线，冷清的广场.jpg"))
                    mImageButton.setImageResource(R.drawable.cover_3);
                else if (mData.getCover().contains("mini-project.muxixyz.com/drifting/covers/三分线构图.jpg"))
                    mImageButton.setImageResource(R.drawable.cover_4);
                else if (mData.getCover().contains("mini-project.muxixyz.com/drifting/covers/4.jpg"))
                    mImageButton.setImageResource(R.drawable.cover_5);
                else if (mData.getCover().contains("mini-project.muxixyz.com/drifting/covers/3.jpg"))
                    mImageButton.setImageResource(R.drawable.cover_6);
                else if (mData.getCover().contains("mini-project.muxixyz.com/drifting/covers/2.jpg"))
                    mImageButton.setImageResource(R.drawable.cover_7);
                else if (mData.getCover().contains("mini-project.muxixyz.com/drifting/covers/1.jpg"))
                    mImageButton.setImageResource(R.drawable.cover_8);
                else mImageButton.setImageResource(R.drawable.cover_8);
            }else mImageButton.setImageResource(R.drawable.cover_7);


            mTextView.setText("来自" + inviter + "的" + type);;
            mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (type) {
                        case "漂流本": {
                            Intent intent = new Intent(getContext(), Book_inviting.class);
                            intent.putExtra("file_id",String.valueOf(mData.getFile_id()));
                            startActivity(intent);
                            //inviting_type = "书";
                            break;
                        }
                        case "漂流相机": {
                            Intent intent = new Intent(getContext(), Camera_Inviting.class);
                            intent.putExtra("file_id",String.valueOf(mData.getFile_id()));
                            startActivity(intent);
                            break;
                        }
                        case "漂流画": {
                            startActivity(new Intent(getContext(), Drawing_InvitingActivity.class));
                            break;
                        }
                        case "漂流小说": {
                            //inviting_type = "小说";
                            break;
                        }
                    }
                }
            });
        }

    }

    private class InvitingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private ArrayList<messageReturn> mInviting_list;
        private ArrayList<inviting_messageReturn.data> mInviting_messageReturns; //

        public InvitingAdapter(ArrayList<messageReturn> inviting_list, ArrayList<inviting_messageReturn.data> data){
            mInviting_list = inviting_list;
            mInviting_messageReturns =data;
        }
        public InvitingAdapter(ArrayList<messageReturn> inviting_list)
        {
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
            inviting_messageReturn.data  data = mInviting_messageReturns.get(position);
            ((InvitingHolder)holder).bind(inviting,data);
        }

        @Override
        public int getItemCount() {
            return mInviting_list.size();
        }
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
