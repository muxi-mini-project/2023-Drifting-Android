package com.example.drift;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.UUID;

/**
 * 用于编辑个人资料的fragment
 */
public class gerenbianjiFragment extends Fragment {
    //正在进行以及已经结束面板
    RecyclerView mDoingNow;
    RecyclerView.Adapter DoingAdapter;
    RecyclerView mDoneAgo;
    RecyclerView.Adapter DoneAdapter;

    Button mZiliaoButton;
    EditText mNameEdit;
    EditText mSignEdit;
    TextView mSexText;
    User mUser;
    final String GET_USER = "user_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = UserLab.get(getActivity()).getUser((UUID) getArguments().getSerializable(GET_USER));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_geren_bianji, container, false);
        mNameEdit = (EditText) view.findViewById(R.id.name_edit);
        mSignEdit = (EditText) view.findViewById(R.id.sign_edit);
        mSexText = (TextView) view.findViewById(R.id.sex_edit);
        mZiliaoButton = (Button)view.findViewById(R.id.ziliao_button);
        mNameEdit.setText(mUser.getName());
        mSignEdit.setText(mUser.getSignature());
        if (mUser.isSex())
            mSexText.setText("性别:女");
        else
            mSexText.setText("性别:男");
        mNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mSignEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setSignature(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mZiliaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new gerenFragment();
                Bundle args = new Bundle();

                FragmentTransaction mTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                if (!fragment.isAdded()) {
                    mTransaction.add(R.id.main_container, fragment);
                    mTransaction.replace(R.id.main_container, fragment);
                } else
                    mTransaction.replace(R.id.main_container, fragment);
                mTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_CLOSE);
                args.putSerializable(GET_USER,mUser.getUUID());
                fragment.setArguments(args);
                mTransaction.commit();
            }
        });
        mDoingNow = (RecyclerView)view.findViewById(R.id.gerenjinduing_view);
        mDoingNow.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDoneAgo = (RecyclerView)view.findViewById(R.id.gerenjinduover_view);
        mDoneAgo.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;
    }
    public void updateUI() {
        if (DoingAdapter == null) {
            DoingAdapter = new gerenbianjiFragment.JinduAdaptering();//将案例组传给Adapter并创建Adapter
            mDoingNow.setAdapter(DoingAdapter);//将RecyclerView与Adapter绑定
            if (DoneAdapter == null) {
                DoneAdapter = new gerenbianjiFragment.JinduAdapterover();
                mDoneAgo.setAdapter(DoneAdapter);
            }
        }
    }
    /**
     * 以下为Adapter和Holder
     */
    private class JinduAdaptering extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final List<String> namesing = GetAllItems.get(mUser.getUUID(), getContext()).getAllNameunderway();
        private final List<Integer> nowusering = GetAllItems.get(mUser.getUUID(), getContext()).getAllnowuserunderway();
        private final List<Integer> Maxusering = GetAllItems.get(mUser.getUUID(), getContext()).getAllMaxuserunderway();
        private final List<Boolean> Ifusering = GetAllItems.get(mUser.getUUID(), getContext()).getAllIfuserunderway();
        public JinduAdaptering() {
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            if (viewType == 0)
                return new gerenbianjiFragment.JinduHolder(layoutInflater, parent);
            else
                return new gerenbianjiFragment.JinduHolder2(layoutInflater, parent);
        }

        @Override
        public int getItemViewType(int position) {
            return Ifusering.get(position) ? 1 : 0;
            //看ViewHolder的创建先后来定顺序。例如CrimeHolder先创建，0就代表CrimeHolder，1代表CrimeHolder2
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            String name = "《" + namesing.get(position) + "》";
            String number = nowusering.get(position) + "/" + Maxusering.get(position);
            if (holder instanceof gerenbianjiFragment.JinduHolder)
                ((gerenbianjiFragment.JinduHolder) holder).bind(name, number);
            else if (holder instanceof gerenbianjiFragment.JinduHolder2)
                ((gerenbianjiFragment.JinduHolder2) holder).bind(name, number);
        }

        @Override
        public int getItemCount() {
            return namesing.size();//RecyclerView通过调用该方法确定一共有多少个Fragment
        }

    }

    private class JinduAdapterover extends RecyclerView.Adapter<gerenbianjiFragment.JinduHolder> {
        private final List<String> namesover = GetAllItems.get(mUser.getUUID(), getContext()).getAllNameover();
        private final List<Integer> nowuserover = GetAllItems.get(mUser.getUUID(), getContext()).getAllnowuserover();
        private final List<Integer> Maxuserover = GetAllItems.get(mUser.getUUID(), getContext()).getAllMaxuserover();

        public JinduAdapterover() {
        }

        @NonNull
        @Override
        public gerenbianjiFragment.JinduHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new gerenbianjiFragment.JinduHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull gerenbianjiFragment.JinduHolder holder, int position) {
            String name = "《" + namesover.get(position) + "》";
            String number = nowuserover.get(position) + "/" + Maxuserover.get(position);
            holder.bind(name, number);
        }

        @Override
        public int getItemCount() {
            return namesover.size();//RecyclerView通过调用该方法确定一共有多少个Fragment
        }
    }
    private static class JinduHolder extends RecyclerView.ViewHolder {
        private TextView mJinduname;
        private TextView mJindurenshu;

        public JinduHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_gerenjindu, parent, false));
            mJinduname = (TextView) itemView.findViewById(R.id.jinduname_text);
            mJindurenshu = (TextView) itemView.findViewById(R.id.jindurenshu_text);
        }

        public void bind(String name, String number) {
            mJinduname.setText(name);
            mJindurenshu.setText(number);
        }
    }

    private class JinduHolder2 extends RecyclerView.ViewHolder {
        private TextView mJinduname;
        private TextView mJindurenshu;

        public JinduHolder2(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_gerenjindu2, parent, false));
            mJinduname = (TextView) itemView.findViewById(R.id.jinduname_text);
            mJindurenshu = (TextView) itemView.findViewById(R.id.jindurenshu_text);

        }

        public void bind(String name, String number) {
            mJinduname.setText(name);
            mJindurenshu.setText(number);
        }
    }
}