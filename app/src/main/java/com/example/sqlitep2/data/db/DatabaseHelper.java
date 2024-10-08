package com.example.sqlitep2.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlitep2.util.Constants;


public class DatabaseHelper extends SQLiteOpenHelper{
    public DatabaseHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla de usuarios
        String CREATE_USERS_TABLE = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "email TEXT" +
                ")";
        db.execSQL(CREATE_USERS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_PRODUCT);
        onCreate(db);
    }

}
