package myproject.kehamilanku.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import myproject.kehamilanku.Kelas.Petugas;
import myproject.kehamilanku.Kelas.Utils;
import myproject.kehamilanku.R;
import myproject.kehamilanku.base.BaseActivity;

public class AddPetugasActivity extends BaseActivity {

    EditText etPhone,etNama;
    Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_petugas);

        etPhone = findViewById(R.id.etPhone);
        etNama = findViewById(R.id.etNama);
        btnSimpan = findViewById(R.id.btnSimpan);
        ref = firestore.collection("petugas");

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
            simpanData(getNama,getPhone);
        }
    }

    private void simpanData(final String nama,final String phone){
        showLoading();

        final String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        Petugas petugas = new Petugas(timeStamp,nama,phone);


        ref.document(timeStamp).set(petugas).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dismissLoading();
                if (task.isSuccessful()){
                    showSuccessMessage("Data berhasil disimpan");
                    resetKomponen();
                }else {
                    showErrorMessage("Terjadi kesalahan, coba lagi nanti");
                    Log.d("AddPetugas:","eror"+task.getException().toString());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dismissLoading();
                showErrorMessage("Terjadi kesalahan, coba lagi nanti");
                Log.d("AddPetugas:","eror"+e.toString());
            }
        });

    }

    private void resetKomponen(){
        etNama.setText("");
        etPhone.setText("");
    }
}
