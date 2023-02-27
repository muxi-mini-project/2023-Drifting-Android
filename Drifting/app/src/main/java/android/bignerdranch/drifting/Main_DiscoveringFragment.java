package android.bignerdranch.drifting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * 主界面
 * 发现界面
 */
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
        LinearLayout drifting_book = (LinearLayout) view.findViewById(R.id.drifting_book);
        LinearLayout drifting_camera = (LinearLayout) view.findViewById(R.id.drifting_camera);
        LinearLayout drifting_novel = (LinearLayout) view.findViewById(R.id.drifting_novel);
        LinearLayout drifting_drawing = (LinearLayout) view.findViewById(R.id.drifting_drawing);

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
                startActivity(new Intent(getContext(), Camera_inviting.class));
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
                Toast.makeText(requireActivity(),"开发中",Toast.LENGTH_SHORT).show();
            }
        });

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

    private class InvitingHolder extends RecyclerView.ViewHolder {
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

    }


}
