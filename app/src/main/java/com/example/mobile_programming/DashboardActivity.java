package com.example.mobile_programming;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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

        // Nav
        setupBottomNav();

        // DB
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();

        // Views
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

        // Load real data
        loadDashboardData();
    }

    private void loadDashboardData() {
        double totalIncome = 0.0;
        double totalExpense = 0.0;

        ArrayList<Transaction> latestTransactions = new ArrayList<>();

        // Query semua transaksi ordered by id DESC → terbaru di atas
        Cursor cursor = db.rawQuery("SELECT * FROM transaksi ORDER BY id DESC", null);

        int count = 0;
        if (cursor.moveToFirst()) {
            do {
                double amount = cursor.getDouble(cursor.getColumnIndexOrThrow("jumlah"));

                // Ambil kategori untuk bedakan income/expense
                int kategoriId = cursor.getInt(cursor.getColumnIndexOrThrow("kategori_id"));
                String kategori = getKategoriName(kategoriId);

                if (kategori.equalsIgnoreCase("pemasukkan")) {
                    totalIncome += amount;
                } else if (kategori.equalsIgnoreCase("pengeluaran")) {
                    totalExpense += amount;
                }

                // Tambahkan ke list terbaru (hanya 3)
                if (count < 3) {
                    String desc = cursor.getString(cursor.getColumnIndexOrThrow("deskripsi"));
                    String tanggal = cursor.getString(cursor.getColumnIndexOrThrow("tanggal"));
                    latestTransactions.add(new Transaction(desc, tanggal, amount));
                    count++;
                }

            } while (cursor.moveToNext());
        }
        cursor.close();

        double balance = totalIncome - totalExpense;

        txtBalance.setText("$" + String.format("%.2f", balance));
        txtIncome.setText("$" + String.format("%.2f", totalIncome));
        txtExpense.setText("-$" + String.format("%.2f", totalExpense));

        recyclerViewTransactions.setLayoutManager(new LinearLayoutManager(this));
        TransactionAdapter adapter = new TransactionAdapter(latestTransactions);
        recyclerViewTransactions.setAdapter(adapter);
    }

    // Ambil nama kategori dari id
    private String getKategoriName(int kategoriId) {
        String name = "";
        Cursor cursor = db.rawQuery("SELECT kategori FROM kategori WHERE id = ?", new String[]{String.valueOf(kategoriId)});
        if (cursor.moveToFirst()) {
            name = cursor.getString(0);
        }
        cursor.close();
        return name;
    }
}
