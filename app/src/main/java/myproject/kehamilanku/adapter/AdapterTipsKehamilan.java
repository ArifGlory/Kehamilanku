package myproject.kehamilanku.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import myproject.kehamilanku.Kelas.SharedVariable;
import myproject.kehamilanku.Kelas.TipsKehamilan;
import myproject.kehamilanku.R;
import myproject.kehamilanku.activity.DetailTipsKehamilan;
import myproject.kehamilanku.activity.PanelAdminActivity;
import myproject.kehamilanku.admin.UbahTipsKehamilan;


import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AdapterTipsKehamilan extends RecyclerView.Adapter<AdapterTipsKehamilan.MyViewHolder> {

    private Context mContext;
    private List<TipsKehamilan> tipsKehamilanList;
    FirebaseFirestore firestore;
    CollectionReference ref;
    private SweetAlertDialog pDialogLoading,pDialodInfo;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNamaTips;
        ImageView ivTips;
        public LinearLayout lineTips;

        public MyViewHolder(View view) {
            super(view);
            tvNamaTips = (TextView) view.findViewById(R.id.tvNamaTips);
            ivTips = (ImageView) view.findViewById(R.id.ivTips);
            lineTips = view.findViewById(R.id.lineTips);

        }
    }

    public AdapterTipsKehamilan(Context mContext, List<TipsKehamilan> tipsKehamilanList) {
        this.mContext = mContext;
        this.tipsKehamilanList = tipsKehamilanList;
        Firebase.setAndroidContext(mContext);
        FirebaseApp.initializeApp(mContext);
        firestore = FirebaseFirestore.getInstance();
        ref = firestore.collection("tipskehamilan");

        pDialogLoading = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Loading..");
        pDialogLoading.setCancelable(false);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tips_kehamilan, parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (tipsKehamilanList.isEmpty()){

            Log.d("isiTipsKehamilan: ",""+tipsKehamilanList.size());
        }else {

            final TipsKehamilan tipsKehamilan = tipsKehamilanList.get(position);

            holder.tvNamaTips.setText(tipsKehamilan.getNamaTips());

            Glide.with(mContext)
                    .load(tipsKehamilan.getFoto())
                    .into(holder.ivTips);

            holder.lineTips.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailTipsKehamilan.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("tips",tipsKehamilan);
                    mContext.startActivity(intent);
                }
            });
            holder.lineTips.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (SharedVariable.email.equals(SharedVariable.adminMail)){
                        new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Kelola TipsKehamilan")
                                .setContentText("Anda dapat melakukan perubahan data ini")
                                .setConfirmText("Ubah Data")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        Intent intent = new Intent(mContext, UbahTipsKehamilan.class);
                                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra("tips",tipsKehamilan);
                                        mContext.startActivity(intent);
                                    }
                                })
                                .setCancelButton("Hapus", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        pDialogLoading.show();
                                        ref.document(tipsKehamilan.getIdTips()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                pDialogLoading.dismiss();
                                                if (mContext instanceof PanelAdminActivity){
                                                    ((PanelAdminActivity)mContext).reloadListTipsKehamilan();
                                                }
                                            }
                                        });

                                    }
                                })
                                .show();
                    }

                    return true;
                }
            });


        }

    }



    @Override
    public int getItemCount() {
        //return namaWisata.length;
        return tipsKehamilanList.size();
    }
}
