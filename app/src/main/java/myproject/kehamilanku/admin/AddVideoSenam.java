package myproject.kehamilanku.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import myproject.kehamilanku.Kelas.Utils;
import myproject.kehamilanku.Kelas.VideoSenam;
import myproject.kehamilanku.R;
import myproject.kehamilanku.base.BaseActivity;

public class AddVideoSenam extends BaseActivity {

    EditText etIDVideo,etNama;
    Button btnCek,btnSimpan;
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video_senam);

        ref = firestore.collection("videosenam");
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);
        etIDVideo = findViewById(R.id.etIDVideo);
        etNama = findViewById(R.id.etNama);
        btnCek = findViewById(R.id.btnCek);
        btnSimpan = findViewById(R.id.btnSimpan);

        youTubePlayerView.setEnabled(false);

        btnCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String videoID = etIDVideo.getText().toString();
                if (videoID.equals("") || videoID.length() == 0){
                    showErrorMessage("Anda harus mengisi ID Video terlebih dahulu");
                }else{
                    showLoading();
                    youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(YouTubePlayer youTubePlayer) {
                            //super.onReady(youTubePlayer);
                            dismissLoading();
                            //youTubePlayer.loadVideo(videoID,0);
                            youTubePlayer.cueVideo(videoID,0);
                            youTubePlayerView.setEnabled(true);
                        }

                        @Override
                        public void onError(YouTubePlayer youTubePlayer, PlayerConstants.PlayerError error) {
                            super.onError(youTubePlayer, error);
                            dismissLoading();
                            Log.d("youtubePlayer"," error : "+error.toString());
                        }
                    });
                }
            }
        });
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
    }

    private void checkValidation() {

        // Get all edittext texts
        String getNama      = etNama.getText().toString();
        String getYoutubeID = etIDVideo.getText().toString();
        Pattern p = Pattern.compile(Utils.regEx);

        // Check if all strings are null or not
        if (getNama.equals("") || getNama.length() == 0 ||
                getYoutubeID.equals("") || getYoutubeID.length() == 0) {

            showErrorMessage("Semua data harus diisi");
        }
        else{
            simpanData();
        }
    }

    private void simpanData(){
        showLoading();
        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        VideoSenam videoSenam = new VideoSenam(timeStamp,etNama.getText().toString(),etIDVideo.getText().toString());

        ref.document(timeStamp).set(videoSenam).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dismissLoading();
                if (task.isSuccessful()){
                    showSuccessMessage("Data berhasil disimpan");
                    resetKomponen();
                }else {
                    showErrorMessage("Terjadi kesalahan, coba lagi nanti");
                    Log.d("AddTips:","eror"+task.getException().toString());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dismissLoading();
                showErrorMessage("Terjadi kesalahan, coba lagi nanti");
                Log.d("AddTips:","eror"+e.toString());
            }
        });
    }

    private void resetKomponen(){
        etIDVideo.setText("");
        etNama.setText("");
    }
}
