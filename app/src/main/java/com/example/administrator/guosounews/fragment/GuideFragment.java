package com.example.administrator.guosounews.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.adapter.GuidePagerAdapter;
import com.example.administrator.guosounews.base.BasePage;

import java.util.ArrayList;
import java.util.List;

public class GuideFragment extends Fragment implements ViewPager.OnPageChangeListener{
    private Context context;

    private ViewPager vp;
    private GuidePagerAdapter vpAdapter;
    private List<View> views = new ArrayList<View>();
    private View view;

    // 底部小点图片
    private ImageView[] dots;

    // 记录当前选中位置
    private int currentIndex;

    public GuideFragment(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = LayoutInflater.from(context).inflate(R.layout.layout_guide, null);

        // 初始化页面
        initViews();

        // 初始化底部小点
        initDots();

        return view;
    }

    private void initViews() {
        LayoutInflater in = LayoutInflater.from(context);

        // 初始化引导图片列表
        views.add(in.inflate(R.layout.what_new_one, null));
        views.add(in.inflate(R.layout.what_new_one, null));
        views.add(in.inflate(R.layout.what_new_one, null));
        views.add(in.inflate(R.layout.what_new_one, null));
        // 初始化Adapter
        vpAdapter = new GuidePagerAdapter(views, getActivity());

        vp = (ViewPager) view.findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);
        // 绑定回调
        vp.setOnPageChangeListener(this);
    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll);

        dots = new ImageView[views.size()];

        // 循环取得小点图片
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);// 都设为灰色
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
