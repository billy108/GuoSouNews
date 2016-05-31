package com.example.administrator.guosounews.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.guosounews.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class TabPageIndicatorAdapter extends FragmentPagerAdapter {
    List<Fragment> list = new ArrayList<Fragment>();

    public TabPageIndicatorAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        //新建一个Fragment来展示ViewPager item的内容，并传递参数
        Fragment fragment = list.get(position);
        Bundle args = new Bundle();
        args.putString("arg", HomeFragment.TITLE[position]);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return HomeFragment.TITLE[position % HomeFragment.TITLE.length];
    }

    @Override
    public int getCount() {
        return HomeFragment.TITLE.length;
    }
}
