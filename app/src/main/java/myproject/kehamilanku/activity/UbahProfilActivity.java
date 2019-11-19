package myproject.kehamilanku.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import myproject.kehamilanku.Kelas.AlarmReceiver;
import myproject.kehamilanku.Kelas.Utils;
import myproject.kehamilanku.R;
import myproject.kehamilanku.base.BaseActivity;

public class UbahProfilActivity extends BaseActivity {

    EditText etAlamat,etNama,etTinggi,etBerat;
    Button btnSimpan;
    RelativeLayout rlTanggal;
    Calendar myCalendar,calset;
    TextView tvTanggal;
    DatePickerDialog.OnDateSetListener date;
    String tanggal;
    Switch toogleNotif;
    boolean isCheckedNotif = false;
    final static int RQS1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_profil);

        etAlamat = findViewById(R.id.etAlamat);
        etNama = findViewById(R.id.etNama);
        etTinggi = findViewById(R.id.etTinggi);
        etBerat = findViewById(R.id.etBerat);
        btnSimpan = findViewById(R.id.btnSimpan);
        rlTanggal = findViewById(R.id.rlTanggal);
        tvTanggal = findViewById(R.id.tvTanggal);
        toogleNotif = findViewById(R.id.toogleNotif);

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
        toogleNotif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    isCheckedNotif = true;
                }else{
                    isCheckedNotif = false;
                }
            }
        });

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
        if (mUserPref.getTinggi() != 0){
            etTinggi.setText(mUserPref.getTinggi()+ "");
        }
        if (mUserPref.getBerat() != 0){
            etBerat.setText(mUserPref.getBerat()+"");
        }

        if (mUserPref.getHthp() != null){
            String HTHP = mUserPref.getHthp();
            tanggal = HTHP;
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

        if (mUserPref.getIsLoggedIn() != null){
            if (mUserPref.getIsLoggedIn().equals("true")){
                toogleNotif.setChecked(true);
            }else{
                toogleNotif.setChecked(false);
            }
        }


    }

    private void setAlarm(Calendar targetCal){
        Log.d("alarmSet"," on :"+"*****\n"+"Alarm Set On "+"\n"
                +targetCal.getTime()+"\n*****");

        Intent i = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),RQS1,i,0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //alarmManager.set(AlarmManager.RTC_WAKEUP,targetCal.getTimeInMillis(),pendingIntent);

        if (Build.VERSION.SDK_INT < 23){

            if (Build.VERSION.SDK_INT >= 19){
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,targetCal.getTimeInMillis(),pendingIntent);
            }else {
                alarmManager.set(AlarmManager.RTC_WAKEUP,targetCal.getTimeInMillis(),pendingIntent);
            }

        }else {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,targetCal.getTimeInMillis(),pendingIntent);
        }


    }

    private void checkValidation() {
        String getNama = etNama.getText().toString();
        String getAlamat = etAlamat.getText().toString();
        String getTinggi = etTinggi.getText().toString();
        String getBerat = etBerat.getText().toString();


        if (getNama.equals("") || getNama.length() == 0
                || getAlamat.equals("") || getAlamat.length() == 0
                || getTinggi.equals("") || getTinggi.length() == 0
                || getBerat.equals("") || getBerat.length() == 0
                || tanggal == null
        ) {

            showErrorMessage("Semua Field Harus diisi");
        }
        else {
            simpanProfil(getNama,getAlamat,getTinggi,getBerat);
        }
    }

    private void simpanProfil(final String nama,final String alamat,final String tinggi,final String berat){
        int myTinggi = Integer.parseInt(tinggi);
        int myBerat = Integer.parseInt(berat);

        mUserPref.setNama(nama);
        mUserPref.setAlamat(alamat);
        mUserPref.setHthp(tanggal);
        mUserPref.setTinggi(myTinggi);
        mUserPref.setBerat(myBerat);

        if (isCheckedNotif){
            int year = Integer.parseInt(tanggal.substring(0,4));
            int month = Integer.parseInt(tanggal.substring(5,7)) - 1;
            int day = Integer.parseInt(tanggal.substring(8,10));

            Log.d("HTHP","tahun : "+year);
            Log.d("HTHP","bulan : "+month);
            Log.d("HTHP","hari : "+day);

            calset.set(year,month,day,00,00,00);
            calset.add(Calendar.MONTH,3);
            setAlarm(calset);
        }else{
            calset.add(Calendar.YEAR,3);
            setAlarm(calset);
        }

        showSuccessMessage("Data Disimpan");
        onBackPressed();
    }
}
