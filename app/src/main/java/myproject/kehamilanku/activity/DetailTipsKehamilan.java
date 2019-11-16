package myproject.kehamilanku.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import myproject.kehamilanku.Kelas.TipsKehamilan;
import myproject.kehamilanku.R;
import myproject.kehamilanku.base.BaseActivity;

public class DetailTipsKehamilan extends BaseActivity {

    TextView tvDeskripsi,tvNamaTips;
    ImageView ivTips;
    Intent intent;
    TipsKehamilan tipsKehamilan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tips_kehamilan);

        tvDeskripsi = findViewById(R.id.tvDeskripsi);
        tvNamaTips = findViewById(R.id.tvNamaTips);
        ivTips = findViewById(R.id.ivTips);

        intent = getIntent();
        tipsKehamilan = (TipsKehamilan) intent.getSerializableExtra("tips");

        tvDeskripsi.setText(tipsKehamilan.getDeskripsi());
        tvNamaTips.setText(tipsKehamilan.getNamaTips());

        Glide.with(this)
                .load(tipsKehamilan.getFoto())
                .into(ivTips);

    }
}
