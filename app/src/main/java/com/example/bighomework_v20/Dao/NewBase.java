package com.example.bighomework_v20.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class NewBase {
    SQLiteDatabase db;
    public NewBase(Context context){
        db=SQLiteDatabase.openOrCreateDatabase(context.getFilesDir().toString()+"/firstBase.db",null);
        db.execSQL("create table if not exists users(name varchar(50),password varchar(50),primary key(name))");
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }
}
