package myproject.kehamilanku.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import myproject.kehamilanku.Kelas.VideoSenam;
import myproject.kehamilanku.R;
import myproject.kehamilanku.base.BaseActivity;

public class DetailVideoSenam extends BaseActivity {

    TextView tvJudulVideo;
    YouTubePlayerView youTubePlayerView;
    Intent intent;
    VideoSenam videoSenam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_video_senam);

        ref = firestore.collection("videosenam");
        tvJudulVideo = findViewById(R.id.tvJudulVideo);
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        intent = getIntent();
        videoSenam = (VideoSenam) intent.getSerializableExtra("videosenam");

        tvJudulVideo.setText(videoSenam.getJudulVideo());
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                //super.onReady(youTubePlayer);
                dismissLoading();
                //youTubePlayer.loadVideo(videoID,0);
                youTubePlayer.cueVideo(videoSenam.getYoutubeID(),0);
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
