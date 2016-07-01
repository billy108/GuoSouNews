package com.example.administrator.guosounews.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class HelpActivity extends FragmentActivity {

    @InjectView(R.id.vp_help)
    ViewPager vpHelp;
    @InjectView(R.id.ll_help_point_group)
    LinearLayout llHelpPointGroup;
    @InjectView(R.id.iv_help_close)
    ImageView ivHelpClose;
    @InjectView(R.id.btn_help_close)
    Button btnHelpClose;

    private MyAdvPagerAdapter adapter;
    private  List<ImageView> imageList;
    private int lastPointPostion;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_help);
        ButterKnife.inject(this);

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
            llHelpPointGroup.addView(point);
        }

        initAdapter();
    }

    private void initAdapter() {
        adapter = new MyAdvPagerAdapter(imageList);
        vpHelp.setAdapter(adapter);
        vpHelp.setCurrentItem(0);
        vpHelp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position = position % imageList.size();
                if (position == 3) {
                    btnHelpClose.setVisibility(View.VISIBLE);
                }
                llHelpPointGroup.getChildAt(position).setEnabled(true);
                llHelpPointGroup.getChildAt(lastPointPostion).setEnabled(false);
                lastPointPostion = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.iv_help_close, R.id.btn_help_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_help_close:
                finish();
                break;
            case R.id.btn_help_close:
                finish();
                break;
        }
    }

}
