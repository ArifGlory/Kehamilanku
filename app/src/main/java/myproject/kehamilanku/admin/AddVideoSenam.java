package myproject.kehamilanku.admin;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

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

        //video Id = w4Z7bBvSfBw
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);
        etIDVideo = findViewById(R.id.etIDVideo);
        etNama = findViewById(R.id.etNama);
        btnCek = findViewById(R.id.btnCek);
        btnSimpan = findViewById(R.id.btnSimpan);

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
                            super.onReady(youTubePlayer);
                            dismissLoading();
                            youTubePlayer.loadVideo(videoID,0);
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
    }
}
