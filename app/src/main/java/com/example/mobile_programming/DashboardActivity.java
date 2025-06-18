package com.example.mobile_programming;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DashboardActivity extends NavActivity {

    private ImageButton btnNotification, btnSettings;
    private TextView txtBalance, txtIncome, txtExpense;
    private RecyclerView recyclerViewTransactions;

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Bottom Navigation setup
        setupBottomNav();

        // DB setup
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();

        // View initialization
        btnNotification = findViewById(R.id.btnNotification);
        btnSettings = findViewById(R.id.btnSettings);
        txtBalance = findViewById(R.id.txtBalance);
        txtIncome = findViewById(R.id.txtIncome);
        txtExpense = findViewById(R.id.txtExpense);
        recyclerViewTransactions = findViewById(R.id.recyclerViewTransactions);

        btnNotification.setOnClickListener(v ->
                Toast.makeText(this, "Belum ada notifikasi baru.", Toast.LENGTH_SHORT).show()
        );

        btnSettings.setOnClickListener(v ->
                Toast.makeText(this, "Navigasi ke pengaturan (belum dibuat).", Toast.LENGTH_SHORT).show()
        );

        // Load actual dashboard data
        loadDashboardData();
    }

    private void loadDashboardData() {
        double totalIncome = 0.0;
        double totalExpense = 0.0;
        double balance;

        ArrayList<Transaction> latestTransactions = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM transaksi ORDER BY id DESC", null);

        int count = 0;
        if (cursor.moveToFirst()) {
            do {
                double amount = cursor.getDouble(cursor.getColumnIndexOrThrow("jumlah"));
                int kategoriId = cursor.getInt(cursor.getColumnIndexOrThrow("kategori_id"));
                String kategori = getKategoriName(kategoriId);

                Log.d("DB_DEBUG", "Kategori: " + kategori + ", Jumlah: " + amount);

                if (kategori.equalsIgnoreCase("pemasukkan")) {
                    totalIncome += amount;
                } else if (kategori.equalsIgnoreCase("pengeluaran")) {
                    totalExpense += amount;
                }

                if (count < 3) {
                    String desc = cursor.getString(cursor.getColumnIndexOrThrow("deskripsi"));
                    String tanggal = cursor.getString(cursor.getColumnIndexOrThrow("tanggal"));
                    latestTransactions.add(new Transaction(desc, tanggal, amount));
                    count++;
                }

            } while (cursor.moveToNext());
        }
        cursor.close();

        balance = totalIncome - totalExpense;

        // Format to Rupiah
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        txtBalance.setText(formatRupiah.format(balance));
        txtIncome.setText(formatRupiah.format(totalIncome));
        txtExpense.setText(formatRupiah.format(totalExpense));

        // RecyclerView setup
        recyclerViewTransactions.setLayoutManager(new LinearLayoutManager(this));
        TransactionAdapter adapter = new TransactionAdapter(latestTransactions);
        recyclerViewTransactions.setAdapter(adapter);
    }

    private String getKategoriName(int kategoriId) {
        String name = "unknown";
        Cursor cursor = db.rawQuery("SELECT kategori FROM kategori WHERE id = ?", new String[]{String.valueOf(kategoriId)});
        if (cursor.moveToFirst()) {
            name = cursor.getString(0);
        } else {
            Log.w("DB_WARNING", "Kategori ID " + kategoriId + " tidak ditemukan!");
        }
        cursor.close();
        return name;
    }
}
