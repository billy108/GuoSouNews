package com.example.administrator.guosounews.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * 广告栏adapter
 */
public class MyAdvPagerAdapter extends PagerAdapter {
    private List<ImageView> imageList;

    public MyAdvPagerAdapter(List<ImageView> imageList) {
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object ;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(imageList.get(position%imageList.size()));
        return imageList.get(position%imageList.size());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView(imageList.get(position%imageList.size()));
        object = null;
    }

}
