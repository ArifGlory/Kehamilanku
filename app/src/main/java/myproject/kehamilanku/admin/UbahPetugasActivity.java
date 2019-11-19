package myproject.kehamilanku.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.regex.Pattern;

import myproject.kehamilanku.Kelas.Petugas;
import myproject.kehamilanku.Kelas.Utils;
import myproject.kehamilanku.R;
import myproject.kehamilanku.base.BaseActivity;

public class UbahPetugasActivity extends BaseActivity {

    EditText etPhone,etNama;
    Button btnSimpan;
    Intent intent;
    Petugas petugas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_petugas);

        etPhone = findViewById(R.id.etPhone);
        etNama = findViewById(R.id.etNama);
        btnSimpan = findViewById(R.id.btnSimpan);
        ref = firestore.collection("petugas");

        intent = getIntent();
        petugas = (Petugas) intent.getSerializableExtra("petugas");
        etNama.setText(petugas.getNamaPetugas());
        etPhone.setText(petugas.getPhonePetugas());

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
        String getPhone     = etPhone.getText().toString();
        Pattern p = Pattern.compile(Utils.regEx);

        // Check if all strings are null or not
        if (getNama.equals("") || getNama.length() == 0 ||
                getPhone.equals("") || getPhone.length() == 0) {

            showErrorMessage("Semua data harus diisi");
        }
        else{
            updateData(getNama,getPhone);
        }
    }

    private void updateData(final String nama,final String phone){
        showLoading();

        ref.document(petugas.getIdPetugas()).update("namaPetugas",nama);
        ref.document(petugas.getIdPetugas()).update("phonePetugas",phone).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dismissLoading();
                if (task.isSuccessful()){
                    showSuccessMessage("Perubahan berhasil disimpan");
                    onBackPressed();
                }else {
                    showErrorMessage("Terjadi kesalahan, coba lagi nanti");
                    Log.d("AddPetugas:","eror"+task.getException().toString());
                }
            }
        });
    }
}
