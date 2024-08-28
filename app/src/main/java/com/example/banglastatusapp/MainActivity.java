package com.example.banglastatusapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    MaterialToolbar materialToolbar;
    NavigationView navigationView;
    CardView cvTodayMemeBtn,cvPopularMemeBtn,cvViralMemeBtn,cvAllMemeBtn,cvFavouriteBtn,cvMoreAppBtn,cvRateUsBtn;

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
        loadHomeScreen();




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
                    startActivity(new Intent(MainActivity.this, PrivacyPolicy.class));
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


    // -----------------------Inflate home screen------------------------
    private void loadHomeScreen(){
        FrameLayout frameLayout = findViewById(R.id.frameLayout);

        LayoutInflater inflater = LayoutInflater.from(this);
        View homeView = inflater.inflate(R.layout.activity_home, frameLayout, false);

        cvTodayMemeBtn = homeView.findViewById(R.id.cvTodayMemeBtn);
        cvPopularMemeBtn = homeView.findViewById(R.id.cvPopularMemeBtn);
        cvViralMemeBtn = homeView.findViewById(R.id.cvViralMemeBtn);
        cvAllMemeBtn = homeView.findViewById(R.id.cvAllMemeBtn);
        cvFavouriteBtn = homeView.findViewById(R.id.cvFavouriteBtn);
        cvMoreAppBtn = homeView.findViewById(R.id.cvMoreAppBtn);
        cvRateUsBtn = homeView.findViewById(R.id.cvRateUsBtn);


        cvTodayMemeBtn.setOnClickListener(v-> {
//            MemeActivity.URL = "";
            starActivityMeme();
        });

        cvPopularMemeBtn.setOnClickListener(v-> {
//            MemeActivity.URL = "";
            starActivityMeme();
        });

        cvViralMemeBtn.setOnClickListener(v-> {
//            MemeActivity.URL = "";
            starActivityMeme();
        });

        cvAllMemeBtn.setOnClickListener(v-> {
//            MemeActivity.URL = "";
            starActivityMeme();
        });

        cvFavouriteBtn.setOnClickListener(v-> {

        });

        cvMoreAppBtn.setOnClickListener(v-> {

        });

        cvRateUsBtn.setOnClickListener(v-> {

        });



        frameLayout.addView(homeView);

    }


    private void starActivityMeme(){
        startActivity(new Intent(MainActivity.this, MemeActivity.class));
    }




}