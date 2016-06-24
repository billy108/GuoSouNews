package com.example.administrator.guosounews.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.adapter.CollectItemAdapter;
import com.example.administrator.guosounews.bean.NewsList;
import com.example.administrator.guosounews.utils.RecycleViewDivider;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CollectActivity extends FragmentActivity {

    public static ArrayList<NewsList> histroyNews = new ArrayList<>();;

    private FragmentManager fm;
    private View collectView, histroyView;
    private ArrayList<View> mViewList = new ArrayList<>();
    private LayoutInflater inflater;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合

    @InjectView(R.id.vp_collect)
    ViewPager vpCollect;
    @InjectView(R.id.collect_back)
    ImageView collectBack;
    @InjectView(R.id.edit)
    ImageView edit;
    @InjectView(R.id.tl_collect)
    TabLayout tlCollect;
    @InjectView(R.id.tv_collect_title)
    TextView tvCollectTitle;

    private RecyclerView revCollect;
    private RecyclerView revHistroy;

    private CollectItemAdapter collectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_collect);
        ButterKnife.inject(this);

        initContent();
        initTab();
        initCollcetion();
    }

    private void initCollcetion() {
        revCollect = (RecyclerView) collectView.findViewById(R.id.rev_collcet);
        histroyView = (RecyclerView) collectView.findViewById(R.id.rev_histroy);

        revCollect.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        //设置分割线
        revCollect.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL));
        if (NewsActivity.collectNews.size() != 0) {
            collectAdapter = new CollectItemAdapter(NewsActivity.collectNews, this, NewsActivity.picUrls);
            revCollect.setAdapter(collectAdapter);
        }
    }

    private void initContent() {
        mTitleList.add("收藏");
        mTitleList.add("历史");
        inflater = LayoutInflater.from(this);
        collectView = inflater.inflate(R.layout.fragment_collection, null);
        histroyView = inflater.inflate(R.layout.fragment_histroy, null);

        mViewList.add(collectView);
        mViewList.add(histroyView);

        CollectAdapter adapter = new CollectAdapter(mViewList, mTitleList);

        vpCollect.setAdapter(adapter);
        tlCollect.setupWithViewPager(vpCollect);//将TabLayout和ViewPager关联起来。
    }

    private void initTab() {
        tlCollect.setTabTextColors(Color.GRAY, Color.RED);

        tlCollect.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int postiton = tab.getPosition();
                switch (postiton) {
                    case 0:
                        tvCollectTitle.setText("收藏");
                        edit.setBackground(getResources().getDrawable(R.drawable.header_right_icon_edit_selected));
                        break;
                    case 1:
                        tvCollectTitle.setText("历史");
                        edit.setBackground(getResources().getDrawable(R.drawable.header_right_icon_del_selected));
                        break;
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


    @OnClick({R.id.collect_back, R.id.edit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.collect_back:
                break;
            case R.id.edit:
                break;
        }
    }

    /**
     *
     */
    class CollectAdapter extends PagerAdapter{
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
}
