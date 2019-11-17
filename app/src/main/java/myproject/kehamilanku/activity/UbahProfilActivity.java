package myproject.kehamilanku.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import myproject.kehamilanku.Kelas.Utils;
import myproject.kehamilanku.R;
import myproject.kehamilanku.base.BaseActivity;

public class UbahProfilActivity extends BaseActivity {

    EditText etPhone,etAlamat,etNama;
    Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_profil);

        etPhone = findViewById(R.id.etPhone);
        etAlamat = findViewById(R.id.etAlamat);
        etNama = findViewById(R.id.etNama);
        btnSimpan = findViewById(R.id.btnSimpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

        setView();
    }

    private void setView(){
        if (mUserPref.getNama() != null){
            etNama.setText(mUserPref.getNama());
        }

        if (mUserPref.getAlamat() != null){
            etAlamat.setText(mUserPref.getAlamat());
        }

        if (mUserPref.getNope() != null){
            etPhone.setText(mUserPref.getNope());
        }
    }

    private void checkValidation() {
        String getNama = etNama.getText().toString();
        String getAlamat = etAlamat.getText().toString();
        String getPhone = etPhone.getText().toString();


        if (getNama.equals("") || getNama.length() == 0
                || getAlamat.equals("") || getAlamat.length() == 0
                || getPhone.equals("") || getPhone.length() == 0
        ) {

            showErrorMessage("Semua Field Harus diisi");
        }
        else {
            simpanProfil(getNama,getAlamat,getPhone);
        }
    }

    private void simpanProfil(final String nama,final String alamat,final String phone){
        mUserPref.setNama(nama);
        mUserPref.setAlamat(alamat);
        mUserPref.setNope(phone);
        onBackPressed();
    }
}
