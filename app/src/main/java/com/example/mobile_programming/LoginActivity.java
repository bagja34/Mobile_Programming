package com.example.mobile_programming;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;


public class LoginActivity extends AppCompatActivity{
    private TextInputEditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword, tvRegister;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi komponen
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvRegister = findViewById(R.id.tvRegister);

        // Aksi tombol login
        btnLogin.setOnClickListener(view -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.equals("user@example.com") && password.equals("password")) {
                Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                // TODO: Navigasi ke halaman utama
            } else {
                Toast.makeText(LoginActivity.this, "Email atau Password salah", Toast.LENGTH_SHORT).show();
            }
        });

        // Aksi lupa password
        tvForgotPassword.setOnClickListener(view ->
                Toast.makeText(LoginActivity.this, "Fitur lupa password belum tersedia", Toast.LENGTH_SHORT).show()
        );

        // Aksi daftar
        tvRegister.setOnClickListener(view ->
                Toast.makeText(LoginActivity.this, "Navigasi ke halaman pendaftaran", Toast.LENGTH_SHORT).show()
        );
    }
}
