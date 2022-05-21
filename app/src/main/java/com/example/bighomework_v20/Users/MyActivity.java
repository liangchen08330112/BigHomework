package com.example.bighomework_v20.Users;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bighomework_v20.R;

public class MyActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton imageButton_quit,imageButton_home,imageButton_grades,imageButton_my;
    private TextView textView_quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        initView();
    }

    private void initView() {
        imageButton_home = (ImageButton) findViewById(R.id.imageButton_home);
        imageButton_grades = (ImageButton) findViewById(R.id.imageButton_grades);
        imageButton_quit = (ImageButton) findViewById(R.id.imageButton_quit);
        textView_quit = (TextView) findViewById(R.id.textView_quit);

        imageButton_home.setOnClickListener(this);
        imageButton_grades.setOnClickListener(this);
        imageButton_quit.setOnClickListener(this);
        textView_quit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButton_home:
                startActivity(new Intent(MyActivity.this,MainActivity.class));
                break;
            case R.id.imageButton_grades:
                startActivity(new Intent(MyActivity.this,GradeImportActivity.class));
                break;
            case R.id.imageButton_quit:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示").setIcon(R.drawable.ic_alert).setMessage("您确定要退出登录吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MyActivity.this,LoginActivity.class));
                    }
                });
                builder.setNegativeButton("手滑了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.textView_quit:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("提示").setIcon(R.drawable.ic_alert).setMessage("您确定要退出登录吗？");
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MyActivity.this,LoginActivity.class));
                    }
                });
                builder1.setNegativeButton("手滑了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog1 = builder1.create();
                dialog1.show();
                break;
            default:
                break;
        }
    }
}