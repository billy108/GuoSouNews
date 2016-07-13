package com.example.administrator.guosounews.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.bean.NewsAdv;
import com.example.administrator.guosounews.bean.NewsChannel;
import com.example.administrator.guosounews.bean.NewsList;
import com.example.administrator.guosounews.fragment.CopyOfHotFragment;
import com.example.administrator.guosounews.utils.APIs;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import onekeyshare.OnekeyShare;

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

    NewsAdv newsCategory;
    NewsList newsCaty;
    NewsChannel channel;
    String picUrl;

    public static ArrayList<NewsList> collectNews = new ArrayList<>();
    public static ArrayList<NewsList> histroyNews = new ArrayList<>();
    public static ArrayList<String> collectedNews = new ArrayList<>();
    public static ArrayList<String> collcetpicUrls = new ArrayList<>();
    public static ArrayList<String> histroypicUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_news);
        ButterKnife.inject(this);

        getNews();
        addHistroy();

    }

    /**
     * 只要打开新闻详情页，就添加到histroyNews
     */
    public static ArrayList<String> histroyItem = new ArrayList<>();
    private void addHistroy() {
        for (int i = 0; i < histroyNews.size(); i ++) {
            histroyItem.add(histroyNews.get(i).nid);
        }
            if (newsCaty != null &&!histroyItem.contains(newsCaty.nid)) {
                picUrl = getIntent().getStringExtra("url");
                histroyNews.add(newsCaty);
                histroypicUrls.add(picUrl);
        }
    }

    /**
     * 点击事件处理
     * @param view 组件
     */
    @OnClick({R.id.news_back, R.id.news_collect, R.id.news_shared})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.news_back:
                finish();
                break;
            case R.id.news_collect:

                if (!newsCollect.isSelected()) {
                    collectNews.add(newsCaty);
                    collcetpicUrls.add(picUrl);
                    collectedNews.add(newsCaty.nid);
                    newsCollect.setSelected(true);
                } else {
                    collectedNews.remove(newsCaty.nid);
                    collectNews.remove(newsCaty);
                    collcetpicUrls.remove(picUrl);//?????
                    newsCollect.setSelected(false);
                }
                break;
            case R.id.news_shared:
                showShare();
                break;
        }
    }

    /**
     * 第三方分享
     */
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(newsCaty.title);

        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(newsCaty.url);

        // text是分享文本，所有平台都需要这个字段
        oks.setText(newsCaty.description);

        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(picUrl);//确保SDcard下面存在此张图片

        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");

        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");

        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(newsCaty.mname);

        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

    /**
     * 获取json，跳转到新闻详情页
     */
    public void getNews() {

        String json = getIntent().getStringExtra(CopyOfHotFragment.HOTFRAGMENT);
        int type = getIntent().getIntExtra(CopyOfHotFragment.NEWS_TYPE, -1);

        if (type == APIs.ADV_NEWS) {
            newsCategory = new Gson().fromJson(json, NewsAdv.class);
            initAdvNewsDetail(newsCategory);
            if (collectedNews.contains(newsCategory.nid)) {
                newsCollect.setSelected(true);
            }
        } else if (type == 22) {
            int postiton = getIntent().getIntExtra("postiton", -1);
            channel = new Gson().fromJson(json, NewsChannel.class);
            initNewsDetail(channel, postiton);
        } else {
            newsCaty = new Gson().fromJson(json, NewsList.class);
            initListNewsDetail(newsCaty);
            if (collectedNews.contains(newsCaty.nid)) {
                newsCollect.setSelected(true);
            }
        }
    }

    /**
     * 初始化Adv新闻详情页
     */
    private void initAdvNewsDetail(NewsAdv newsCategory) {

                newsTitle.setText(newsCategory.title);
                newsTime.setText(newsCategory.timeString);
                newsFrom.setText(newsCategory.mname);

                for (int i = 0; i < newsCategory.content.size(); i++) {
                    if (Objects.equals(newsCategory.content.get(i).type, "image")) {
                        ImageView im = new ImageView(NewsActivity.this);
                        Glide.with(NewsActivity.this).load(newsCategory.content.get(i).value)
                                .into(im);

                        TextView tv = new TextView(NewsActivity.this);
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                        tv.setText(Html.fromHtml(newsCategory.content.get(i).title));
                        tv.setText(newsCategory.content.get(i).title);

                        newsDetailsLl.addView(im);
                        newsDetailsLl.addView(tv);
                    }

                    if (Objects.equals(newsCategory.content.get(i).type, "text")) {
                        TextView tv = new TextView(NewsActivity.this);
                        if (newsCategory.content.get(i).title != null) {
                            tv.setText(Html.fromHtml(newsCategory.content.get(i).title));
                        }

                        newsDetailsLl.addView(tv);
                    }
                }
        }

    /**
     * 初始化List新闻详情页
     */
    private void initListNewsDetail(NewsList newsCaty) {
        newsTitle.setText(newsCaty.title);
        newsTime.setText(newsCaty.timeString);
        newsFrom.setText(newsCaty.mname);

        for (int i = 0; i < newsCaty.content.size(); i++) {
            if (Objects.equals(newsCaty.content.get(i).type, "image")) {
                ImageView im = new ImageView(NewsActivity.this);
                Glide.with(NewsActivity.this).load(newsCaty.content.get(i)
                        .value).into(im);

                newsDetailsLl.addView(im);
            }

            if (Objects.equals(newsCaty.content.get(i).type, "text")) {
                TextView tv = new TextView(NewsActivity.this);
                tv.setText(Html.fromHtml(newsCaty.content.get(i).value));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                newsDetailsLl.addView(tv);
            }

        }
    }


    private void initNewsDetail(NewsChannel channel, int postiton) {
        newsTitle.setText(channel.articleList.get(postiton).title);
        newsTime.setText(channel.articleList.get(postiton).time);
        newsFrom.setText(channel.mname);

        for (int i = 0; i < channel.articleList.get(postiton).content.size(); i++) {
            if (Objects.equals(channel.articleList.get(postiton).content.get(i).type, "image")) {
                ImageView im = new ImageView(NewsActivity.this);
                Glide.with(NewsActivity.this).load(channel.articleList.get(postiton).content.get(i)
                        .value).into(im);

                newsDetailsLl.addView(im);
            }

            if (Objects.equals(channel.articleList.get(postiton).content.get(i).type, "text")) {
                TextView tv = new TextView(NewsActivity.this);
                tv.setText(Html.fromHtml(channel.articleList.get(postiton).content.get(i).value));
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
                newsDetailsLl.addView(tv);
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
