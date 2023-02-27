package android.bignerdranch.drifting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 *
 */
public class Inviting_Fragment extends Fragment {
    public static Inviting_Fragment newInstance(){
        return new Inviting_Fragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inviting_fragment, container, false);
    }


}
