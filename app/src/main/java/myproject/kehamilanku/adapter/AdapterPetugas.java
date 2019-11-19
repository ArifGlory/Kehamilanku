package myproject.kehamilanku.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import myproject.kehamilanku.Kelas.Petugas;
import myproject.kehamilanku.Kelas.SharedVariable;
import myproject.kehamilanku.Kelas.TipsKehamilan;
import myproject.kehamilanku.R;
import myproject.kehamilanku.activity.DetailTipsKehamilan;
import myproject.kehamilanku.activity.ListPetugasActivity;
import myproject.kehamilanku.activity.PanelAdminActivity;
import myproject.kehamilanku.admin.UbahPetugasActivity;
import myproject.kehamilanku.admin.UbahTipsKehamilan;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AdapterPetugas extends RecyclerView.Adapter<AdapterPetugas.MyViewHolder> {

    private Context mContext;
    private SweetAlertDialog pDialogLoading,pDialodInfo;
    private List<Petugas> petugasList;
    String[] list_petugas = {"Amelia","Azalea","Dr. Roberto"};
    String[] list_phone = {"0896288383","0899193833","0858919383"};
    FirebaseFirestore firestore;
    CollectionReference ref;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNamaPetugas,tvPhone;
        public Button btnCall;
        public LinearLayout linePetugas;

        public MyViewHolder(View view) {
            super(view);
            btnCall = view.findViewById(R.id.btnCall);
            tvNamaPetugas = view.findViewById(R.id.tvNamaPetugas);
            tvPhone = view.findViewById(R.id.tvPhone);
            linePetugas = view.findViewById(R.id.linePetugas);

        }
    }

    public AdapterPetugas(Context mContext,List<Petugas> petugasList) {
        this.mContext = mContext;
        this.petugasList = petugasList;
        Firebase.setAndroidContext(mContext);
        FirebaseApp.initializeApp(mContext);
        firestore = FirebaseFirestore.getInstance();
        ref = firestore.collection("petugas");

        pDialogLoading = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Loading..");
        pDialogLoading.setCancelable(false);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_kontak, parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final Petugas petugas = petugasList.get(position);

       holder.tvNamaPetugas.setText(petugas.getNamaPetugas());
       holder.tvPhone.setText(petugas.getPhonePetugas());
       holder.btnCall.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String noTelepon = petugas.getPhonePetugas();
               Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", noTelepon, null));
               mContext.startActivity(intent);
           }
       });
       holder.linePetugas.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               if (SharedVariable.email.equals(SharedVariable.adminMail)){
                   new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                           .setTitleText("Kelola Petugas")
                           .setContentText("Anda dapat melakukan perubahan data ini")
                           .setConfirmText("Ubah Data")
                           .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                               @Override
                               public void onClick(SweetAlertDialog sDialog) {
                                   sDialog.dismissWithAnimation();
                                   Intent intent = new Intent(mContext, UbahPetugasActivity.class);
                                   intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                                   intent.putExtra("petugas",petugas);
                                   mContext.startActivity(intent);
                               }
                           })
                           .setCancelButton("Hapus", new SweetAlertDialog.OnSweetClickListener() {
                               @Override
                               public void onClick(SweetAlertDialog sDialog) {
                                   sDialog.dismissWithAnimation();
                                   pDialogLoading.show();
                                   ref.document(petugas.getIdPetugas()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                       @Override
                                       public void onSuccess(Void aVoid) {
                                           pDialogLoading.dismiss();
                                           if (mContext instanceof ListPetugasActivity){
                                               ((ListPetugasActivity)mContext).getDataPetugas();
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



    @Override
    public int getItemCount() {
        //return list_petugas.length;
        return petugasList.size();
    }
}
