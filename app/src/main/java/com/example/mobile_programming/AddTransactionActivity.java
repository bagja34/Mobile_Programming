package com.example.mobile_programming;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddTransactionActivity extends AppCompatActivity {

    EditText editTitle, editAmount;
    RadioGroup radioCategory;
    Spinner spinnerType;
    Button buttonSave;

    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        // Init DB
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        // Init Views
        editTitle = findViewById(R.id.editTitle);
        editAmount = findViewById(R.id.editAmount);
        radioCategory = findViewById(R.id.radioCategory);
        spinnerType = findViewById(R.id.spinnerType);
        buttonSave = findViewById(R.id.buttonSave);

        // Setup Spinner
        String[] types = {"pemasukkan", "pengeluaran"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        // Save Button
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTransaction();
            }
        });
    }

    private void saveTransaction() {
        String title = editTitle.getText().toString().trim();
        String amount = editAmount.getText().toString().trim();
        int selectedId = radioCategory.getCheckedRadioButtonId();
        RadioButton selectedRadio = findViewById(selectedId);
        String category = selectedRadio != null ? selectedRadio.getText().toString() : "";
        String type = spinnerType.getSelectedItem().toString();

        if (title.isEmpty() || amount.isEmpty() || category.isEmpty()) {
            Toast.makeText(this, "Please complete all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();

        values.put("user_id", 1);

        values.put("jumlah", Double.parseDouble(amount));

        values.put("deskripsi", title);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = sdf.format(new Date());
        values.put("tanggal", currentDate);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert("transaksi", null, values);

        Toast.makeText(this, "Transaction saved!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
