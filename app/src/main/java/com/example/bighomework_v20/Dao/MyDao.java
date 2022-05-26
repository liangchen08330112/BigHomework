package com.example.bighomework_v20.Dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDao {
    private final MySQLiteHelper helper;

    public MyDao(Context context) {
        this.helper = new MySQLiteHelper(context);

    }


    public void add_user(String user_name, String user_password){
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("insert into "+Constants.TABLE_NAME_USER+"(user_name,user_password) values(?,? )",
                new Object[]{user_name,user_password});}
    @SuppressLint("Range")
    public  boolean check_password(String user_name, String user_password){
        SQLiteDatabase db = helper.getReadableDatabase();
        String name = "",password="";
        @SuppressLint("Recycle")
        Cursor cursor = db.rawQuery("select * from "+ Constants.TABLE_NAME_USER,null);
        while(cursor.moveToNext()){
            name= cursor.getString(cursor.getColumnIndex("user_name"));
            password = cursor.getString(cursor.getColumnIndex("user_password"));
        }
        cursor.close();
        db.close();
        if (name.equals(user_name)|password.equals(user_password)){
            return  true;
        }else {
            return false;
        }
    }
}
