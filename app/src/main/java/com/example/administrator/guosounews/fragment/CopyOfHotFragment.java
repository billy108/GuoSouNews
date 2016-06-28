package com.example.administrator.guosounews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.activity.MainActivity;
import com.example.administrator.guosounews.activity.NewsActivity;
import com.example.administrator.guosounews.activity.SettingsActivity;
import com.example.administrator.guosounews.activity.SpecialActivity;
import com.example.administrator.guosounews.adapter.MyAdvPagerAdapter;
import com.example.administrator.guosounews.adapter.RecyclerViewAdapter;
import com.example.administrator.guosounews.base.BaseFragment;
import com.example.administrator.guosounews.bean.NewsCenterCategory;
import com.example.administrator.guosounews.utils.APIs;
import com.example.administrator.guosounews.utils.ToastUtil;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;


public class CopyOfHotFragment extends BaseFragment {
    public static final String HOTFRAGMENT = "HotFragment";
    public static final String NEWS_TYPE = "news_type";

    ViewPager news_viewPager;
    TextView news_viewpager_text;
    LinearLayout point_group;
    PullLoadMoreRecyclerView news_list;
    ScrollView scrollView;

    private List<ImageView> imageList;
    private List<ImageView> imageNewsList;
    private int lastPointPostion;
    public boolean isRuning = false;
    private RecyclerViewAdapter myNewsListAdapter;
    private MyAdvPagerAdapter myAdvPagerAdapter;

    private NewsCenterCategory category;
    private List<String> menuNewCenterList = new ArrayList<>();

    private List<String> slide_url_list = new ArrayList<String>();
    private List<String> list_url_list = new ArrayList<String>();

    View view;
    private int tpye;

    /**
     * 初始化控件
     *
     * @param inflater
     * @return
     */
    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.copy_of_layout_hot, null);

        news_viewPager = (ViewPager) view.findViewById(R.id.news_viewpager);
        news_viewpager_text = (TextView) view.findViewById(R.id.news_viewpager_text);
        point_group = (LinearLayout) view.findViewById(R.id.point_group);
        news_list = (PullLoadMoreRecyclerView) view.findViewById(R.id.news_pullLoadMoreRecyclerView);
        news_list.setLinearLayout();
        scrollView = (ScrollView) view.findViewById(R.id.scroll);

        initMenu2();
        getJson();
        initAdvViewPager();

        return view;
    }

    /**
     * 填充List新闻的数据
     *
     * @param category json实例
     */
    private void initNewsList(final NewsCenterCategory category) {

        for (int i = 0; i < category.list.size(); i++) {
            list_url_list.add(APIs.ADV_BASE + category.list.get(i).nid + APIs.ADV_END);
        }

        myNewsListAdapter = new RecyclerViewAdapter(category, ct);
        news_list.setAdapter(myNewsListAdapter);

        news_list.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                news_list.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // 更新数据
                        getJson();
                        myNewsListAdapter.notifyDataSetChanged();
                        myAdvPagerAdapter.notifyDataSetChanged();
                        // 更新完后调用该方法结束刷新
                        news_list.setPullLoadMoreCompleted();
                    }
                }, 3000);
            }

            @Override
            public void onLoadMore() {
                news_list.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        news_list.setPullLoadMoreCompleted();
                    }
                }, 3000);
            }
        });
        news_list.setFooterViewText("加载更多...");

        myNewsListAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.showShort(ct, position);
                if (position == 0) {
                        Intent i = new Intent(getActivity(), SpecialActivity.class);
                        startActivity(i);
                    } else {
                        intentToNews(list_url_list.get(position), APIs.LIST_NEWS, position);
                    }
            }
        });
    }

    /**
     * 获取json
     */
    private void getJson() {
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET,
                APIs.HOT_NEWS,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.d(responseInfo.result);

                        category = new Gson().fromJson(responseInfo.result, NewsCenterCategory.class);
                        news_viewpager_text.setText(category.slide.get(0).title);
                        //初始化adv
                        initSlideList(category.slide.size(), imageList);
                        //初始化newsList
                        initNewsList(category);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                    }
                });
    }

    /**
     * 加载图片
     *
     * @param size  数量
     * @param image 图片
     */
    private void initSlideList(int size, List<ImageView> image) {
        for (int i = 0; i < size; i++) {
            if (!SettingsActivity.isLoadImage) {
                Glide.with(ct).load(category.slide.get(i).picture).into(image.get(i));
            }

            slide_url_list.add(APIs.ADV_BASE + category.slide.get(i).url + APIs.ADV_END);
        }
    }

    /**
     * 初始化adv
     */
    private void initAdvViewPager() {
        //加载图片
        imageList = new ArrayList<ImageView>();

        for (int i = 0; i < 4; i++) {
            ImageView im = new ImageView(ct);
            im.setScaleType(ImageView.ScaleType.CENTER_CROP);
            im.setImageResource(R.drawable.dark_dot);
            final int finalI = i;
            im.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intentToNews(slide_url_list.get(finalI), APIs.ADV_NEWS, finalI);
                }
            });
            imageList.add(im);


            //加载圆点
            ImageView point = new ImageView(ct);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.rightMargin = 20;
            point.setLayoutParams(params);
            point.setEnabled(false);
            if (i == 0) {
                point.setEnabled(true);
            }
            point.setBackgroundResource(R.drawable.point_bg);
            point_group.addView(point);
        }

        myAdvPagerAdapter = new MyAdvPagerAdapter(imageList);
        news_viewPager.setAdapter(myAdvPagerAdapter);
        news_viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageList.size()));
        news_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position = position % imageList.size();
                news_viewpager_text.setText(category.slide.get(position).title);
                point_group.getChildAt(position).setEnabled(true);
                point_group.getChildAt(lastPointPostion).setEnabled(false);
                lastPointPostion = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        isRuning = true;
    }

    /**
     * 跳转到新闻页面
     */
    private void intentToNews(String url, final int type, final int position) {
        //获取adv的json
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET,
                url,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.d(responseInfo.result);
                        //跳转并传递json
                        Intent i = new Intent(getActivity(), NewsActivity.class);
                        i.putExtra(HOTFRAGMENT, responseInfo.result);
                        i.putExtra(NEWS_TYPE, type);
                        if (category.list.get(position).pictures == null) {
                            i.putExtra("url", category.list.get(position).picture);
                        } else {
                            i.putExtra("url", category.list.get(position).pictures.get(0));
                        }
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                    }
                });



    }

    /**
     * 初始化左右滑动菜单
     */
    public void initMenu2() {

        if (menuNewCenterList.size() == 0) {
            BaseFragment.flag = true;
            menuNewCenterList.add("新闻");
            menuNewCenterList.add("订阅");
            menuNewCenterList.add("投票");
        }

        MenuFragment2 menuFragment2 = ((MainActivity) ct).getMenuFragment2();
        menuFragment2.initMenu(menuNewCenterList);
    }

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    @Override
    public void initData(Bundle savedInstanceState) {
    }

}
