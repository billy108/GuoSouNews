package com.example.administrator.guosounews.activity;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.adapter.SubscriptionLeftItemAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SubscripManagerActivity extends Activity {

    @InjectView(R.id.subscription_back)
    ImageView subscriptionBack;
    @InjectView(R.id.subscription_header)
    RelativeLayout subscriptionHeader;
    @InjectView(R.id.left_subscription)
    RecyclerView leftSubscription;
    @InjectView(R.id.right_subscription)
    RecyclerView rightSubscription;

    SubscriptionLeftItemAdapter leftAdapter;
    ArrayList<String> leftContents = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_subscrip_manager);
        ButterKnife.inject(this);

        initLeftView();
        initRightView();
    }

    private void initRightView() {

    }

    private void initLeftView() {
        leftContents.add("我的订阅");
        leftContents.add("阅读");
        leftContents.add("娱乐");
        leftContents.add("教育");
        leftContents.add("美食");
        leftContents.add("漫画");

        leftSubscription.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        leftAdapter = new SubscriptionLeftItemAdapter(leftContents, this);
        leftSubscription.setAdapter(leftAdapter);
        leftAdapter.setOnItemClickListener(new SubscriptionLeftItemAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int postition) {
                leftAdapter.setCurPostion(postition);
//                leftAdapter.notifyDataSetChanged();？？？？？？数据显示异常
                leftSubscription.setAdapter(leftAdapter);
            }
        });
    }

    @OnClick(R.id.subscription_back)
    public void onClick() {
    }
}
