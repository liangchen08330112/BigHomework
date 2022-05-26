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

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class GradeImportActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_name,editText_grades,editText_search,et_xuehao;
    private Button button_insert,button_delete,button_update,button_search,button_seeAll;
    private LinearLayout linearLayout;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_import);
        Bmob.initialize(this, "b5275c7bd39777e5666d5832ba50478e");
        initView();
        db = SQLiteDatabase.openOrCreateDatabase(this.getFilesDir().toString()+"/grades.db",null);
        db.execSQL("create table if not exists users(name varchar(10),grades varchar(10),number varchar(50),primary key(number))");
    }

    private void initView() {
        editText_name = (EditText) findViewById(R.id.editText_name);
        editText_grades = (EditText) findViewById(R.id.editText_grades);
        editText_search = (EditText) findViewById(R.id.editText_search);
        et_xuehao=findViewById(R.id.et_xuehao);
        button_insert = (Button) findViewById(R.id.button_insert);
        button_delete = (Button) findViewById(R.id.button_delete);
        button_update = (Button) findViewById(R.id.button_update);
        button_search = (Button) findViewById(R.id.button_search);
        button_seeAll = (Button) findViewById(R.id.button_seeAll);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        button_insert.setOnClickListener(this);
        button_delete.setOnClickListener(this);
        button_update.setOnClickListener(this);
        button_search.setOnClickListener(this);
        button_seeAll.setOnClickListener(this);
    }
    /*
    * 封装一个方法，用于判断用户名或密码或者学号是否为空
    * */
    private static boolean  isNull(String name,String grades,String xuehao){
        if (name.equals("")||grades.equals("")||xuehao.equals("")){
            return false;
        }else {
            return true;
        }
    }
    /*
    * 再封装一个方法，用于显示提示框
    * */
    private  void tip(String content){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setIcon(R.drawable.ic_alert).setMessage(content).setCancelable(true)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    /*
    * 作用：用于将数据上传到云端
    * 数据：姓名，成绩，学号
    * */
    private void sendCloud(String name,String grade,String number){
        StudentGrade xuezha=new StudentGrade(name,grade,number);
        xuezha.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if(e==null){
                    Toast.makeText(getApplicationContext(), "添加数据成功，返回objectId为："+objectId, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "创建数据失败：" + "请确保学号正确！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    /*
    * 点击事件回调方法
    * */
    @Override
    public void onClick(View v) {
        String name = editText_name.getText().toString().trim();
        String grades = editText_grades.getText().toString().trim();
        String num=et_xuehao.getText().toString().trim();

        switch (v.getId()){
            case R.id.button_insert:  //添加成绩
                if (isNull(name,grades,num)) {
                    try {
                        db.execSQL("insert into users(name,grades,number) values(?,?,?)", new String[]{name, grades,num});
                        Toast.makeText(this, "成绩录入成功", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "成绩录入失败,请确保学号正确！", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    tip("请确保成绩，学号，姓名不为空！");
                }
                break;
            case R.id.button_delete:  //删除成绩
                int deleteLines  = db.delete("users","name=? and number=?",new String[]{name,num});
                StudentGrade s1=new StudentGrade();
                s1.setNumber(num);
                s1.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                        }else{
                            Toast.makeText(GradeImportActivity.this, "云端删除成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Toast.makeText(this,"删除"+deleteLines+"条记录",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_update:  //修改成绩
                String grades1 = editText_grades.getText().toString().trim();
                if(TextUtils.isEmpty(grades)){
                    tip("成绩为空");
                    return;
                }else if (TextUtils.isEmpty(name)){
                    tip("姓名为空");
                    return;
                }else if (TextUtils.isEmpty(num)){
                    tip("学号为空");
                    return;
                }
                ContentValues values = new ContentValues();
                values.put("grades",grades1);
                long affectLines = db.update("users",values,"name=? and number=?",new String[]{name,num});
                Toast.makeText(this,"修改"+affectLines+"行记录",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_search:  //根据姓名和学号搜索成绩
                String search = editText_search.getText().toString().trim();
                if(TextUtils.isEmpty(search)){
                    tip("姓名为空");
                    return;
                }else if (TextUtils.isEmpty(num)){
                    tip("学号为空");
                    return;
                }
                String result = "暂无数据";
                Cursor cursor = db.rawQuery("select * from users where name=?",new String[]{search});
                while (cursor.moveToNext()){
                    result = cursor.getString(1); //获取第二列数据（第一列是姓名，第二列是成绩）
                }
                tip("姓名："+search+"，成绩："+result+"。");
                break;
            case R.id.button_seeAll:  //展示所有成绩
                Cursor cursor_seeAll = db.rawQuery("select * from users",null);
                /**
                 * 2022年5月21日21:57修改：
                 * 不再在xml布局中单独添加TextView，而是为新建的LinearLayout设置id并关联资源，在该布局中新建一个TextView对象，
                 * 并根据数据库中的数据来改变TextView显示的内容。
                 */
                while (cursor_seeAll.moveToNext()){
                    TextView textView = new TextView(this);
                    TextView textView1=new TextView(this);
                    textView.setText("姓名："+cursor_seeAll.getString(0)+"，成绩："+cursor_seeAll.getString(1)+"。"
                    +"学号"+cursor_seeAll.getString(2));
                    linearLayout.addView(textView);
                    sendCloud(cursor_seeAll.getString(0),cursor_seeAll.getString(1),cursor_seeAll.getString(2));
                }
                Toast.makeText(this,"检索完毕",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}