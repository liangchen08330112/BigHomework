package com.example.bighomework_v20.Users;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
        switch (v.getId()){
            case R.id.btn_registe:
                //点击注册按钮，跳转进入注册页面
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case  R.id.btn_up:
                MyDao dao = new MyDao(this);
                Log.i("TAG", "onClick: "+etUsername.getText().toString().trim()+"    " +etPassword.getText().toString().trim());
                if( dao.check_password(etUsername.getText().toString().trim(),etPassword.getText().toString().trim())){
                    startActivity(new Intent(this, MainActivity.class));
                    Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setIcon(R.drawable.ic_alert).setTitle("提示").setMessage("用户名或密码错误，请重试。").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                break;
        }
    }
}