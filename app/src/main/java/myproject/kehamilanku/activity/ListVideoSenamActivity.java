package myproject.kehamilanku.activity;

import android.annotation.SuppressLint;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import myproject.kehamilanku.Kelas.SharedVariable;
import myproject.kehamilanku.R;
import myproject.kehamilanku.base.BaseActivity;

public class ListVideoSenamActivity extends BaseActivity {

    RecyclerView rvVideoSenam;
    FloatingActionButton btnCreate;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_video_senam);

        rvVideoSenam = findViewById(R.id.rvVideoSenam);
        btnCreate = findViewById(R.id.btnCreate);

        if (SharedVariable.email.equals(SharedVariable.adminMail)){
            btnCreate.setVisibility(View.VISIBLE);
        }
    }
}
