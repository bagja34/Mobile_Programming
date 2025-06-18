package com.example.mobile_programming;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class NavActivity extends AppCompatActivity {

    protected void setupBottomNav() {
        ImageButton navHome = findViewById(R.id.navHome);
        ImageButton navAdd = findViewById(R.id.navAdd);
        ImageButton navProfile = findViewById(R.id.navProfile);
        ImageButton navHistory = findViewById(R.id.navHistory);

        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        navAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavActivity.this, AddTransactionActivity.class);
                startActivity(intent);
                finish();
            }
        });

        navProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        navHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavActivity.this, HistoryActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
