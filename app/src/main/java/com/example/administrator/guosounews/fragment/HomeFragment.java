package com.example.administrator.guosounews.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.activity.TestActivity;
import com.example.administrator.guosounews.base.BaseFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment {
    @InjectView(R.id.top_title_text)
    TextView topTitleText;
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;

    @ViewInject(R.id.top_title_sliding)
    private ImageButton top_sliding_btn;

    @ViewInject(R.id.top_title_sliding2)
    private ImageButton top_title_sliding2;

    @ViewInject(R.id.viewpagertab)
    private SmartTabLayout viewPagerTab;

    @ViewInject(R.id.textView1)
    private TextView tv;

    private View view;

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.frag_home2, null);
        ViewUtils.inject(this, view);
        initOnclick();
        return view;
    }

    private void initOnclick() {
        top_title_sliding2.setOnClickListener(listener);
        top_sliding_btn.setOnClickListener(listener);
    }

    /**
     * 标题栏点击
     */
    Handler handler = new Handler();
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.top_title_sliding2:
                    if (!sm.isSecondaryMenuShowing()) {
                        sm.showSecondaryMenu();
                    } else {
                        sm.showContent();
                    }

                    break;
                case R.id.top_title_sliding:

                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            sm.toggle();
                        }
                    }, 100);
                    break;
            }
        }
    };


    List<Fragment> list = new ArrayList<Fragment>();

    /**
     * 初始化viewPagerTab
     *
     * @param savedInstanceState
     */
    @Override
    public void initData(Bundle savedInstanceState) {

        list.add(new HotFragment());
        list.add(new CopyOfHotFragment());
        list.add(new PoliticsFragment());
        list.add(new FinanceFragment());
        list.add(new IntentNetFragment());
        list.add(new LawFragment());


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getActivity().getSupportFragmentManager(), FragmentPagerItems.with(ct)
                .add("热搜", HotFragment.class)
                .add("CopyHot", CopyOfHotFragment.class)
                .add("时政", PoliticsFragment.class)
                .add("互联网", IntentNetFragment.class)
                .add("财经", FinanceFragment.class)
                .add("法治", LawFragment.class)
                .create());

        viewPager.setAdapter(adapter);

        viewPagerTab.setViewPager(viewPager);

        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                BaseFragment fragment = (BaseFragment) list.get(position);
                if (!flag) {
                    fragment.initData(null);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void toggleMenu(SlidingMenu slidingMenu) {
        slidingMenu.toggle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.top_title_text)
    public void onClick() {
        Intent i = new Intent(ct, TestActivity.class);
        startActivity(i);
    }
}

