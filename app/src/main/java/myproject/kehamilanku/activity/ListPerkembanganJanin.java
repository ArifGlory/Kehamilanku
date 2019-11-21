package myproject.kehamilanku.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import myproject.kehamilanku.R;
import myproject.kehamilanku.adapter.AdapterPerkembanganJanin;
import myproject.kehamilanku.base.BaseActivity;

public class ListPerkembanganJanin extends BaseActivity {

    RecyclerView rvPerkembanganJanin;
    AdapterPerkembanganJanin adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_perkembangan_janin);

        rvPerkembanganJanin = findViewById(R.id.rvPerkembanganJanin);

        adapter = new AdapterPerkembanganJanin(this);
        rvPerkembanganJanin.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvPerkembanganJanin.setHasFixedSize(true);
        rvPerkembanganJanin.setItemAnimator(new DefaultItemAnimator());
        rvPerkembanganJanin.setAdapter(adapter);
    }

    public void keBack(View view) {
        onBackPressed();
    }
}
