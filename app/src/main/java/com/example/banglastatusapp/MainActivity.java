package com.example.banglastatusapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    MaterialToolbar materialToolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout = findViewById(R.id.drawerLayout);
        materialToolbar = findViewById(R.id.materialToolbar);
        navigationView = findViewById(R.id.navigationView);


        // ----------------------- Drawer Layout------------------------
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                MainActivity.this, drawerLayout, materialToolbar,R.string.nav_close, R.string.nav_open);
        drawerLayout.addDrawerListener(toggle);


        loadDrawerNavigation();
        loadToolbarItem();




    }

    //=========================== On Create end here ============================================


    // -----------------------Drawer Navigation View------------------------
    private void loadDrawerNavigation(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.nav_home){

                    drawerLayout.closeDrawer(GravityCompat.START);
                }else if (item.getItemId() == R.id.favourite){

                    drawerLayout.closeDrawer(GravityCompat.START);
                }else if (item.getItemId() == R.id.rating){

                    drawerLayout.closeDrawer(GravityCompat.START);
                }else if (item.getItemId() == R.id.moreApps){

                    drawerLayout.closeDrawer(GravityCompat.START);
                }else if (item.getItemId() == R.id.privacyPolicy){

                    drawerLayout.closeDrawer(GravityCompat.START);
                }

                return true;
            }
        });
    }


    //----------------------- Toolbar item --------------------------------
    private void loadToolbarItem(){
        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getItemId() == R.id.toolbarNotification){

                }

                return false;
            }
        });

    }





}