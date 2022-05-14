package com.example.myloginactivity.Users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.example.myloginactivity.R;

import java.util.ArrayList;

public class ListViewPage2 extends AppCompatActivity {
    ViewPager2 viewPager2;
    ArrayList<Fragment> pagelist;
    int theState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_page2);
        init();
    }

    private void init() {
        viewPager2=findViewById(R.id.list_viewpage2);
        storeData();

        MyAdapter myAdapter=new MyAdapter(ListViewPage2.this);
        viewPager2.setAdapter(myAdapter);
        //为ViewPage绑定时间监听器
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            //当屏幕滑动时
            @Override
            //形参解释
            //position:第几个页面
            // positionoffset：视图滑动便宜的百分比（向左滑动，也就是显示右边页面的百分比）
            //positionoffsetPixels：视图滑动偏移量的绝对值，有第二个参数成正比
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //是最后一页吗
                boolean isFinal = position == pagelist.size() - 1;
                //用户正在手指滑动屏幕吗
                boolean moving = theState == 1;
                //屏幕偏移了吗
                boolean ispianyi = positionOffset == 0;
                //如果三个条件都满足，我们就关闭关闭Acivity
                if (ispianyi == true && moving == true && isFinal == true) {
                    Intent intent = new Intent(ListViewPage2.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
            //state:0无事件  1正在滑动  2事件结束
            @Override
            public void onPageScrollStateChanged(int state) {
                theState=state;
            }
        });
    }
    //创建方法类
    private void storeData() {
        pagelist=new ArrayList<>();
        pagelist.add(new com.example.myloginactivity.Users.Page1());
        pagelist.add(new com.example.myloginactivity.Users.Page2());
        pagelist.add(new com.example.myloginactivity.Users.Page3());
    }
    //适配器类
    class MyAdapter extends FragmentStateAdapter {

        public MyAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return pagelist.get(position);
        }

        @Override
        public int getItemCount() {
            return pagelist.size();
        }
    }
}