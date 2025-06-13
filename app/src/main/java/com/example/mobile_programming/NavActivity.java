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
        ImageButton navStats = findViewById(R.id.navStats);

        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        navAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(NavActivity.this, AddActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });

//        navStats.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(NavActivity.this, StatsActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
