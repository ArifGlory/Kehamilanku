package myproject.kehamilanku.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import myproject.kehamilanku.Kelas.TabelBeratBadan;
import myproject.kehamilanku.Kelas.SharedVariable;
import myproject.kehamilanku.R;


import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AdapterBeratBadan extends RecyclerView.Adapter<AdapterBeratBadan.MyViewHolder> {

    private Context mContext;
    private SweetAlertDialog pDialogLoading,pDialodInfo;
    private List<TabelBeratBadan> beratBadanList;
    String[] list_TabelBeratBadan = {"Amelia","Azalea","Dr. Roberto"};
    String[] list_phone = {"0896288383","0899193833","0858919383"};
    FirebaseFirestore firestore;
    CollectionReference ref;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMinggu,tvMinimum,tvMaksimum,tvRatarata;
        public LinearLayout lineBeratBadan;

        public MyViewHolder(View view) {
            super(view);
            tvMaksimum = view.findViewById(R.id.tvMaksimum);
            tvMinggu = view.findViewById(R.id.tvMinggu);
            tvMinimum = view.findViewById(R.id.tvMinimum);
            tvRatarata = view.findViewById(R.id.tvRatarata);
            lineBeratBadan = view.findViewById(R.id.lineBeratBadan);

        }
    }

    public AdapterBeratBadan(Context mContext, List<TabelBeratBadan> beratBadanList) {
        this.mContext = mContext;
        this.beratBadanList = beratBadanList;

        pDialogLoading = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Loading..");
        pDialogLoading.setCancelable(false);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_beratbadan, parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final TabelBeratBadan beratBadan = beratBadanList.get(position);

       holder.tvMinggu.setText(beratBadan.getMinggu());
       holder.tvMinimum.setText(beratBadan.getMinimum());
       holder.tvMaksimum.setText(beratBadan.getMaksimum());
       holder.tvRatarata.setText(beratBadan.getRatarata());

    }



    @Override
    public int getItemCount() {
        //return list_TabelBeratBadan.length;
        return beratBadanList.size();
    }
}
