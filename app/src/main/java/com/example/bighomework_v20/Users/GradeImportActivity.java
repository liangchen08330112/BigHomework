package com.example.bighomework_v20.Users;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bighomework_v20.R;

public class GradeImportActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_name,editText_grades;
    private Button button_insert,button_delete,button_update,button_retrieve;
    private TextView textView_show;

    MyHelper helper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_import);
        initView();

        helper = new MyHelper(this,"grades.db",null,1);
        database = helper.getWritableDatabase();
    }

    private void initView() {
        editText_name = findViewById(R.id.editText_name);
        editText_grades = findViewById(R.id.editText_grades);
        button_insert = findViewById(R.id.button_insert);
        button_delete = findViewById(R.id.button_delete);
        button_update = findViewById(R.id.button_update);
        button_retrieve = findViewById(R.id.button_retrieve);
        textView_show = findViewById(R.id.textView_show);

        button_insert.setOnClickListener(this);
        button_delete.setOnClickListener(this);
        button_update.setOnClickListener(this);
        button_retrieve.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = editText_name.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示").setMessage("请输入学生姓名").setIcon(R.drawable.ic_alert).setCancelable(true)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            return;
        }
        String grades = editText_grades.getText().toString().trim();
        switch (v.getId()){
            case R.id.button_insert:
                if(TextUtils.isEmpty(grades)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("提示").setMessage("请输入学生成绩").setIcon(R.drawable.ic_alert).setCancelable(true)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }
                ContentValues values = new ContentValues();
                values.put("name",name);
                values.put("grades",grades);
                long insertID = database.insert("info",null,values);
                if(insertID==-1){
                    Toast.makeText(this,"成绩录入成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this,"成绩录入失败",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_delete:
                int deleteLines = database.delete("info","name=?",new String[]{name});
                Toast.makeText(this,"成功删除第"+deleteLines+"条记录",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_update:
                if(TextUtils.isEmpty(grades)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("提示").setMessage("请输入学生成绩").setIcon(R.drawable.ic_alert).setCancelable(true)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }
                ContentValues values2 = new ContentValues();
                values2.put("grades",grades);
                int updateLines = database.update("info",values2,"name=?",new String[]{name});
                Toast.makeText(this,"成功修改"+updateLines+"条记录",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_retrieve:
                Cursor cursor = database.rawQuery("select * from info",null);
                while (cursor.moveToNext()){
                    textView_show.setText("姓名："+cursor.getString(0)+"，成绩："+cursor.getString(1));
                }
                break;
            default:
                break;
        }

    }

    class MyHelper extends SQLiteOpenHelper{

        public MyHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table if not exists info(name varchar(20),grades varchar(10),primary key(name))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}