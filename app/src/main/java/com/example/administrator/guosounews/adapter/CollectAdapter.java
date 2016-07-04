package com.example.administrator.guosounews.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;

import java.util.List;

public class CollectAdapter extends PagerAdapter {
        private List<View> mVList;
        private List<String> mTList;

        public CollectAdapter(List<View> mViewList, List<String> mTitleList) {
            this.mVList = mViewList;
            this.mTList = mTitleList;
        }

        @Override
        public int getCount() {
            return mVList.size();

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mVList.get(position));
            return mVList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Logger.d("remove");
            container.removeView(mVList.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTList.get(position);
        }
}
