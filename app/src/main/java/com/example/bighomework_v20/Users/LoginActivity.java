package com.example.bighomework_v20.Users;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bighomework_v20.Dao.MyDao;
import com.example.bighomework_v20.Dao.NewBase;
import com.example.bighomework_v20.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imageView;
    private EditText etUsername;
    private EditText etPassword;
    private Button btnRegiste;
    private Button btnUp;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        judge();//用于判断是否是第一次打开页面
        initView();
    }
    /*
    * 仅仅是判断是判断是否是第一次打开
    * */
    private void judge(){
        SharedPreferences sp=getSharedPreferences("judge",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        int num =sp.getInt("times",0);
        if (num==0){
            num++;
            editor.putInt("times",num);
            editor.commit();
            Intent intent=new Intent(LoginActivity.this,ListViewPage2.class);
            startActivity(intent);
        }else {
            num++;
            editor.putInt("times",num);
            editor.commit();
        }
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.imageView);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnRegiste = (Button) findViewById(R.id.btn_registe);
        btnUp = (Button) findViewById(R.id.btn_up);
        btnRegiste.setOnClickListener(this);
        btnUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String nm = etUsername.getText().toString().trim();
        String pw = etPassword.getText().toString().trim();
        switch (v.getId()) {
            case R.id.btn_registe:
                //点击注册按钮，跳转进入注册页面
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.btn_up:
                /*
                 * 逻辑：首先判断是否为空，如实为空，则自动报错
                 * 2.在此判断输入的用户是否在数据库里面，并且进行比较，如实正确，则进行登录
                 * */
                NewBase mybase = new NewBase(this);
                if (nm.equals("") || pw.equals("")) {
                    Toast.makeText(this, "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(this, MainActivity.class));
//                    Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    String condition = "用户不存在";

                    Cursor cursor = mybase.getDb().rawQuery("select name,password from users where name=?", new String[]{nm});
                    while (cursor.moveToNext()) {
                        condition = cursor.getString(1);
                    }

                    cursor.close();//关门游标
                    if (condition.equals("用户不存在")) {
                        Toast.makeText(this, "用户不存在", Toast.LENGTH_SHORT).show();
                        return;
                    }else {
                        if (condition.equals(pw)){
                            startActivity(new Intent(this, MainActivity.class));
                            Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


//                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    builder.setIcon(R.drawable.ic_alert).setTitle("提示").setMessage("用户名或密码错误，请重试。").setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
//                        }
//                    });
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
                break;
        }

        }
    }
