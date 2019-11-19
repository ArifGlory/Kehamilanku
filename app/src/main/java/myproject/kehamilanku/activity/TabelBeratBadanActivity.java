package myproject.kehamilanku.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import myproject.kehamilanku.R;
import myproject.kehamilanku.base.BaseActivity;

public class TabelBeratBadanActivity extends BaseActivity {

    TextView tvKlasifikasi,tvBMI;
    double bmi = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabel_berat_badan);

        tvKlasifikasi = findViewById(R.id.tvKlasifikasi);
        tvBMI = findViewById(R.id.tvBMI);

        int tinggi = mUserPref.getTinggi() / 10;
        bmi = mUserPref.getBerat() / (tinggi * tinggi);

        tvBMI.setText(bmi+"");
        if (bmi < 18.5 ){
            tvKlasifikasi.setText("Underweight");
        }else if(bmi > 18.5 && bmi < 24.9){
            tvKlasifikasi.setText("Ideal");
        }else if (bmi > 25 && bmi < 29.9){
            tvKlasifikasi.setText("Overweight ");
        }else{
            tvKlasifikasi.setText("Obesitas");
        }
    }
}
