package com.example.administrator.guosounews.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.bean.News;
import com.example.administrator.guosounews.fragment.HotFragment;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class NewsActivity extends Activity {

    @InjectView(R.id.news_back)
    ImageView newsBack;
    @InjectView(R.id.news_collect)
    ImageView newsCollect;
    @InjectView(R.id.news_shared)
    ImageView newsShared;
    @InjectView(R.id.news_title)
    TextView newsTitle;
    @InjectView(R.id.news_time)
    TextView newsTime;
    @InjectView(R.id.news_from)
    TextView newsFrom;
    @InjectView(R.id.news_details_ll)
    LinearLayout newsDetailsLl;
    @InjectView(R.id.see_original)
    TextView seeOriginal;

    News newsCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_news);
        ButterKnife.inject(this);

        getNews();

    }

    @OnClick({R.id.news_back, R.id.news_collect, R.id.news_shared, R.id.see_original})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.news_back:
                break;
            case R.id.news_collect:
                break;
            case R.id.news_shared:
                break;
            case R.id.see_original:
                break;
        }
    }

    public void getNews() {
        String json = getIntent().getStringExtra(HotFragment.HOTFRAGMENT);
        newsCategory = new Gson().fromJson(json, News.class);

        initNewsDetail(newsCategory);
    }
    /**
     * 初始化新闻详情页
     */
    private void initNewsDetail(News newsCategory) {
        newsTitle.setText(newsCategory.title);
        newsTime.setText(newsCategory.timeString);
        newsFrom.setText(newsCategory.mname);

        for (int i = 0; i < newsCategory.content.size(); i ++) {
            if (Objects.equals(newsCategory.content.get(i).type, "image")) {
                ImageView im = new ImageView(NewsActivity.this);
                im.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Picasso.with(NewsActivity.this).load(newsCategory.content.get(i).value)
                        .config(Bitmap.Config.RGB_565).error(R.drawable.dot)
                        .into(im);

                TextView tv = new TextView(NewsActivity.this);
                tv.setText(newsCategory.content.get(i).title);
//                tv.setTextSize(WebSettings.TextSize.LARGER);

                newsDetailsLl.addView(im);
                newsDetailsLl.addView(tv);
            }

            if (Objects.equals(newsCategory.content.get(i).type, "text")) {
                TextView tv = new TextView(NewsActivity.this);
                tv.setText(newsCategory.content.get(i).title);

                newsDetailsLl.addView(tv);
            }
        }
    }
}
