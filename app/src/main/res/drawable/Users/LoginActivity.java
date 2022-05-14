package com.example.myloginactivity.Users;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myloginactivity.Dao.MyDao;
import com.example.myloginactivity.MainActivity;
import com.example.myloginactivity.R;

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
        initView();
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.imageView);
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnRegiste = (Button) findViewById(R.id.btn_registe);
        btnUp = (Button) findViewById(R.id.btn_up);
        textView = (TextView) findViewById(R.id.textView);
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

                }
                else {
                    Toast.makeText(this,"wrong",Toast.LENGTH_LONG).show();
                }
                break;

        }
    }
}