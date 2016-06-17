package com.example.administrator.guosounews.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.adapter.SubscriptionItemAdapter;
import com.example.administrator.guosounews.bean.SubscriptionChannel;
import com.example.administrator.guosounews.utils.APIs;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SubscriptionActivity extends Activity {

    @InjectView(R.id.subscription_bar_sliding)
    ImageButton subscriptionBarSliding;
    @InjectView(R.id.subscription_recyclerView)
    RecyclerView subscriptionRecyclerView;

    private SubscriptionItemAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    ArrayList<SubscriptionChannel> subLIst;
    ArrayList<SubscriptionChannel> mychannels = new ArrayList<SubscriptionChannel>();
    static ArrayList<String> readingURLs = new ArrayList<String>();
    static ArrayList<String> entertainURLs = new ArrayList<String>();
    View view;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_subscription);
        ButterKnife.inject(this);

        setURL();
        initRecyclerView();
    }

    private void setURL() {
        readingURLs.add(APIs.READING_TIGERSMELL);
        readingURLs.add(APIs.READING_NEXTCAR);
        readingURLs.add(APIs.READING_SINA);
        readingURLs.add(APIs.READING_SINAINSIED);

        entertainURLs.add(APIs.ENTERTAINMENT_MAFADA);
        entertainURLs.add(APIs.ENTERTAINMENT_ATS);
        entertainURLs.add(APIs.ENTERTAINMENT_STAR);
    }


    ArrayList<String> items = new ArrayList<String>();

    private void initRecyclerView() {
        bundle = getIntent().getExtras();
        if (bundle != null) {
            subLIst = (ArrayList<SubscriptionChannel>) bundle.getSerializable("data");
            for (int i =0; i < subLIst.size(); i++) {
                if (!mychannels.contains(subLIst.get(i))) {
                    mychannels.add(subLIst.get(i));
                }
            }
        }
        items.clear();
        if (mychannels.size() > 0) {
            for (int i = 0; i < mychannels.size(); i ++) {
                items.add(mychannels.get(i).title);
            }
        }
        items.add(" ");

        //设置固定大小
        subscriptionRecyclerView.setHasFixedSize(true);
        //创建线性布局
        mLayoutManager = new GridLayoutManager(this, 2);
        //给RecyclerView设置布局管理器
        subscriptionRecyclerView.setLayoutManager(mLayoutManager);
        //创建适配器，并且设置
        mAdapter = new SubscriptionItemAdapter(items, this);
        subscriptionRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SubscriptionItemAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int postiton) {
                Intent i;
                if (postiton == (items.size() - 1)) {
                    i = new Intent(SubscriptionActivity.this, SubscripManagerActivity.class);
                    if (bundle != null) {
                        i.putExtras(bundle);
                    }
                } else {
                    i = new Intent(SubscriptionActivity.this, SubscriptionNewsActivity.class);

                }
                startActivity(i);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.subscription_bar_sliding)
    public void onClick() {
    }

}
