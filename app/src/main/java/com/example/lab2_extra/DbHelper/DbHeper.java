package com.example.lab2_extra.DbHelper;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHeper extends SQLiteOpenHelper {
    public static final String Db_name = "Todo_Management";

    public DbHeper(Context context) {


        super(context, Db_name, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tb_todo = "create table student(id integer primary key autoincrement,name text,msv text,date text, type text,status integer)";
        sqLiteDatabase.execSQL(tb_todo);

    }
    //được gọi khi nâng cấp version
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i!=i1){
            sqLiteDatabase.execSQL("DROP table IF EXISTS student");
            onCreate(sqLiteDatabase);
        }
    }
}
