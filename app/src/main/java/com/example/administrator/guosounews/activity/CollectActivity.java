package com.example.administrator.guosounews.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.adapter.CollectAdapter;
import com.example.administrator.guosounews.adapter.CollectItemAdapter;
import com.example.administrator.guosounews.utils.RecycleViewDivider;
import com.example.administrator.guosounews.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CollectActivity extends FragmentActivity {

    private FragmentManager fm;
    private View collectView, histroyView;
    private ArrayList<View> mViewList = new ArrayList<>();
    private LayoutInflater inflater;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合

    @InjectView(R.id.vp_collect)
    ViewPager vpCollect;
    @InjectView(R.id.collect_back)
    ImageView collectBack;
    @InjectView(R.id.iv_collect_edit)
    ImageView edit;
    @InjectView(R.id.iv_collect_ok)
    ImageView ok;
    @InjectView(R.id.tl_collect)
    TabLayout tlCollect;
    @InjectView(R.id.tv_collect_title)
    TextView tvCollectTitle;

    private RecyclerView revCollect;
    private RecyclerView revHistroy;

    private CollectItemAdapter collectAdapter;
    private int currPostiton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.inject(this);

        initContent();
        initTab();
        initData();
    }

    private void initData() {

        //填充收藏过的数据
        revCollect = (RecyclerView) collectView.findViewById(R.id.rev_collcet);
        revCollect.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        revCollect.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL));

        if (NewsActivity.collectNews.size() != 0) {
            collectAdapter = new CollectItemAdapter(NewsActivity.collectNews, this, NewsActivity.collcetpicUrls);
            revCollect.setAdapter(collectAdapter);
            setOnclikcitem();
        }

        //填充历史记录数据
        revHistroy = (RecyclerView) histroyView.findViewById(R.id.rev_histroy);
        revHistroy.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        revHistroy.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL));
        if (NewsActivity.histroyNews.size() != 0) {
            collectAdapter = new CollectItemAdapter(NewsActivity.histroyNews, this, NewsActivity.histroypicUrls);
            revHistroy.setAdapter(collectAdapter);
            setOnclikcitem();
        }

    }

    private void setOnclikcitem(){
        collectAdapter.setOnItemClickListener(new CollectItemAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
            }
        });

        collectAdapter.setOnItemClickListener(new CollectItemAdapter.OnRecyclerViewItemDeleteClickListener() {
            @Override
            public void onItemDeleteClick(int position) {
            }
        });
    }

    private void initContent() {
        mTitleList.add("收藏");
        mTitleList.add("历史");
        inflater = LayoutInflater.from(this);
        collectView = inflater.inflate(R.layout.fragment_collection, null);
        histroyView = inflater.inflate(R.layout.fragment_histroy, null);

        mViewList.add(collectView);
        mViewList.add(histroyView);

        CollectAdapter adapter = new CollectAdapter(mViewList, mTitleList);

        vpCollect.setAdapter(adapter);
        tlCollect.setupWithViewPager(vpCollect);//将TabLayout和ViewPager关联起来。
    }

    private void initTab() {
        tlCollect.setTabTextColors(Color.GRAY, Color.RED);

        tlCollect.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currPostiton = tab.getPosition();
                switch (currPostiton) {
                    case 0:
                        tvCollectTitle.setText("收藏");
                        edit.setBackground(getResources().getDrawable(R.drawable.header_right_icon_edit_selected));
                        break;
                    case 1:
                        tvCollectTitle.setText("历史");
                        edit.setBackground(getResources().getDrawable(R.drawable.header_right_icon_del_selected));
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    /**
     * 删除收藏和历史记录
     * @param view
     */
    @OnClick({R.id.collect_back, R.id.iv_collect_edit, R.id.iv_collect_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_collect_ok:
                edit.setVisibility(View.VISIBLE);
                ok.setVisibility(View.GONE);
                collectAdapter.setDeleteShown(false);
                break;
            case R.id.collect_back:
                finish();
                break;
            case R.id.iv_collect_edit:
                if (currPostiton == 0) {
                    edit.setVisibility(View.GONE);
                    ok.setVisibility(View.VISIBLE);
                    collectAdapter.setDeleteShown(true);
                } else {
                    if (NewsActivity.histroyNews.size() > 0) {
                        new AlertDialog.Builder(this)
                                .setTitle("确定删除历史记录？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        NewsActivity.histroyNews.clear();
                                        revHistroy.setAdapter(collectAdapter);
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();
                    } else {
                        ToastUtil.showShort(CollectActivity.this, "历史记录已为空");
                    }

                }


                break;
        }
    }

}
