package myproject.kehamilanku.fragment;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import myproject.kehamilanku.R;
import myproject.kehamilanku.activity.ListTipsKehamilan;
import myproject.kehamilanku.activity.ListVideoSenamActivity;
import myproject.kehamilanku.activity.ProfilActivity;
import myproject.kehamilanku.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHomeUser extends BaseFragment {


    public FragmentHomeUser() {
        // Required empty public constructor
    }

    RelativeLayout rlTipsKehamilan,rlKembangJanin,rlProfil,rlVideo,rlPetugas;
    public static android.app.AlertDialog dialog;
    TextView tvNama,tvAlamat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        rlTipsKehamilan = view.findViewById(R.id.rlTipsKehamilan);
        rlKembangJanin = view.findViewById(R.id.rlKembangJanin);
        rlProfil = view.findViewById(R.id.rlProfil);
        rlVideo = view.findViewById(R.id.rlVideo);
        rlPetugas = view.findViewById(R.id.rlPetugas);
        tvNama = view.findViewById(R.id.tvNama);
        tvAlamat = view.findViewById(R.id.tvAlamat);

        rlTipsKehamilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListTipsKehamilan.class);
                startActivity(intent);
            }
        });
        rlVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListVideoSenamActivity.class);
                startActivity(intent);
            }
        });
        rlProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfilActivity.class);
                startActivity(intent);
            }
        });

        setView();

        return view;
    }

    private void setView(){
        if (mUserPref.getNama() != null){
            tvNama.setText(mUserPref.getNama());
        }else{
            tvNama.setText("Nama Belum di setting");
        }

        if (mUserPref.getAlamat() != null){
            tvAlamat.setText(mUserPref.getAlamat());
        }else{
            tvAlamat.setText("Alamat Belum di setting");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setView();
    }
}
