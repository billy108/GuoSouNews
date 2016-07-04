package com.example.administrator.guosounews.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.base.BaseFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment {
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;

    @ViewInject(R.id.top_title_sliding)
    private ImageButton top_sliding_btn;

    @ViewInject(R.id.top_title_sliding2)
    private ImageButton top_title_sliding2;

    @ViewInject(R.id.viewpagertab)
    private TabLayout viewPagerTab;

    @ViewInject(R.id.textView1)
    private TextView tv;

    private View view;
    private ArrayList<String> mTitleList = new ArrayList<>();

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.frag_home2, null);
        ViewUtils.inject(this, view);
        initOnclick();
        return view;
    }

    /**
     * 标题左右按键点击事件
     */
    private void initOnclick() {
        top_title_sliding2.setOnClickListener(listener);
        top_sliding_btn.setOnClickListener(listener);
    }

    /**
     * 标题栏按键点击
     */
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
                            sm.toggle();
                    break;
            }
        }
    };

    /**
     * 初始化viewPagerTab
     *
     * @param savedInstanceState
     */
    @Override
    public void initData(Bundle savedInstanceState) {
        initContent();
    }

    private void initContent() {

        mTitleList.add("热搜");
        mTitleList.add("时政");
        mTitleList.add("互联网");
        mTitleList.add("财经");
        mTitleList.add("法治");

        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public int getCount() {
                return mTitleList.size();
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 1:
                        return new PoliticsFragment();
                    case 2:
                        return new FinanceFragment();
                    case 3:
                        return new IntentNetFragment();
                    case 4:
                        return new LawFragment();
                    default:
                        return new CopyOfHotFragment();
                }
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mTitleList.get(position);
            }
        });

        viewPagerTab.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
        viewPagerTab.setTabTextColors(Color.GRAY, Color.RED);
        viewPagerTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab == viewPagerTab.getTabAt(0)) {
                    viewPager.setCurrentItem(0);
                } else if (tab == viewPagerTab.getTabAt(1)) {
                    viewPager.setCurrentItem(1);
                }else if (tab == viewPagerTab.getTabAt(2)) {
                    viewPager.setCurrentItem(2);
                }else if (tab == viewPagerTab.getTabAt(3)) {
                    viewPager.setCurrentItem(3);
                }else if (tab == viewPagerTab.getTabAt(4)) {
                    viewPager.setCurrentItem(4);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void toggleMenu(SlidingMenu slidingMenu) {
        slidingMenu.toggle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}

