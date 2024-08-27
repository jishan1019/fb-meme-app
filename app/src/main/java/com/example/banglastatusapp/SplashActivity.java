package com.example.banglastatusapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    Animation botom_anim , top_anim;
    CardView logo1;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        getWindow().setStatusBarColor(getResources().getColor(R.color.white, getTheme()));

        text =  findViewById(R.id.stext);
        logo1 =  findViewById(R.id.logo1);
        top_anim = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        botom_anim = AnimationUtils.loadAnimation(this, R.anim.botom_anim);
        logo1.setAnimation(this.top_anim);
        text.setAnimation(this.botom_anim);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        }, 2000);






    }

    //=========================== On Create end here ============================================




}