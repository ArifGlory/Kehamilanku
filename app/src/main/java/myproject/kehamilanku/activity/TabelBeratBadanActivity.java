package myproject.kehamilanku.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import myproject.kehamilanku.Kelas.TabelBeratBadan;
import myproject.kehamilanku.R;
import myproject.kehamilanku.adapter.AdapterBeratBadan;
import myproject.kehamilanku.base.BaseActivity;

public class TabelBeratBadanActivity extends BaseActivity {

    TextView tvKlasifikasi,tvBMI;
    RecyclerView rvTabelBerat;
    Double bmi = 0.0;
    AdapterBeratBadan adapter;
    List<TabelBeratBadan> beratBadanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabel_berat_badan);

        tvKlasifikasi = findViewById(R.id.tvKlasifikasi);
        tvBMI = findViewById(R.id.tvBMI);
        rvTabelBerat = findViewById(R.id.rvTabelBerat);

        beratBadanList = new ArrayList<>();
        adapter = new AdapterBeratBadan(this,beratBadanList);
        rvTabelBerat.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvTabelBerat.setHasFixedSize(true);
        rvTabelBerat.setItemAnimator(new DefaultItemAnimator());
        rvTabelBerat.setAdapter(adapter);

        if (mUserPref.getBerat() != 0 && mUserPref.getTinggi() != 0){
            Log.d("berat",":"+mUserPref.getBerat());
            Log.d("tinggi",":"+mUserPref.getTinggi());
            Double tinggi = 0.0;
            Double getTinggi = Double.valueOf(mUserPref.getTinggi());
            tinggi = getTinggi / 100;
            Log.d("tinggi",":"+tinggi);
            tinggi = tinggi * tinggi;
            Log.d("tinggi",":"+tinggi);
            bmi = mUserPref.getBerat() / tinggi;

            DecimalFormat df = new DecimalFormat("#.##");
            tvBMI.setText(df.format(bmi)+"");
            if (bmi < 18.5 ){
                tvKlasifikasi.setText("Underweight");
                setTabelUnderweight();
            }else if(bmi > 18.5 && bmi < 24.9){
                tvKlasifikasi.setText("Ideal");
                setTabelIdeal();
            }else if (bmi > 25 && bmi < 29.9){
                tvKlasifikasi.setText("Overweight ");
                setTabelOverweight();
            }else{
                tvKlasifikasi.setText("Obesitas");
                setTabelObesitas();
            }
        }else{
            tvBMI.setText("Berat / Tinggi anda belum di setting");
            tvKlasifikasi.setText("-");
        }


    }

    private void setTabelUnderweight(){
        TabelBeratBadan head = new TabelBeratBadan("Minggu","Minimum","Maksimum","Rata-rata");
        String myBerat = String.valueOf(mUserPref.getBerat());
        TabelBeratBadan subHead = new TabelBeratBadan("0",myBerat,myBerat,myBerat);
        beratBadanList.add(head);
        beratBadanList.add(subHead);


        Double rangeMin = 12.0;
        rangeMin = rangeMin / 20;
        Double rangeMax = 18.0;
        rangeMax = rangeMax / 20;
        DecimalFormat df = new DecimalFormat("#.##");

        Double beratMin = Double.valueOf(mUserPref.getBerat());
        Double beratMax = Double.valueOf(mUserPref.getBerat());
        Double rata2 = 0.0;

        for (int c=2;c <=40; c+=2){
            beratMin = beratMin + rangeMin;
            beratMax = beratMax +rangeMax;
            rata2 = (beratMax + beratMin) / 2;

            String myMinggu = String.valueOf(df.format(c));
            String myBeratMin = String.valueOf(df.format(beratMin));
            String myBeratMax = String.valueOf(df.format(beratMax));
            String myRata2 = String.valueOf(df.format(rata2));

            TabelBeratBadan bodyTabel = new TabelBeratBadan(myMinggu,myBeratMin,myBeratMax,myRata2);
            beratBadanList.add(bodyTabel);
        }
        adapter.notifyDataSetChanged();
    }

    private void setTabelIdeal(){
        TabelBeratBadan head = new TabelBeratBadan("Minggu","Minimum","Maksimum","Rata-rata");
        String myBerat = String.valueOf(mUserPref.getBerat());
        TabelBeratBadan subHead = new TabelBeratBadan("0",myBerat,myBerat,myBerat);
        beratBadanList.add(head);
        beratBadanList.add(subHead);


        Double rangeMin = 11.0;
        rangeMin = rangeMin / 20;
        Double rangeMax = 16.0;
        rangeMax = rangeMax / 20;
        DecimalFormat df = new DecimalFormat("#.##");

        Double beratMin = Double.valueOf(mUserPref.getBerat());
        Double beratMax = Double.valueOf(mUserPref.getBerat());
        Double rata2 = 0.0;

        for (int c=2;c <=40; c+=2){
            beratMin = beratMin + rangeMin;
            beratMax = beratMax +rangeMax;
            rata2 = (beratMax + beratMin) / 2;

            String myMinggu = String.valueOf(df.format(c));
            String myBeratMin = String.valueOf(df.format(beratMin));
            String myBeratMax = String.valueOf(df.format(beratMax));
            String myRata2 = String.valueOf(df.format(rata2));

            TabelBeratBadan bodyTabel = new TabelBeratBadan(myMinggu,myBeratMin,myBeratMax,myRata2);
            beratBadanList.add(bodyTabel);
        }
        adapter.notifyDataSetChanged();
    }

    private void setTabelOverweight(){
        TabelBeratBadan head = new TabelBeratBadan("Minggu","Minimum","Maksimum","Rata-rata");
        String myBerat = String.valueOf(mUserPref.getBerat());
        TabelBeratBadan subHead = new TabelBeratBadan("0",myBerat,myBerat,myBerat);
        beratBadanList.add(head);
        beratBadanList.add(subHead);


        Double rangeMin = 7.0;
        rangeMin = rangeMin / 20;
        Double rangeMax = 11.0;
        rangeMax = rangeMax / 20;
        DecimalFormat df = new DecimalFormat("#.##");

        Double beratMin = Double.valueOf(mUserPref.getBerat());
        Double beratMax = Double.valueOf(mUserPref.getBerat());
        Double rata2 = 0.0;

        for (int c=2;c <=40; c+=2){
            beratMin = beratMin + rangeMin;
            beratMax = beratMax +rangeMax;
            rata2 = (beratMax + beratMin) / 2;

            String myMinggu = String.valueOf(df.format(c));
            String myBeratMin = String.valueOf(df.format(beratMin));
            String myBeratMax = String.valueOf(df.format(beratMax));
            String myRata2 = String.valueOf(df.format(rata2));

            TabelBeratBadan bodyTabel = new TabelBeratBadan(myMinggu,myBeratMin,myBeratMax,myRata2);
            beratBadanList.add(bodyTabel);
        }
        adapter.notifyDataSetChanged();
    }

    private void setTabelObesitas(){
        TabelBeratBadan head = new TabelBeratBadan("Minggu","Minimum","Maksimum","Rata-rata");
        String myBerat = String.valueOf(mUserPref.getBerat());
        TabelBeratBadan subHead = new TabelBeratBadan("0",myBerat,myBerat,myBerat);
        beratBadanList.add(head);
        beratBadanList.add(subHead);


        Double rangeMin = 5.0;
        rangeMin = rangeMin / 20;
        Double rangeMax = 9.0;
        rangeMax = rangeMax / 20;
        DecimalFormat df = new DecimalFormat("#.##");

        Double beratMin = Double.valueOf(mUserPref.getBerat());
        Double beratMax = Double.valueOf(mUserPref.getBerat());
        Double rata2 = 0.0;

        for (int c=2;c <=40; c+=2){
            beratMin = beratMin + rangeMin;
            beratMax = beratMax +rangeMax;
            rata2 = (beratMax + beratMin) / 2;

            String myMinggu = String.valueOf(df.format(c));
            String myBeratMin = String.valueOf(df.format(beratMin));
            String myBeratMax = String.valueOf(df.format(beratMax));
            String myRata2 = String.valueOf(df.format(rata2));

            TabelBeratBadan bodyTabel = new TabelBeratBadan(myMinggu,myBeratMin,myBeratMax,myRata2);
            beratBadanList.add(bodyTabel);
        }
        adapter.notifyDataSetChanged();
    }

    public void keBack(View view) {
        onBackPressed();
    }
}
