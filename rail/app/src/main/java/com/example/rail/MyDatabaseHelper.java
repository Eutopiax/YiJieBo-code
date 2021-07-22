package com.example.rail;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    static String name="user.db";
    static int dbVersion=1;
    public MyDatabaseHelper(Context context) {
        super(context, name, null, dbVersion);
    }


    public void onCreate(SQLiteDatabase db) {
        String sql="create table if not exists user(id integer primary key autoincrement,username varchar(20),password varchar(20),age integer,sex varchar(2),image BLOB)";
        String sql1="create table if not exists orderr(id integer primary key autoincrement,date varchar(20),startdestination varchar(20),enddestinatioin varchar(20),usetime varchar(20))";

        db.execSQL(sql);
        db.execSQL(sql1);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}