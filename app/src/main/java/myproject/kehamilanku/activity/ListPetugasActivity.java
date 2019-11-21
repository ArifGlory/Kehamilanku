package myproject.kehamilanku.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import myproject.kehamilanku.Kelas.Petugas;
import myproject.kehamilanku.Kelas.SharedVariable;
import myproject.kehamilanku.R;
import myproject.kehamilanku.adapter.AdapterPetugas;
import myproject.kehamilanku.admin.AddPetugasActivity;
import myproject.kehamilanku.base.BaseActivity;

public class ListPetugasActivity extends BaseActivity {

    RecyclerView rvPetugas;
    AdapterPetugas adapter;
    private List<Petugas> petugasList;
    FloatingActionButton btnCreate;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_petugas);

        rvPetugas = findViewById(R.id.rvPetugas);
        btnCreate = findViewById(R.id.btnCreate);
        petugasList = new ArrayList<>();
        ref = firestore.collection("petugas");

        adapter = new AdapterPetugas(this,petugasList);
        rvPetugas.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvPetugas.setHasFixedSize(true);
        rvPetugas.setItemAnimator(new DefaultItemAnimator());
        rvPetugas.setAdapter(adapter);

        if (SharedVariable.email.equals(SharedVariable.adminMail)){
            btnCreate.setVisibility(View.VISIBLE);
        }
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddPetugasActivity.class);
                startActivity(intent);
            }
        });

        getDataPetugas();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataPetugas();
    }


    public void getDataPetugas(){
        showLoading();
        ref.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                dismissLoading();
                petugasList.clear();

                if (task.isSuccessful()){
                    for (DocumentSnapshot doc : task.getResult()){
                        Petugas petugas = doc.toObject(Petugas.class);
                        petugasList.add(petugas);
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    showErrorMessage("Terjadi kesalahan,coba lagi nanti");
                }
            }
        });
    }

    public void keBack(View view) {
        onBackPressed();
    }
}
