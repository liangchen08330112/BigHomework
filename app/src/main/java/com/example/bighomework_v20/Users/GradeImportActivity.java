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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bighomework_v20.R;

public class GradeImportActivity extends AppCompatActivity implements View.OnClickListener {
    private MyHelper helper;
    private EditText editText_name,editText_grades;
    private Button button_insert,button_delete,button_update,button_retrieve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_import);
        initView();
    }

    private void initView() {
        editText_name = findViewById(R.id.editText_name);
        editText_grades = findViewById(R.id.editText_grades);
        button_insert = findViewById(R.id.button_insert);
        button_delete = findViewById(R.id.button_delete);
        button_update = findViewById(R.id.button_update);
        button_retrieve = findViewById(R.id.button_retrieve);

        button_insert.setOnClickListener(this);
        button_delete.setOnClickListener(this);
        button_update.setOnClickListener(this);
        button_retrieve.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name,grades;
        SQLiteDatabase database;
        ContentValues values;
        switch (v.getId()){
            case R.id.button_insert:
                name = editText_name.getText().toString().trim();
                grades = editText_grades.getText().toString().trim();
                database = helper.getWritableDatabase();
                values = new ContentValues();
                values.put("name",name);
                values.put("grades",grades);
                database.insert("info",null,values);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示").setIcon(R.drawable.ic_zhengque).setMessage("录入成功");
                builder.setCancelable(true);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                //关闭数据库
                database.close();
                break;
            case R.id.button_delete:

                break;
            case R.id.button_update:
                database = helper.getWritableDatabase();
                values = new ContentValues();
                values.put("grades",grades = editText_grades.getText().toString().trim());
                database.update("info",values,"name=?",new String[]{editText_name.getText().toString().trim()});
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("提示").setIcon(R.drawable.ic_zhengque).setMessage("成绩修改成功");
                builder1.setCancelable(true);
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog1 = builder1.create();
                dialog1.show();
                break;
            case R.id.button_retrieve:
                //获得可读权限
                database = helper.getReadableDatabase();
                //Cursor集游标，查询数据
                Cursor cursor = database.query("info",null,null,null,null,null,null);
                //如果未获取数据
                if(cursor.getCount()==0){
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                    builder2.setIcon(R.drawable.ic_jinggao).setTitle("提示").setMessage("暂无数据！");
                    builder2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder2.setCancelable(true);
                    AlertDialog dialog2 = builder2.create();
                    dialog2.show();
                }else{ //获取到数据后从第一个数据开始显示文本
                    cursor.moveToFirst();
//                    textView_show.setText("姓名："+cursor.getString(1)+"，成绩："+cursor.getString(2));
                }  //往下读一行
                while (cursor.moveToNext()) {
//                    textView_show.append("\n"+"姓名："+cursor.getString(1)+"，成绩："+cursor.getString(2));
                }
                Toast.makeText(this,"成绩查询完毕",Toast.LENGTH_SHORT).show();
                //关闭集游标
                cursor.close();
                //关闭数据库
                database.close();
                break;
        }
    }

    private class MyHelper extends SQLiteOpenHelper {
        public MyHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, "grades.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}