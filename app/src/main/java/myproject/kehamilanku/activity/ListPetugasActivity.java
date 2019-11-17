package myproject.kehamilanku.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import myproject.kehamilanku.R;
import myproject.kehamilanku.adapter.AdapterPetugas;
import myproject.kehamilanku.base.BaseActivity;

public class ListPetugasActivity extends BaseActivity {

    RecyclerView rvPetugas;
    AdapterPetugas adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_petugas);

        rvPetugas = findViewById(R.id.rvPetugas);

        adapter = new AdapterPetugas(this);
        rvPetugas.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvPetugas.setHasFixedSize(true);
        rvPetugas.setItemAnimator(new DefaultItemAnimator());
        rvPetugas.setAdapter(adapter);
    }
}
