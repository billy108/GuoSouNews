package com.example.administrator.guosounews.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.base.BaseFragment;
import com.example.administrator.guosounews.base.BasePage;
import com.example.administrator.guosounews.home.FunctionPage;
import com.example.administrator.guosounews.home.GovAffairsPage;
import com.example.administrator.guosounews.home.NewsCenterPage;
import com.example.administrator.guosounews.home.SettingPage;
import com.example.administrator.guosounews.home.SmartServicePage;
import com.example.administrator.guosounews.ui.LazyViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {
    @ViewInject(R.id.viewpager)
    private LazyViewPager viewPager;

    @ViewInject(R.id.top_title_sliding)
    private ImageButton top_sliding_btn;

    @ViewInject(R.id.top_title_sliding2)
    private ImageButton top_title_sliding2;

    @ViewInject(R.id.news_categories_ll)
    private LinearLayout news_categories_ll;

    private RadioGroup main_radio;
    private View view;

    FunctionPage hot_serach;

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


    List<BasePage> list = new ArrayList<BasePage>();
    @Override
    public void initData(Bundle savedInstanceState) {
        showNewsCategory();
        hot_serach = new FunctionPage(ct);
        list.add(hot_serach);
        list.add(new NewsCenterPage(ct));
        list.add(new SmartServicePage(ct));
        list.add(new GovAffairsPage(ct));
        list.add(new SettingPage(ct));

        HomePageAdapter adapter = new HomePageAdapter(ct, list);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new LazyViewPager.OnPageChangeListener() {
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
                BasePage page = list.get(position);
                if (!flag) {
                    page.initData();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        main_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.rb_function:
//                        viewPager.setCurrentItem(0, false);
////                        checkedId = 0;
//                        break;
//                    case R.id.rb_news_center:
//                        viewPager.setCurrentItem(1, false);
////                        checkedId = 1;
//                        break;
//                    case R.id.rb_smart_service:
//                        viewPager.setCurrentItem(2, false);
////                        checkedId = 2;
//                        break;
//                    case R.id.rb_gov_affairs:
//                        viewPager.setCurrentItem(3, false);
////                        checkedId = 3;
//                        break;
//                    case R.id.rb_setting:
//                        viewPager.setCurrentItem(4, false);
////                        checkedId = 4;
//                        break;
//                }
//            }
//        });
    }

    private ArrayList<String> categoryList = new ArrayList<String>();
    private String[] newsCategoryList = {"热搜", "时政", "互联网", "财经", "法治", "美食", "娱乐", "国际"};
    private void showNewsCategory() {
        if (categoryList.size() == 0) {
            for (String item : newsCategoryList) {
                categoryList.add(item);
                TextView tx = new TextView(ct);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                params.rightMargin = 50;
                tx.setLayoutParams(params);
                tx.setText(item);
                tx.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                news_categories_ll.addView(tx);
            }
        }
    }

    public void toggleMenu(SlidingMenu slidingMenu) {
        slidingMenu.toggle();
    }

    class HomePageAdapter extends PagerAdapter {
        private Context ct;

        private List<BasePage> list;

        public HomePageAdapter(Context ct, List<BasePage> list) {
            this.ct = ct;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((LazyViewPager) container).removeView(list.get(position).getRootView());
            hot_serach.isRuning = false;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if(position == 0){
                list.get(0).initData();
            }
            ((LazyViewPager) container).addView(list.get(position).getRootView(), 0);
            return list.get(position).getRootView();
        }

    }
}

