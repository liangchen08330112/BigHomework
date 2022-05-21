package com.example.bighomework_v20.Users;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bighomework_v20.R;

public class GradeImportActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_name,editText_grades,editText_search;
    private Button button_insert,button_delete,button_update,button_search,button_seeAll;
    private TextView textView_show;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_import);
        initView();

        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+"/grades.db",null);
        db.execSQL("create table if not exists users(name varchar(10),grades varchar(10),primary key(name))");
    }

    private void initView() {
        editText_name = (EditText) findViewById(R.id.editText_name);
        editText_grades = (EditText) findViewById(R.id.editText_grades);
        editText_search = (EditText) findViewById(R.id.editText_search);
        button_insert = (Button) findViewById(R.id.button_insert);
        button_delete = (Button) findViewById(R.id.button_delete);
        button_update = (Button) findViewById(R.id.button_update);
        button_search = (Button) findViewById(R.id.button_search);
        button_seeAll = (Button) findViewById(R.id.button_seeAll);
        textView_show = (TextView) findViewById(R.id.textView_show);

        button_insert.setOnClickListener(this);
        button_delete.setOnClickListener(this);
        button_update.setOnClickListener(this);
        button_search.setOnClickListener(this);
        button_seeAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = editText_name.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示").setIcon(R.drawable.ic_alert).setMessage("姓名为空").setCancelable(true)
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
        if(TextUtils.isEmpty(grades)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示").setIcon(R.drawable.ic_alert).setMessage("成绩为空").setCancelable(true)
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
        switch (v.getId()){
            case R.id.button_insert:
                try {
                    db.execSQL("insert into users(name,grades) values(?,?)",new String[]{name,grades});
                    Toast.makeText(this,"成绩录入成功",Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(this,"成绩录入失败",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_delete:
                int deleteLines  = db.delete("users","name=?",new String[]{name});
                Toast.makeText(this,"删除"+deleteLines+"条记录",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_update:
                String grades1 = editText_grades.getText().toString().trim();
                if(TextUtils.isEmpty(grades)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("提示").setIcon(R.drawable.ic_alert).setMessage("成绩为空").setCancelable(true)
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
                values.put("grades",grades1);
                long affectLines = db.update("users",values,"name=?",new String[]{name});
                Toast.makeText(this,"修改"+affectLines+"行记录",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_search:
                String search = editText_search.getText().toString().trim();
                if(TextUtils.isEmpty(search)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("提示").setIcon(R.drawable.ic_alert).setMessage("姓名为空").setCancelable(true)
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
                String result = "暂无数据";
                Cursor cursor = db.rawQuery("select * from users where name=?",new String[]{search});
                while (cursor.moveToNext()){
                    result = cursor.getString(1); //获取第二列数据（第一列是姓名，第二列是成绩）
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("搜索结果").setMessage("姓名："+search+"，成绩："+result+"。").setCancelable(true)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.button_seeAll:
                Cursor cursor_seeAll = db.rawQuery("select * from users",null);
                while (cursor_seeAll.moveToNext()){
                    textView_show.setText("姓名："+cursor_seeAll.getString(0)+"，成绩："+cursor_seeAll.getString(1)+"。");
                }
                Toast.makeText(this,"检索完毕",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}