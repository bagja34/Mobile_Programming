package com.example.mobile_programming;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    Button btnEditProfile;
    ImageView PP;
    TextView nama, email;
    User user;
    DatabaseHelper dh;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        btnEditProfile = findViewById(R.id.btnProfile);
        PP = findViewById(R.id.ivProfile);
        nama = findViewById(R.id.tvPname);
        email = findViewById(R.id.tvPemail);
        dh = new DatabaseHelper(this);
        loadUser();
        btnEditProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                intent.putExtra("id", user.getId());
                intent.putExtra("nama", user.getNama());
                intent.putExtra("email", user.getEmail());
                intent.putExtra("uri", user.getUri());
                startActivity(intent);
            }
        });
        uri = Uri.parse(user.getUri());
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, 1);
        }
        Glide.with(this).load(uri).into(PP);
        nama.setText(user.getNama());
        email.setText(user.getEmail());
    }
    private void loadUser(){
        Cursor c = dh.getUserByID(2);
        if (c != null && c.moveToFirst()) {
            user = new User(c.getInt(0),c.getString(1), c.getString(2), c.getString(4));
        }
    }
}