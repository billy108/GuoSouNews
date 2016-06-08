package com.example.administrator.guosounews.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.activity.MainActivity;
import com.example.administrator.guosounews.activity.NewsActivity;
import com.example.administrator.guosounews.adapter.MyAdvPagerAdapter;
import com.example.administrator.guosounews.adapter.MyNewsListAdapter;
import com.example.administrator.guosounews.base.BaseFragment;
import com.example.administrator.guosounews.bean.NewsCenterCategory;
import com.example.administrator.guosounews.ui.ListViewForScrollView;
import com.example.administrator.guosounews.ui.RefreshLayout;
import com.example.administrator.guosounews.utils.APIs;
import com.example.administrator.guosounews.utils.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class HotFragment extends BaseFragment {
    public static final String HOTFRAGMENT = "HotFragment";

    ViewPager news_viewPager;
    TextView news_viewpager_text;
    LinearLayout point_group;
    ListViewForScrollView news_list;
    ScrollView scrollView;
    RefreshLayout myRefreshListView;

    private List<ImageView> imageList;
    private List<ImageView> imageNewsList;
    private int lastPointPostion;
    public boolean isRuning = false;
    private MyNewsListAdapter myNewsListAdapter;
    private MyAdvPagerAdapter myAdvPagerAdapter;

    private NewsCenterCategory category;
    private List<String> menuNewCenterList = new ArrayList<>();

    private List<String> slide_url_list = new ArrayList<String>();

    public static boolean isLastItem;
    View view;

    /**
     * 初始化控件
     *
     * @param inflater
     * @return
     */
    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.layout_hot, null);

        news_viewPager = (ViewPager) view.findViewById(R.id.news_viewpager);
        news_viewpager_text = (TextView) view.findViewById(R.id.news_viewpager_text);
        point_group = (LinearLayout) view.findViewById(R.id.point_group);
        news_list = (ListViewForScrollView) view.findViewById(R.id.news_list);
        scrollView = (ScrollView) view.findViewById(R.id.scroll);
        myRefreshListView = (RefreshLayout) view.findViewById(R.id.swipe_layout);

        initRefreshListView();
        initMenu2();
        getJson();
        initAdvViewPager();
        initListView();

        return view;
    }

    /**
     * 判断位置执行下拉和加载更多
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void initListView() {

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int heigh = getActivity().getWindowManager().getDefaultDisplay().getHeight();
                LogUtils.d("scrollY is " + scrollY);


                if (scrollY == 0) {
                    myRefreshListView.setEnabled(true);
                } else {
                    myRefreshListView.setEnabled(false);
                }
//				if (scrollY > 6000) {
//					isLastItem = true;
//					LogUtils.d("bottom!!!!!!!!!!!!!!");
//					return;
//				} else {
//					isLastItem = false;
//				}
            }
        });
    }

    /**
     * 下拉刷新
     */
    private void initRefreshListView() {
        // 设置下拉刷新时的颜色值,颜色值需要定义在xml中
        myRefreshListView.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        // 设置下拉刷新监听器
        myRefreshListView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                Toast.makeText(ct, "刷新中...", Toast.LENGTH_SHORT).show();

                myRefreshListView.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // 更新数据
                        category = null;
                        getJson();
                        myNewsListAdapter.notifyDataSetChanged();
                        myAdvPagerAdapter.notifyDataSetChanged();
                        // 更新完后调用该方法结束刷新
                        myRefreshListView.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        // 加载监听器
//		myRefreshListView.setOnLoadListener(new RefreshLayout.OnLoadListener() {
//
//			@Override
//			public void onLoad() {
//
//				Toast.makeText(ct, "load", Toast.LENGTH_SHORT).show();
//				myRefreshListView.postDelayed(new Runnable() {
//
//					@Override
//					public void run() {
//						// 加载完后调用该方法
//						myRefreshListView.setLoading(false);
//					}
//				}, 1500);
//
//			}
//		});
    }

    /**
     * 填充List新闻的数据
     *
     * @param category json实例
     */
    private void initNewsList(NewsCenterCategory category) {
        imageNewsList = new ArrayList<ImageView>();

        for (int i = 0; i < category.list.size(); i++) {
            ImageView im = new ImageView(ct);
            im.setImageResource(R.drawable.dark_dot);
            imageNewsList.add(im);
            myNewsListAdapter = new MyNewsListAdapter(category, ct);
            news_list.setAdapter(myNewsListAdapter);

        }
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
                        SharedPreferencesUtils.saveString(ct, HOTFRAGMENT, responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        LogUtils.d("1111111111111111111111111111");
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
            Picasso.with(ct).load(category.slide.get(i).picture)
                    .config(Bitmap.Config.RGB_565).error(R.drawable.dot)
                    .into(image.get(i));

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
                    intentToNews(slide_url_list.get(finalI));
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
        //adv的item点击事件
    }

    /**
     * 跳转到新闻页面
     */
    private void intentToNews(String url) {
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
        String vaule = SharedPreferencesUtils.getString(ct, HOTFRAGMENT);
    }

}
