package myproject.kehamilanku.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import myproject.kehamilanku.R;
import myproject.kehamilanku.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTipsKehamilan extends BaseFragment {


    public FragmentTipsKehamilan() {
        // Required empty public constructor
    }

    RecyclerView rvTipsKehamilan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_tips, container, false);

        rvTipsKehamilan = view.findViewById(R.id.rvTipsKehamilan);


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

    }
}
