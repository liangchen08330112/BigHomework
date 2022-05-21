package com.example.bighomework_v20.Users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.bighomework_v20.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton imageButton_import,imageButton_home,imageButton_grades,imageButton_my;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        imageButton_import = findViewById(R.id.imageButton_import);
        imageButton_home = findViewById(R.id.imageButton_home);
        imageButton_grades = findViewById(R.id.imageButton_grades);
        imageButton_my = findViewById(R.id.imageButton_my);

        imageButton_import.setOnClickListener(this);
        imageButton_grades.setOnClickListener(this);
        imageButton_my.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButton_import:
                startActivity(new Intent(MainActivity.this, GradeImportActivity.class));
                break;
            case R.id.imageButton_grades:
                startActivity(new Intent(MainActivity.this,GradeImportActivity.class));
                break;
            case R.id.imageButton_my:
                startActivity(new Intent(MainActivity.this,MyActivity.class));
                break;
            default:
                break;
        }
    }
}