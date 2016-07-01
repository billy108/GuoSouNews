package com.example.administrator.guosounews.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment {
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


    List<Fragment> list = new ArrayList<Fragment>();

    /**
     * 初始化viewPagerTab
     *
     * @param savedInstanceState
     */
    @Override
    public void initData(Bundle savedInstanceState) {

        list.add(new CopyOfHotFragment());
        list.add(new PoliticsFragment());
        list.add(new FinanceFragment());
        list.add(new IntentNetFragment());
        list.add(new LawFragment());


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getActivity().getSupportFragmentManager(), FragmentPagerItems.with(ct)
                .add("热搜", CopyOfHotFragment.class)
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

