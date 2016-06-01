package com.example.administrator.guosounews.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
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

public class HomeFragment extends BaseFragment {
    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;

    @ViewInject(R.id.top_title_sliding)
    private ImageButton top_sliding_btn;

    @ViewInject(R.id.top_title_sliding2)
    private ImageButton top_title_sliding2;

    @ViewInject(R.id.viewpagertab)
    private SmartTabLayout viewPagerTab;

    @ViewInject(R.id.swipe_container)
    private SwipeRefreshLayout swipeRefreshLayout;

    @ViewInject(R.id.textView1)
    private TextView tv;

//    @ViewInject(R.id.tabs)
//    private TabPageIndicator tabPageIndicator;

    private View view;


    @Override

    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.frag_home2, null);
        ViewUtils.inject(this, view);
        initOnclick();
        initRefresh();
        return view;
    }

    private void initRefresh() {
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                tv.setText("正在刷新~~");
                // TODO Auto-generated method stub
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        tv.setText("刷新完成");
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 6000);
            }

        });
    }


    private void initOnclick() {
        top_title_sliding2.setOnClickListener(listener);
        top_sliding_btn.setOnClickListener(listener);
    }

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


//    List<BasePage> list = new ArrayList<BasePage>();
    List<Fragment> list = new ArrayList<Fragment>();

    @Override
    public void initData(Bundle savedInstanceState) {

        list.add(new HotFragment());
        list.add(new PoliticsFragment());
        list.add(new FinanceFragment());
        list.add(new IntentNetFragment());
        list.add(new LawFragment());


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getActivity().getSupportFragmentManager(), FragmentPagerItems.with(ct)
                .add("热搜", HotFragment.class)
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
//                if (0 == position) {
//                    sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
//                } else {
//                    sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
//                }
//                BasePage page = list.get(position);
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

//    class HomePageAdapter extends PagerAdapter {
//        private Context ct;
//
//        private List<BasePage> list;
//
//        public HomePageAdapter(Context ct, List<BasePage> list) {
//            this.ct = ct;
//            this.list = list;
//        }
//
//        @Override
//        public int getCount() {
//            return list.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            ((ViewPager) container).removeView(list.get(position).getRootView());
//            hot_serach.isRuning = false;
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            if (position == 0) {
//                list.get(0).initData();
//            }
//            ((ViewPager) container).addView(list.get(position).getRootView(), 0);
//            return list.get(position).getRootView();
//        }
//
//    }


}

