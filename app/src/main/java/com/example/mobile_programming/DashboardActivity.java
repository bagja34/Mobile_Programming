package com.example.mobile_programming;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private ImageButton btnNotification, btnSettings;
    private TextView txtBalance, txtIncome, txtExpense;
    private RecyclerView recyclerViewTransactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Inisialisasi komponen
        btnNotification = findViewById(R.id.btnNotification);
        btnSettings = findViewById(R.id.btnSettings);
        txtBalance = findViewById(R.id.txtBalance);
        txtIncome = findViewById(R.id.txtIncome);
        txtExpense = findViewById(R.id.txtExpense);
        recyclerViewTransactions = findViewById(R.id.recyclerViewTransactions);

        // Aksi tombol notifikasi
        btnNotification.setOnClickListener(v ->
                Toast.makeText(this, "Belum ada notifikasi baru.", Toast.LENGTH_SHORT).show()
        );

        // Aksi tombol settings
        btnSettings.setOnClickListener(v ->
                Toast.makeText(this, "Navigasi ke pengaturan (belum dibuat).", Toast.LENGTH_SHORT).show()
        );

        // Set data dummy saldo, pemasukan & pengeluaran
        txtBalance.setText("$425");
        txtIncome.setText("$1200");
        txtExpense.setText("-$775");

        // Setup RecyclerView dummy transaksi
        recyclerViewTransactions.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> dummyList = new ArrayList<>();
        dummyList.add("Grocery Shopping - $50");
        dummyList.add("Salary - $1200");
        dummyList.add("Electric Bill - $100");
        dummyList.add("Dinner - $25");

        TransactionAdapter adapter = new TransactionAdapter(dummyList);
        recyclerViewTransactions.setAdapter(adapter);

        // Bottom nav bar: disesuaikan di XML melalui <include>
    }
}
