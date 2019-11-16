package myproject.kehamilanku.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import myproject.kehamilanku.Kelas.SharedVariable;
import myproject.kehamilanku.R;
import myproject.kehamilanku.admin.AddVideoSenam;
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
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddVideoSenam.class);
                startActivity(intent);
            }
        });
    }
}
