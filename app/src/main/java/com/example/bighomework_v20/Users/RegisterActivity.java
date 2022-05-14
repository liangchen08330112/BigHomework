package com.example.bighomework_v20.Users;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bighomework_v20.Dao.MyDao;
import com.example.bighomework_v20.R;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etUsername;
    private EditText etPasswordSet;
    private EditText etPasswordComfirm;
    private Button btnConfirm;
    String name=null;
    String password=null;
    String password_confirm=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        etUsername = (EditText) findViewById(R.id.et_username);
        etPasswordSet = (EditText) findViewById(R.id.et_password_set);
        etPasswordComfirm = (EditText) findViewById(R.id.et_password_comfirm);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm:
                if(isEmptyForText()){
                    if(isSamePassword()){
                        //将注册信息存入数据库
                        MyDao dao=new MyDao(this);
                        dao.add_user(name,password);
                        Toast.makeText(this,"注册成功",Toast.LENGTH_LONG).show();
                        //注册成功，返回登录页面
                        startActivity(new Intent(this,LoginActivity.class));
                    }

                }
                break;
        }
    }

    //判断输入内容是否为空
    public boolean isEmptyForText(){
        name=etUsername.getText().toString().trim();
        password=etPasswordSet.getText().toString().trim();
        password_confirm=etPasswordComfirm.getText().toString().trim();
//        if(name.isEmpty()|password.isEmpty()|password_confirm.isEmpty()){
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setIcon(R.drawable.ic_alert).setTitle("提示").setMessage("输入内容为空！");
//            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.dismiss();
//                }
//            });
//            AlertDialog dialog = builder.create();
//            dialog.show();
//            return false;
//        }
        if(name.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_alert).setTitle("提示").setMessage("用户名不得为空！");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }else if(password.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_alert).setTitle("提示").setMessage("密码不得为空！");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }else if(password_confirm.isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_alert).setTitle("提示").setMessage("您未确认密码！");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return true;
    }
    public boolean isSamePassword(){
        password=etPasswordSet.getText().toString().trim();
        password_confirm=etPasswordComfirm.getText().toString().trim();
        if(password.equals(password_confirm)){
            return true;
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.drawable.ic_alert).setTitle("提示").setMessage("两次输入的密码不一致，请重新输入。");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        return false;
    }
}