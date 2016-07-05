package com.example.administrator.guosounews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.adapter.MyAdvPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends FragmentActivity implements View.OnClickListener{

    private ViewPager vpWelcome;
    private LinearLayout llWelcomePointGroup;
    private Button btnWelcomeClose;

    private MyAdvPagerAdapter adapter;
    private List<ImageView> imageList;
    private int lastPointPostion;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);

        vpWelcome = (ViewPager) findViewById(R.id.vp_welcome);
        llWelcomePointGroup = (LinearLayout) findViewById(R.id.ll_welcome_point_group);
        btnWelcomeClose = (Button) findViewById(R.id.btn_welcome_close);
        btnWelcomeClose.setOnClickListener(this);

        initView();

    }

    private void initView() {
        int[] images = {R.drawable.guid_1, R.drawable.guid_2, R.drawable.guid_3, R.drawable.guid_4};
        //加载图片
        imageList = new ArrayList<ImageView>();

        for (int i = 0; i < 4; i++) {
            ImageView im = new ImageView(this);
            im.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(this).load(images[i]).into(im);
            imageList.add(im);


            //加载圆点
            ImageView point = new ImageView(this);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.rightMargin = 20;
            point.setLayoutParams(params);
            point.setEnabled(false);
            if (i == 0) {
                point.setEnabled(true);
            }
            point.setBackgroundResource(R.drawable.point_bg);
            llWelcomePointGroup.addView(point);
        }

        initAdapter();
    }

    private void initAdapter() {
        adapter = new MyAdvPagerAdapter(imageList);
        vpWelcome.setAdapter(adapter);
        vpWelcome.setCurrentItem(0);
        vpWelcome.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position = position % imageList.size();
                if (position == 3) {
                    btnWelcomeClose.setVisibility(View.VISIBLE);
                }
                llWelcomePointGroup.getChildAt(position).setEnabled(true);
                llWelcomePointGroup.getChildAt(lastPointPostion).setEnabled(false);
                lastPointPostion = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    // 不可点击返回键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
