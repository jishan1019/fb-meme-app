package com.example.banglastatusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PreviewMemeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_meme);

        ImageView imgPreview = findViewById(R.id.imgPreview);
        ImageView closeButton = findViewById(R.id.closeButton);

        String imageUrl = getIntent().getStringExtra("imageUrl");

        Picasso.get().load(imageUrl).into(imgPreview);

        closeButton.setOnClickListener(v -> finish());

    }



}