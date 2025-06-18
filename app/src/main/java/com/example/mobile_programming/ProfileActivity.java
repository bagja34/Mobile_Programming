package com.example.mobile_programming;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    Button btnEditProfile;
    ImageView btnBack;
    TextView tvName, tvEmail;

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    int userId = 1; // diasumsikan pengguna dengan ID 1 sudah login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Inisialisasi DB
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();

        // Inisialisasi View
        btnEditProfile = findViewById(R.id.btnProfile);
        btnBack = findViewById(R.id.Pback);
        tvName = findViewById(R.id.tvPname);
        tvEmail = findViewById(R.id.tvPemail);

        // Tombol Edit Profile
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        // Tombol Back ke Dashboard
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        // Tampilkan data pengguna
        loadUserProfile();
    }

    private void loadUserProfile() {
        Cursor cursor = db.rawQuery("SELECT nama, email FROM user WHERE id = ?", new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            String nama = cursor.getString(0);
            String email = cursor.getString(1);

            tvName.setText(nama);
            tvEmail.setText(email);
        }
        cursor.close();
    }
}
