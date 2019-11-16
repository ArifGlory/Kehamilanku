package myproject.kehamilanku.activity;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.view.MenuItem;

import myproject.kehamilanku.R;
import myproject.kehamilanku.base.BaseActivity;
import myproject.kehamilanku.fragment.FragmentHomeUser;
import myproject.kehamilanku.fragment.FragmentLogin;

public class HomeActivity extends BaseActivity {

    FragmentHomeUser fragmentHomeUser;
    FragmentLogin fragmentLogin;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    goToFragment(fragmentHomeUser,true);
                    return true;
                case R.id.navigation_login:
                    goToFragment(fragmentLogin,true);
                    return true;

            }
            return false;
        }
    };

    void goToFragment(Fragment fragment, boolean isTop) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        if (!isTop)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentHomeUser = new FragmentHomeUser();
        fragmentLogin = new FragmentLogin();
        goToFragment(fragmentHomeUser,true);
    }

}
