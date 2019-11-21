package myproject.kehamilanku.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.ImageVideoBitmapDecoder;

import de.hdodenhof.circleimageview.CircleImageView;
import myproject.kehamilanku.R;
import myproject.kehamilanku.base.BaseActivity;

public class ProfilActivity extends BaseActivity {

    TextView tvNama,tvAlamat;
    LinearLayout lineUbahProfil;
    CircleImageView ivProfPict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        tvNama = findViewById(R.id.tvNama);
        tvAlamat = findViewById(R.id.tvAlamat);
        lineUbahProfil = findViewById(R.id.lineUbahProfil);
        ivProfPict = findViewById(R.id.ivProfPict);

        lineUbahProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UbahProfilActivity.class);
                startActivity(intent);
            }
        });
        ivProfPict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangeAvatarActivity.class);
                startActivity(intent);
            }
        });

       setView();

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
        if (mUserPref.getFoto() != null){
            Glide.with(this)
                    .load(mUserPref.getFoto())
                    .into(ivProfPict);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setView();
    }

    public void keBack(View view) {
        onBackPressed();
    }
}
