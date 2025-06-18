package com.example.mobile_programming;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    EditText etNama, etEmail;
    Button btnSimpan;
    ImageView btnBack;

    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    int userId = 1; // sementara user ID statis

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Inisialisasi database
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        // Inisialisasi view
        etNama = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        btnSimpan = findViewById(R.id.btnEditProfile);
        btnBack = findViewById(R.id.EPback);

        // Load data user ke EditText
        loadUserData();

        // Tombol Simpan
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        // Tombol Back
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadUserData() {
        Cursor cursor = db.rawQuery("SELECT nama, email FROM user WHERE id = ?", new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            String nama = cursor.getString(0);
            String email = cursor.getString(1);
            etNama.setText(nama);
            etEmail.setText(email);
        }
        cursor.close();
    }

    private void updateProfile() {
        String nama = etNama.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (nama.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Nama dan Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        db.execSQL("UPDATE user SET nama = ?, email = ? WHERE id = ?", new Object[]{nama, email, userId});
        Toast.makeText(this, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show();

        // Kembali ke ProfileActivity
        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }
}
