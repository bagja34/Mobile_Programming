package com.example.mobile_programming;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    RecyclerView recyclerView;
    List<Transaction> listTransaction;
    TransactionAdapterHistory adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = new DatabaseHelper(this);
        loadTransaksi();
    }
    private void loadTransaksi(){
        listTransaction = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllTransaksi();
        String yak;
        if (cursor.moveToFirst()) {
            yak = cursor.getString(3);
            do {
                listTransaction.add(new Transaction(
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getDouble(3),
                        cursor.getInt(2)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter = new TransactionAdapterHistory(listTransaction,this);
        recyclerView.setAdapter(adapter);
    }
}