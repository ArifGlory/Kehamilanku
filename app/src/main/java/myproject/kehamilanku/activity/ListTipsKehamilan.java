package myproject.kehamilanku.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import myproject.kehamilanku.Kelas.TipsKehamilan;
import myproject.kehamilanku.R;
import myproject.kehamilanku.adapter.AdapterTipsKehamilan;
import myproject.kehamilanku.base.BaseActivity;

public class ListTipsKehamilan extends BaseActivity {


    RecyclerView rvTipsKehamilan;
    AdapterTipsKehamilan adapter;
    List<TipsKehamilan> tipsKehamilanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tips_kehamilan);

        rvTipsKehamilan = findViewById(R.id.rvTipsKehamilan);
        ref = firestore.collection("tipskehamilan");

        tipsKehamilanList = new ArrayList<>();
        adapter = new AdapterTipsKehamilan(getApplicationContext(),tipsKehamilanList);

        rvTipsKehamilan.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvTipsKehamilan.setHasFixedSize(true);
        rvTipsKehamilan.setItemAnimator(new DefaultItemAnimator());
        rvTipsKehamilan.setAdapter(adapter);

        getDataTips(ref,tipsKehamilanList,adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataTips(ref,tipsKehamilanList,adapter);
    }
}
