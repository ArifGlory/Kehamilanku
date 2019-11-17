package myproject.kehamilanku.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import myproject.kehamilanku.Kelas.SharedVariable;
import myproject.kehamilanku.Kelas.VideoSenam;
import myproject.kehamilanku.R;
import myproject.kehamilanku.activity.DetailVideoSenam;
import myproject.kehamilanku.activity.ListVideoSenamActivity;
import myproject.kehamilanku.admin.UbahVideoSenam;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AdapterVideoSenam extends RecyclerView.Adapter<AdapterVideoSenam.MyViewHolder> {

    private Context mContext;
    private List<VideoSenam> videoSenamList;
    FirebaseFirestore firestore;
    CollectionReference ref;
    private SweetAlertDialog pDialogLoading,pDialodInfo;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvJudulVideo;
        YouTubePlayerView youTubePlayerView;
        public LinearLayout lineVideo;

        public MyViewHolder(View view) {
            super(view);
            tvJudulVideo = (TextView) view.findViewById(R.id.tvJudulVideo);
            youTubePlayerView = view.findViewById(R.id.youtube_player_view);
            lineVideo = view.findViewById(R.id.lineVideo);

        }
    }

    public AdapterVideoSenam(Context mContext, List<VideoSenam> videoSenamList) {
        this.mContext = mContext;
        this.videoSenamList = videoSenamList;
        Firebase.setAndroidContext(mContext);
        FirebaseApp.initializeApp(mContext);
        firestore = FirebaseFirestore.getInstance();
        ref = firestore.collection("videosenam");

        pDialogLoading = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialogLoading.setTitleText("Loading..");
        pDialogLoading.setCancelable(false);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video_senam, parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        if (videoSenamList.isEmpty()){

            Log.d("isiVideoSenam: ",""+videoSenamList.size());
        }else {

            final VideoSenam videoSenam = videoSenamList.get(position);

            holder.youTubePlayerView.setEnabled(false);
            holder.tvJudulVideo.setText(videoSenam.getJudulVideo());

            holder.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(YouTubePlayer youTubePlayer) {
                    //super.onReady(youTubePlayer);
                    youTubePlayer.cueVideo(videoSenam.getYoutubeID(), 0);
                    holder.youTubePlayerView.setEnabled(true);
                }
                @Override
                public void onError(YouTubePlayer youTubePlayer, PlayerConstants.PlayerError error) {
                    super.onError(youTubePlayer, error);
                    Log.d("youtubePlayer"," error id adapter : "+error.toString());
                }
            });

            holder.lineVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, DetailVideoSenam.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("videosenam",videoSenam);
                    mContext.startActivity(intent);
                }
            });
            holder.lineVideo.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (SharedVariable.email.equals(SharedVariable.adminMail)){
                        new SweetAlertDialog(mContext, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Kelola VideoSenam")
                                .setContentText("Anda dapat melakukan perubahan data ini")
                                .setConfirmText("Ubah Data")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        Intent intent = new Intent(mContext, UbahVideoSenam.class);
                                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra("videosenam",videoSenam);
                                        mContext.startActivity(intent);
                                        if (mContext instanceof ListVideoSenamActivity){
                                            ((ListVideoSenamActivity)mContext).finish();
                                        }
                                    }
                                })
                                .setCancelButton("Hapus", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        pDialogLoading.show();
                                        ref.document(videoSenam.getIdVideo()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                pDialogLoading.dismiss();
                                                if (mContext instanceof ListVideoSenamActivity){
                                                    ((ListVideoSenamActivity)mContext).reloadData();
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
        return videoSenamList.size();
    }
}
