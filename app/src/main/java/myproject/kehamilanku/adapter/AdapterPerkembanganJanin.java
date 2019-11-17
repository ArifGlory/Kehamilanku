package myproject.kehamilanku.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import cn.pedant.SweetAlert.SweetAlertDialog;
import myproject.kehamilanku.R;


/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AdapterPerkembanganJanin extends RecyclerView.Adapter<AdapterPerkembanganJanin.MyViewHolder> {

    private Context mContext;
    private SweetAlertDialog pDialogLoading,pDialodInfo;
    String[] list_bulan     = {"Bulan ke-1","Bulan ke-2","Bulan ke-3","Bulan ke-4","Bulan ke-5","Bulan ke-6"
            ,"Bulan ke-7","Bulan ke-8","Bulan ke-9"};
    String[] list_deskripsi = {"Janin masih berupa Embrio yang terdiri dari dua lapisan sel dari mana semua organ dan bagian tubuhnya akan berkembang.",
            "Janin sekarang seukuran kacang merah dan terus bergerak. Dia memiliki jari yang berbeda, sedikit berselaput.",
            "Saat ini, bayi Anda memiliki panjang sekitar 7 hingga 8 sentimeter (3 inci) dan beratnya hampir sama dengan kacang polong. Sidik jarinya yang mungil dan unik kini ada di tempatnya.",
            "Panjang bayi Anda sekarang sekitar 13cm  dan berat 140g dan Kerangkanya mulai mengeras dari tulang rawan  ke tulang lainnya.",
            "Alis dan kelopak mata sudah di tempat. Janin sekarang akan lebih dari 27cm jika Anda merentangkan kakinya.",
            "Berat bayi Anda sekitar 660 gram. Kulitnya yang keriput mulai melembut saat ia menempatkan lemak bayi.",
            "Sekarang, janin sudah lebih dari 40cm . Dia dapat membuka dan menutup matanya dan mungkin melihat apa yang ada di sekitarnya.",
            "Bayi sekarang memiliki berat sekitar 2,2 kg . Lapisan lemaknya memenuhi dirinya, membuatnya lebih bulat, dan paru-parunya berkembang dengan baik.",
            "Bayi hampir masuk ke masa untuk dilahirkan. Saat lahir, bayi rata-rata lebih dari 51cm panjang dari kepala hingga kaki dan beratnya sekitar 3.4kg ," +
                    " tetapi bayi sangat bervariasi ukurannya pada tahap ini."};

    int[] list_foto         = {R.drawable.janin1,R.drawable.janin2,R.drawable.janin3,R.drawable.janin4,R.drawable.janin5,
            R.drawable.janin6,R.drawable.janin7,R.drawable.janin8,R.drawable.janin9};


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvBulan,tvDeskripsi;
        public ImageView  ivFotoJanin;

        public MyViewHolder(View view) {
            super(view);
            ivFotoJanin = view.findViewById(R.id.ivFotoJanin);
            tvBulan = view.findViewById(R.id.tvBulan);
            tvDeskripsi = view.findViewById(R.id.tvDeskripsi);

        }
    }

    public AdapterPerkembanganJanin(Context mContext) {
        this.mContext = mContext;

        pDialogLoading = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Loading..");
        pDialogLoading.setCancelable(false);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_perkembanngan_janin, parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

       holder.tvBulan.setText(list_bulan[position]);
       holder.tvDeskripsi.setText(list_deskripsi[position]);
       holder.ivFotoJanin.setImageResource(list_foto[position]);

    }



    @Override
    public int getItemCount() {
        return list_bulan.length;
       // return tipsKehamilanList.size();
    }
}
