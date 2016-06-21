package com.example.administrator.guosounews.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.adapter.NewsChannelAdapter;
import com.example.administrator.guosounews.bean.NewsChannel;
import com.example.administrator.guosounews.utils.RecycleViewDivider;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SubscriptionNewsActivity extends Activity {

    @InjectView(R.id.channel_back)
    ImageView channelBack;
    @InjectView(R.id.channel_header_title)
    TextView channelHeaderTitle;
    @InjectView(R.id.channel_header)
    RelativeLayout channelHeader;
    @InjectView(R.id.channel_recycler)
    RecyclerView channelRecycler;

    private NewsChannelAdapter channelAdapter;
    private NewsChannel channel;
    private LinearLayoutManager manager;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_subscription_news);
        ButterKnife.inject(this);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {

                initData((NewsChannel) msg.obj);
            }
        };
        initRecyler();
    }

    private void initRecyler() {
        String url = getIntent().getStringExtra("url");
        getJson(url);
    }

    private void getJson(String url) {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url(url)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.d("Fail~~~~~");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String htmlStr =  response.body().string();
                Logger.d(htmlStr);
                channel = new Gson().fromJson(htmlStr, NewsChannel.class);
                Message msg = new Message();
                msg.obj = channel;
                handler.sendMessage(msg);
            }

        });
    }

    private void initData(NewsChannel channels) {
        channelHeaderTitle.setText(channels.mname);
        //设置固定大小
        channelRecycler.setHasFixedSize(true);
        //创建线性布局
        manager = new LinearLayoutManager(SubscriptionNewsActivity.this);
        //给RecyclerView设置布局管理器
        channelRecycler.setLayoutManager(manager);
        //创建适配器，并且设置
        //设置分割线
        channelRecycler.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL));

        channelAdapter = new NewsChannelAdapter(channels, SubscriptionNewsActivity.this);
        channelRecycler.setAdapter(channelAdapter);

        channelAdapter.setOnItemClickListener(new NewsChannelAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(SubscriptionNewsActivity.this, NewsActivity.class);
//                i.putExtras("channel", channel);
                startActivity(i);
                Toast.makeText(SubscriptionNewsActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.channel_back, R.id.channel_recycler})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.channel_back:
                break;
            case R.id.channel_recycler:
                break;
        }
    }
}
