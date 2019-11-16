package myproject.kehamilanku.fragment;


import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import myproject.kehamilanku.Kelas.TipsKehamilan;
import myproject.kehamilanku.R;
import myproject.kehamilanku.adapter.AdapterTipsKehamilan;
import myproject.kehamilanku.admin.AddTipsActivity;
import myproject.kehamilanku.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHomeAdmin extends BaseFragment {


    public FragmentHomeAdmin() {
        // Required empty public constructor
    }

    RecyclerView rvTipsKehamilan;
    FloatingActionButton btnCreate;
    AdapterTipsKehamilan adapter;
    List<TipsKehamilan> tipsKehamilanList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.fragment_home_admin, container, false);
        rvTipsKehamilan = view.findViewById(R.id.rvTipsKehamilan);
        btnCreate = view.findViewById(R.id.btnCreate);
        ref = firestore.collection("tipskehamilan");

        tipsKehamilanList = new ArrayList<>();
        adapter = new AdapterTipsKehamilan(getActivity(),tipsKehamilanList);

        rvTipsKehamilan.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTipsKehamilan.setHasFixedSize(true);
        rvTipsKehamilan.setItemAnimator(new DefaultItemAnimator());
        rvTipsKehamilan.setAdapter(adapter);


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTipsActivity.class);
                startActivity(intent);
            }
        });

        getDataTips(ref,tipsKehamilanList,adapter);

        return view;
    }

    public void reloadData(){
        getDataTips(ref,tipsKehamilanList,adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        reloadData();
    }
}
