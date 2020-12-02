package com.example.shoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    final static String DB_NAME = "list.db";
    final static String TABLE_NAME = "shoppinglist";
    final static String CREATE = "CREATE TABLE " + TABLE_NAME + "( `_id` INTEGER PRIMARY KEY AUTOINCREMENT, `goods` TEXT NOT NULL, `cost` INTEGER NOT NULL, `date` TEXT NOT NULL )";
    private static final int DATABASE_VERSION = 10;

    public DBHelper (Context context){
        super (context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE);
        db.execSQL("INSERT INTO shoppinglist VALUES(1, 'Pen', 112, '01.12.2020')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP DATABASE " + DB_NAME);
        this.onCreate(db);
    }

    public void save(SQLiteDatabase db, String query){
        db.execSQL(query);
    }
}
