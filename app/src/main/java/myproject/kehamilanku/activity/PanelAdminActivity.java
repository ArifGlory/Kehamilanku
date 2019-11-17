package myproject.kehamilanku.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;

import myproject.kehamilanku.Kelas.SharedVariable;
import myproject.kehamilanku.R;
import myproject.kehamilanku.admin.AddVideoSenam;
import myproject.kehamilanku.base.BaseActivity;
import myproject.kehamilanku.fragment.FragmentHomeAdmin;

public class PanelAdminActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentHomeAdmin fragmentHomeAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentHomeAdmin = new FragmentHomeAdmin();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        goToFragment(fragmentHomeAdmin,true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.panel_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    void goToFragment(Fragment fragment, boolean isTop) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_homeAdmin, fragment);
        if (!isTop)
            fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_tips) {
            Intent i = new Intent(getApplicationContext(), PanelAdminActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_logout) {
            //biar tombol create ga muncul di client
            SharedVariable.email = "-";

            fAuth.signOut();
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_videosenam){
            Intent i = new Intent(getApplicationContext(), ListVideoSenamActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void reloadListTipsKehamilan(){
        fragmentHomeAdmin.reloadData();
    }
}
