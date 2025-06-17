package com.example.mobile_programming;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "money_managemeny.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USER = "user";
    private static final String TABLE_KATEGORI = "kategori";
    private static final String TABLE_TRANSAKSI = "transaksi";
    private static final String TABLE_ANGGARAN = "anggaran";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAMA = "nama";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "pass";
    private static final String COLUMN_CREATED_AT = "created";
    private static final String COLUMN_USER = "user_id";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_KATEGORI = "kategori";
    private static final String COLUMN_KATEGORI_ID = "kategori_id";
    private static final String COLUMN_JUMLAH = "jumlah";
    private static final String COLUMN_DESC = "deskripsi";
    private static final String COLUMN_DATE = "tanggal";
    private static final String COLUMN_MONTH = "bulan";
    private static final String COLUMN_YEAR = "tahun";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableUser = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAMA + " VARCHAR(100) NOT NULL, " +
                COLUMN_EMAIL + " VARCHAR(100) UNIQUE NOT NULL, "+
                COLUMN_PASSWORD + " TEXT NOT NULL, " +
                COLUMN_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
        String createTableKategori = "CREATE TABLE " + TABLE_KATEGORI + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER + " INTEGER REFERENCES user(id) ON DELETE CASCADE, " +
                COLUMN_KATEGORI + " VARCHAR(100) NOT NULL, "+
                COLUMN_TYPE + " CHECK (type in ('pemasukkan', 'pengeluaran')) NOT NULL, " +
                COLUMN_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
        String createTableTransaksi = "CREATE TABLE " + TABLE_TRANSAKSI + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER + " INTEGER REFERENCES user(id) ON DELETE CASCADE, " +
                COLUMN_KATEGORI_ID + " INTEGER REFERENCES kategori(id) ON DELETE SET NULL, "+
                COLUMN_JUMLAH + " DECIMAL(15,2) NOT NULL, " +
                COLUMN_DESC + " TEXT, " +
                COLUMN_DATE + " DATE NOT NULL, " +
                COLUMN_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
        String createTableAnggaran = "CREATE TABLE " + TABLE_ANGGARAN + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER + " INTEGER REFERENCES user(id) ON DELETE CASCADE, " +
                COLUMN_KATEGORI_ID + " INTEGER REFERENCES kategori(id) ON DELETE SET NULL, "+
                COLUMN_MONTH + " INTEGER NOT NULL CHECK (bulan BETWEEN 1 AND 12), " +
                COLUMN_YEAR + " DECIMAL(15,2) NOT NULL, " +
                COLUMN_JUMLAH + " DECIMAL(15,2) NOT NULL, " +
                COLUMN_CREATED_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                " UNIQUE ("+COLUMN_USER+","+COLUMN_KATEGORI_ID+","+COLUMN_MONTH+","+COLUMN_YEAR+")"+
                ")";
        db.execSQL(createTableUser);
        db.execSQL(createTableKategori);
        db.execSQL(createTableTransaksi);
        db.execSQL(createTableAnggaran);
        // Tambahkan data sample
        db.execSQL("INSERT INTO "+TABLE_USER + "(" +
                COLUMN_NAMA+","+
                COLUMN_EMAIL+","+
                COLUMN_PASSWORD+
                ")" + " VALUES ('Budi Santoso','budisans@gmail.com','123456')");
        db.execSQL("INSERT INTO "+TABLE_USER + "(" +
                COLUMN_NAMA+","+
                COLUMN_EMAIL+","+
                COLUMN_PASSWORD+
                ")" + "  VALUES ('Adi Nugroho','adinugroho@gmail.com','123456')");
        db.execSQL("INSERT INTO "+TABLE_USER + "(" +
                COLUMN_NAMA+","+
                COLUMN_EMAIL+","+
                COLUMN_PASSWORD+
                ")" + " VALUES ('Siti Rahmawati','sitiramawati@gmail.com','123456')");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSAKSI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KATEGORI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANGGARAN);
        onCreate(db);
    }
    public Cursor getAllUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USER, null);
    }
    public Cursor getAllTransaksi() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TRANSAKSI, null);
    }
    public Cursor getAllKategori() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_KATEGORI, null);
    }
    public Cursor getAllAnggaran() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ANGGARAN, null);
    }
    // Tambahkan di DatabaseHelper
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM user WHERE email = ? AND pass = ?",
                new String[]{email, password}
        );
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

}

