package com.example.drift;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.UUID;

public class gerenbianjiFragment extends Fragment {
    EditText mNameEdit;
    EditText mSignEdit;
    TextView mSexText;
    String name;
    String sign;
    User mUser;
    final String FRAGMENT_KEY = "id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = UserLab.get(getActivity()).getUser((UUID) getArguments().getSerializable(FRAGMENT_KEY));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_geren_bianji, container, false);
        mNameEdit = (EditText) view.findViewById(R.id.name_edit);
        mSignEdit = (EditText) view.findViewById(R.id.sign_edit);
        mSexText = (TextView) view.findViewById(R.id.sex_edit);
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
        return view;
    }
}