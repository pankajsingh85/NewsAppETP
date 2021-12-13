package com.pankaj.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.pankaj.newsapp.api.ApiClient;
import com.pankaj.newsapp.api.ApiInterface;
import com.pankaj.newsapp.fragment.businessFragment;
import com.pankaj.newsapp.fragment.entertainmentFragment;
import com.pankaj.newsapp.fragment.healthFragment;
import com.pankaj.newsapp.fragment.scienceFragment;
import com.pankaj.newsapp.fragment.sportFragment;
import com.pankaj.newsapp.fragment.techFragment;

import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    ApiInterface apiInterface;

    final static String apiKey="00dea44cfeef418fba8e0fdbf6af0ae4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        NavigationView nvDrawer = findViewById(R.id.navView);
        drawerLayout = findViewById(R.id.drawer_layout);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        setupDrawerContent(nvDrawer);

        Retrofit retrofit = ApiClient.getClient("http://newsapi.org/");
        apiInterface = retrofit.create(ApiInterface.class);


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent,
                    new DashboardFragment()).commit();
        }


    }

    private NavigationBarView.OnItemSelectedListener navListener =
            new BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new DashboardFragment();
                            break;
                        case R.id.nav_favorites:
                            selectedFragment = new FavouriteFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.flContent,
                            selectedFragment).commit();

                    return true;
                }
            };


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }
    public void selectDrawerItem(MenuItem menuItem) {

        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {

            case R.id.nav_entertainment:
                fragmentClass = entertainmentFragment.class;
                break;
            case R.id.nav_health:
                fragmentClass = healthFragment.class;
                break;

            case R.id.nav_science:
                fragmentClass = scienceFragment.class;
                break;
            case R.id.nav_sport:
                fragmentClass = sportFragment.class;
                break;
            case R.id.nav_tech:
                fragmentClass = techFragment.class;
                break;
            default:
                fragmentClass = businessFragment.class;

        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
    }



}