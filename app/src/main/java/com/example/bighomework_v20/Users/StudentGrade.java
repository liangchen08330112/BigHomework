package com.example.bighomework_v20.Users;

import cn.bmob.v3.BmobObject;
/*
* 用于数据库存储数据
* 只可以存名字和成绩
* 存入后会自动返回一个ID
* */

public class StudentGrade extends BmobObject {
    String Name;
    String Grade;
    String Number;

    public StudentGrade(String name,String grade,String num){
        Name=name;
        Grade=grade;
        Number=num;
    }
    public StudentGrade(){

    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }
}
