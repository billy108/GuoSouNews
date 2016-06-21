package com.example.administrator.guosounews.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.adapter.SubscriptionLeftItemAdapter;
import com.example.administrator.guosounews.adapter.SubscriptionRightItemAdapter;
import com.example.administrator.guosounews.bean.SubscriptionChannel;
import com.example.administrator.guosounews.utils.APIs;
import com.example.administrator.guosounews.utils.RecycleViewDivider;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SubscripManagerActivity extends Activity {
    public static final int SUBSRCIPMANAGERACTIVITY = 1;

    @InjectView(R.id.subscription_back)
    ImageView subscriptionBack;
    @InjectView(R.id.subscription_header)
    RelativeLayout subscriptionHeader;
    @InjectView(R.id.left_subscription)
    RecyclerView leftSubscription;
    @InjectView(R.id.right_subscription)
    RecyclerView rightSubscription;

    SubscriptionLeftItemAdapter leftAdapter;
    SubscriptionRightItemAdapter rightAdapter;
    ArrayList<SubscriptionChannel> channels = new ArrayList<SubscriptionChannel>();
    ArrayList<SubscriptionChannel> reading_channels = new ArrayList<SubscriptionChannel>();
    ArrayList<SubscriptionChannel> ententainment_channels = new ArrayList<SubscriptionChannel>();
    ArrayList<SubscriptionChannel> myChannels = new ArrayList<SubscriptionChannel>();
    ArrayList<String> leftContents = new ArrayList<String>();
    SubscriptionChannel channel;
    Bundle buten;
    boolean isMyChannel = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_subscrip_manager);
        ButterKnife.inject(this);


        initLeftView();
        initRightView();
    }

    /**
     * 初始化右侧子频道
     */
    private void initRightView() {
        createChannel();

        rightSubscription.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        //设置分割线
        rightSubscription.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL, 20, getResources().getColor(android.R.color.transparent)));

        rightAdapter = new SubscriptionRightItemAdapter(channels, this, SubscripManagerActivity.this);
        rightSubscription.setAdapter(rightAdapter);
    }

    /**
     * 创建各频道对象
     */
    private void createChannel() {
        for (int i = 0; i < APIs.reading_channel_title.length; i++) {
            reading_channels.add(new SubscriptionChannel(APIs.reading_channel_title[i],
                    APIs.reading_channel_content[i], false, SubscriptionActivity.readingURLs.get(i)));
        }
        for (int i = 0; i < APIs.entertainment_channel_title.length; i++) {
            ententainment_channels.add(new SubscriptionChannel(APIs.entertainment_channel_title[i],
                    APIs.entertainment_channel_content[i], false, SubscriptionActivity.entertainURLs.get(i)));
        }
    }


    private void initLeftView() {


        leftContents.add("我的订阅");
        leftContents.add("阅读");
        leftContents.add("娱乐");
//        leftContents.add("教育");
//        leftContents.add("美食");
//        leftContents.add("漫画");

        leftSubscription.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        leftAdapter = new SubscriptionLeftItemAdapter(leftContents, this);

        leftSubscription.setAdapter(leftAdapter);
        leftAdapter.setOnItemClickListener(new SubscriptionLeftItemAdapter.OnRecyclerViewItemClickListener() {

            //            String[] title;
//            String[] content;
            @Override
            public void onItemClick(View view, int postition) {
                switch (postition) {
                    case 0:
                        isMyChannel = true;
                        channels.clear();
                        channels.addAll(myChannels);
                        rightSubscription.setAdapter(rightAdapter);
                        break;
                    case 1:
                        editMyChannel(reading_channels);
                        break;
                    case 2:
                        editMyChannel(ententainment_channels);
                        break;
                }


                leftAdapter.setCurPostion(postition);
//                leftAdapter.notifyDataSetChanged();？？？？？？数据显示异常
                leftSubscription.setAdapter(leftAdapter);
            }
        });

    }


    /**
     * 添加、删除 我的订阅channel集合
     *
     * @param ischecked 判断添加（true）或者删除(false)操作
     * @param channel   数据
     */
    public void addToMySubscription(boolean ischecked, ArrayList<SubscriptionChannel> channel, int position) {

        if (isMyChannel) {
            channel.remove(channel.get(position));
            myChannels.remove(position);
            rightSubscription.setAdapter(rightAdapter);
        } else {
            if (ischecked) {
                channels.addAll(channel);
                myChannels.add(channel.get(position));
            } else {
                myChannels.remove(channel.get(position));
            }
        }

    }

    /**
     * 管理 我的订阅
     *
     * @param chanl
     */
    private void editMyChannel(ArrayList<SubscriptionChannel> chanl) {
        isMyChannel = false;
        channels.clear();
        if (chanl.size() == 0) {
        }
        if (channels.size() == 0) {
            channels.addAll(chanl);
        }
        rightSubscription.setAdapter(rightAdapter);
    }

    /**
     * 添加channel数据
     */
    private void setChannelList(ArrayList<SubscriptionChannel> channel) {
        channels.clear();
//        if (channel.size() == 0) {
//            for (int i = 0; i < title.length; i++) {
//                channel.add(new SubscriptionChannel(title[i], content[i], false, SubscriptionActivity.readingURLs.get(i)));
//            }
//        }
        channels.addAll(channel);
        rightSubscription.setAdapter(rightAdapter);

    }

    /**
     * 返回 我的订阅 信息
     */
    @OnClick(R.id.subscription_back)
    public void onClick() {

        Intent i = new Intent(this, SubscriptionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", myChannels);
        i.putExtras(bundle);
        startActivity(i);
    }

    /**
     * 设置我的订阅为默认显示
     */
    @Override
    protected void onResume() {
        super.onResume();
        leftAdapter.setCurPostion(0);
        leftSubscription.setAdapter(leftAdapter);
        if (channels.size() != 0) {
            channels.clear();
            channels.addAll(myChannels);
            rightSubscription.setAdapter(rightAdapter);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "12121", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, SubscriptionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", myChannels);
        i.putExtras(bundle);
        startActivity(i);
    }
}
