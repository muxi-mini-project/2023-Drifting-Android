package android.bignerdranch.drifting.Main;

import android.bignerdranch.drifting.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class Main_MineFragment extends Fragment {
    public static Main_MineFragment newInstance(){
        return new Main_MineFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mine_fragment, container, false);
    }


}
