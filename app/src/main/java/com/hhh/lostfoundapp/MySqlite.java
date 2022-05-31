package com.hhh.lostfoundapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MySqlite extends SQLiteOpenHelper {
    private Context context;
    public MySqlite(@Nullable Context context, int version) {
        super(context, "lost.db", null, version);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table record("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name text," +
                "phone text," +
                "des text," +
                "date text," +
                "location text," +
                "statue text);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
