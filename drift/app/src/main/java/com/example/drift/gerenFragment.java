package com.example.drift;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

public class gerenFragment extends Fragment {
    TextView mSignText;
    TextView mSexText;
    TextView mNameText;
    User mUser;
    final String FRAGMENT_KEY = "id";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID mUUID = (UUID)getArguments().getSerializable(FRAGMENT_KEY);
        mUser = UserLab.get(getActivity()).getUser(mUUID);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
           View view = inflater.inflate(R.layout.fragment_geren, container, false);

        mNameText = (TextView) view.findViewById(R.id.name_text);
        mSexText = (TextView) view.findViewById(R.id.sex_text);
        mSignText = (TextView) view.findViewById(R.id.sign_text);
        mNameText.setText(mUser.getName());
        if (mUser.isSex())
            mSexText.setText("性别:女");
        else
            mSexText.setText("性别:男");
        mSignText.setText( mUser.getSignature());
        return view;
    }
}