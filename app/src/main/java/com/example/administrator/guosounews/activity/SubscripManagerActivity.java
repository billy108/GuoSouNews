package com.example.administrator.guosounews.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.adapter.SubscriptionLeftItemAdapter;
import com.example.administrator.guosounews.adapter.SubscriptionRightItemAdapter;
import com.example.administrator.guosounews.bean.SubscriptionChannel;
import com.example.administrator.guosounews.utils.APIs;

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

    private void getSubscriptionIntent() {
        buten = getIntent().getExtras();
        if (buten != null) {
            myChannels.addAll((ArrayList<SubscriptionChannel>)buten.getSerializable("data"));
            channels.addAll(myChannels);
        }
    }


    private void initRightView() {
        createChannel();


        getSubscriptionIntent();
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
     * @param ischecked 判断添加（true）或者删除(false)操作
     * @param channel 数据
     */
    public void addToMySubscription(boolean ischecked, ArrayList<SubscriptionChannel> channel, int position){

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
     * @param chanl
     */
    private void editMyChannel(ArrayList<SubscriptionChannel> chanl){
        isMyChannel = false;
        channels.clear();
        if (channels.size() == 0) {
            channels.addAll(chanl);
        }
        rightSubscription.setAdapter(rightAdapter);
    }

    /**
     * 添加channel数据
     */
    private void setChannelList(ArrayList<SubscriptionChannel> channel){
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
     * 分割线设置
     */
    public class RecycleViewDivider extends RecyclerView.ItemDecoration {

        private Paint mPaint;
        private Drawable mDivider;
        private int mDividerHeight = 2;//分割线高度，默认为1px
        private int mOrientation;//列表的方向：LinearLayoutManager.VERTICAL或LinearLayoutManager.HORIZONTAL
        private final int[] ATTRS = new int[]{android.R.attr.listDivider};

        /**
         * 默认分割线：高度为2px，颜色为灰色
         *
         * @param context
         * @param orientation 列表方向
         */
        public RecycleViewDivider(Context context, int orientation) {
            if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
                throw new IllegalArgumentException("请输入正确的参数！");
            }
            mOrientation = orientation;

            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        }

        /**
         * 自定义分割线
         *
         * @param context
         * @param orientation 列表方向
         * @param drawableId  分割线图片
         */
        public RecycleViewDivider(Context context, int orientation, int drawableId) {
            this(context, orientation);
            mDivider = ContextCompat.getDrawable(context, drawableId);
            mDividerHeight = mDivider.getIntrinsicHeight();
        }

        /**
         * 自定义分割线
         *
         * @param context
         * @param orientation   列表方向
         * @param dividerHeight 分割线高度
         * @param dividerColor  分割线颜色
         */
        public RecycleViewDivider(Context context, int orientation, int dividerHeight, int dividerColor) {
            this(context, orientation);
            mDividerHeight = dividerHeight;
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(dividerColor);
            mPaint.setStyle(Paint.Style.FILL);
        }


        //获取分割线尺寸
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, 0, 0, mDividerHeight);
        }

        //绘制分割线
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            if (mOrientation == LinearLayoutManager.VERTICAL) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }

        //绘制横向 item 分割线
        private void drawHorizontal(Canvas canvas, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
            final int childSize = parent.getChildCount();
            for (int i = 0; i < childSize; i++) {
                final View child = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getBottom() + layoutParams.bottomMargin;
                final int bottom = top + mDividerHeight;
                if (mDivider != null) {
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
                if (mPaint != null) {
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            }
        }

        //绘制纵向 item 分割线
        private void drawVertical(Canvas canvas, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
            final int childSize = parent.getChildCount();
            for (int i = 0; i < childSize; i++) {
                final View child = parent.getChildAt(i);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int left = child.getRight() + layoutParams.rightMargin;
                final int right = left + mDividerHeight;
                if (mDivider != null) {
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
                if (mPaint != null) {
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            }
        }
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
        finish();
    }
}
