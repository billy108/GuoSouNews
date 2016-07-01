package com.example.administrator.guosounews.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.adapter.SpecialNewsListAdapter;
import com.example.administrator.guosounews.bean.NewsSpecial;
import com.example.administrator.guosounews.fragment.CopyOfHotFragment;
import com.example.administrator.guosounews.fragment.HomeFragment;
import com.example.administrator.guosounews.ui.ListViewForScrollView;
import com.example.administrator.guosounews.ui.RefreshLayout;
import com.example.administrator.guosounews.utils.APIs;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SpecialActivity extends Activity {
    public static final String SPECIALACTIVITY = "specialActivity";
    public static final String NEWS_TYPE = "news_type";

    @InjectView(R.id.special_back)
    ImageView specialBack;
    @InjectView(R.id.special_title)
    TextView specialTitle;
    @InjectView(R.id.special_news_viewpager)
    ImageView specialNewsViewpager;
    @InjectView(R.id.special_news_viewpager_text)
    TextView specialNewsViewpagerText;
    @InjectView(R.id.special_point_group)
    LinearLayout specialPointGroup;
    @InjectView(R.id.special_news_list)
    ListViewForScrollView specialNewsList;
    @InjectView(R.id.special_news_rl)
    RelativeLayout specialNewsRl;
    @InjectView(R.id.special_scroll)
    ScrollView specialScroll;
    @InjectView(R.id.special_swipe_layout)
    RefreshLayout specialSwipeLayout;

    private NewsSpecial specica;
    private HomeFragment hotFragment = new HomeFragment();;
    String slide_url;
    private List<ImageView> imageNewsList;
    private List<String> list_url_list = new ArrayList<String>();
    private SpecialNewsListAdapter myNewsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_news_special);
        ButterKnife.inject(this);

        specialNewsViewpager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SpecialActivity.this, "1111111111111111111111111", Toast.LENGTH_SHORT).show();
            }
        });
        getURLJson();
        initAdv();
    }

    @OnClick({R.id.special_back,R.id.special_news_viewpager})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.special_back:
                Toast.makeText(this, "1111111111111111111111111", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void getURLJson() {
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET,
                APIs.SPECIAL_NEWS,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.d(responseInfo.result);

                        specica = new Gson().fromJson(responseInfo.result, NewsSpecial.class);
                        specialNewsViewpagerText.setText(specica.slide.get(0).title);
                        //初始化adv
                        initSlideList(specica);
                        //初始化newsList
                        initNewsList(specica);
//                        SharedPreferencesUtils.saveString(ct, HOTFRAGMENT, responseInfo.result);
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
     */
    private void initSlideList(NewsSpecial specica) {
            Picasso.with(this).load(specica.slide.get(0).picture)
                    .config(Bitmap.Config.RGB_565).error(R.drawable.dot)
                    .into(specialNewsViewpager);

            slide_url = APIs.ADV_BASE + specica.slide.get(0).url + APIs.ADV_END;
    }

    /**
     * 初始化adv
     */
    private void initAdv() {
        //加载图片
            specialNewsViewpager.setScaleType(ImageView.ScaleType.CENTER_CROP);
            specialNewsViewpager.setImageResource(R.drawable.dark_dot);
            specialNewsViewpager.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intentToNews(slide_url, APIs.LIST_NEWS);
                }
            });

            //加载圆点
            ImageView point = new ImageView(this);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.rightMargin = 20;
            point.setLayoutParams(params);
            point.setBackgroundResource(R.drawable.point_bg);
            specialPointGroup.addView(point);
        //adv的item点击事件
    }

    /**
     * 填充List新闻的数据
     *
     * @param specica json实例
     */
    private void initNewsList(NewsSpecial specica) {
        imageNewsList = new ArrayList<ImageView>();

        for (int i = 0; i < specica.list.size(); i++) {
            ImageView im = new ImageView(this);
            im.setImageResource(R.drawable.dark_dot);
            list_url_list.add(APIs.ADV_BASE + specica.list.get(i).nid + APIs.ADV_END);
            imageNewsList.add(im);

            myNewsListAdapter = new SpecialNewsListAdapter(specica, this);
            specialNewsList.setAdapter(myNewsListAdapter);
            final int finalI = i;
            specialNewsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        intentToNews(list_url_list.get(position), APIs.SPE_LIST_NEWS);
                }
            });
        }
    }

    /**
     * 跳转到新闻页面
     */
    private void intentToNews(String url, final int type) {
        //获取adv的json
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET,
                url,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.d(responseInfo.result);
                        //跳转并传递json
                        Intent i = new Intent(SpecialActivity.this, NewsActivity.class);
                        i.putExtra(CopyOfHotFragment.HOTFRAGMENT, responseInfo.result);
                        i.putExtra(NEWS_TYPE, type);
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                    }
                });

    }
}
