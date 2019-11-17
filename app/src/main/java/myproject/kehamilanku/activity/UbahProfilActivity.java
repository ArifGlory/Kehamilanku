package myproject.kehamilanku.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import myproject.kehamilanku.Kelas.Utils;
import myproject.kehamilanku.R;
import myproject.kehamilanku.base.BaseActivity;

public class UbahProfilActivity extends BaseActivity {

    EditText etAlamat,etNama;
    Button btnSimpan;
    RelativeLayout rlTanggal;
    Calendar myCalendar,calset;
    TextView tvTanggal;
    DatePickerDialog.OnDateSetListener date;
    String tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_profil);

        etAlamat = findViewById(R.id.etAlamat);
        etNama = findViewById(R.id.etNama);
        btnSimpan = findViewById(R.id.btnSimpan);
        rlTanggal = findViewById(R.id.rlTanggal);
        tvTanggal = findViewById(R.id.tvTanggal);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });
        rlTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(UbahProfilActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        myCalendar = Calendar.getInstance();
        calset = (Calendar) myCalendar.clone();
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        setView();
    }

    private void updateLabel() {
        String myFormat     = "dd-MM-yyyy";
        String sqlFormat    = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf_untukDikirim = new SimpleDateFormat(sqlFormat,Locale.US);

        tanggal = sdf_untukDikirim.format(myCalendar.getTime());
        tvTanggal.setText(sdf.format(myCalendar.getTime()));
        Log.d("UbahProfil","tanggal "+tanggal);
    }

    private void setView(){
        if (mUserPref.getNama() != null){
            etNama.setText(mUserPref.getNama());
        }

        if (mUserPref.getAlamat() != null){
            etAlamat.setText(mUserPref.getAlamat());
        }

        if (mUserPref.getHthp() != null){
            String HTHP = mUserPref.getHthp();
            int year = Integer.parseInt(HTHP.substring(0,4));
            int month = Integer.parseInt(HTHP.substring(5,7)) - 1;
            int day = Integer.parseInt(HTHP.substring(8,10));

            Log.d("HTHP","tahun : "+year);
            Log.d("HTHP","bulan : "+month);
            Log.d("HTHP","hari : "+day);

            calset.set(year,month,day,00,00,00);
            month = month +1;
            tvTanggal.setText("Hari Pertama Haid Terakhir : "+day+"-"+month+"-"+year);
        }


    }

    private void checkValidation() {
        String getNama = etNama.getText().toString();
        String getAlamat = etAlamat.getText().toString();


        if (getNama.equals("") || getNama.length() == 0
                || getAlamat.equals("") || getAlamat.length() == 0
                || tanggal == null
        ) {

            showErrorMessage("Semua Field Harus diisi");
        }
        else {
            simpanProfil(getNama,getAlamat);
        }
    }

    private void simpanProfil(final String nama,final String alamat){
        mUserPref.setNama(nama);
        mUserPref.setAlamat(alamat);
        mUserPref.setHthp(tanggal);
        showSuccessMessage("Data Disimpan");
        onBackPressed();
    }
}
