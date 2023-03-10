package android.bignerdranch.drifting.Camera;

import android.bignerdranch.drifting.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 选择模式
 */
public class Camera_fragment extends Fragment {
    Button mFriend;
    Button mStrang;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creat, container, false);
    mFriend = (Button) view.findViewById(R.id.acquaintance_mode);
    mStrang = (Button) view.findViewById(R.id.stranger_mode);
    mFriend.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(),Camera_Start.class);
            startActivity(intent);
        }
    });
    mStrang.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(),Camera_setting_stranger.class);
            startActivity(intent);
        }
    });
    return view;
    }
}
