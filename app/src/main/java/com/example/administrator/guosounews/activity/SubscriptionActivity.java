package com.example.administrator.guosounews.activity;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.adapter.SubscriptionItemAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SubscriptionActivity extends Activity {


    @InjectView(R.id.subscription_bar_sliding)
    ImageButton subscriptionBarSliding;
    @InjectView(R.id.subscription_recyclerView)
    RecyclerView subscriptionRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_subscription);
        ButterKnife.inject(this);

        initRecyclerView();
    }


    ArrayList<String> items = new ArrayList<String>();

    private void initRecyclerView() {
        items.add("爱范儿");
        items.add("新浪网-新闻要闻");
        items.add("");

        //设置固定大小
        subscriptionRecyclerView.setHasFixedSize(true);
        //创建线性布局
        mLayoutManager = new GridLayoutManager(this, 2);
        //给RecyclerView设置布局管理器
        subscriptionRecyclerView.setLayoutManager(mLayoutManager);
        //创建适配器，并且设置
        mAdapter = new SubscriptionItemAdapter(items, this);
        subscriptionRecyclerView.setAdapter(mAdapter);
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
