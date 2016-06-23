package com.example.administrator.guosounews.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.bean.NewsList;
import com.example.administrator.guosounews.fragment.CollectFragment;
import com.example.administrator.guosounews.fragment.HistroyFragment;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CollectActivity extends Activity {
    public static ArrayList<NewsList> collectNews;
    public static ArrayList<NewsList> histroyNews;

    private FragmentTransaction fragmentTransaction;
    Fragment collectFragment = new CollectFragment();
    Fragment histroyFragment = new HistroyFragment();

    @InjectView(R.id.collect_back)
    ImageView collectBack;
    @InjectView(R.id.edit)
    ImageView edit;
    @InjectView(R.id.tl_collect)
    TabLayout tlCollect;
    @InjectView(R.id.tv_collect_title)
    TextView tvCollectTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_collect);
        ButterKnife.inject(this);

        initTab();
        initContent();
    }

    private void initContent() {

        FragmentManager fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();

    }

    private void initTab() {
        TabLayout.Tab tab1 = tlCollect.newTab().setText("收藏");
        tlCollect.addTab(tab1);
        TabLayout.Tab tab2 = tlCollect.newTab().setText("历史");
        tlCollect.addTab(tab2);

        tlCollect.setTabTextColors(Color.GRAY, Color.RED);

        tlCollect.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int postiton = tab.getPosition();
                switch (postiton) {
                    case 0:
                        tvCollectTitle.setText("收藏");
                        edit.setBackground(getResources().getDrawable(R.drawable.header_right_icon_edit_selected));
                        fragmentTransaction.replace(R.id.fl_collect, collectFragment);
                        break;
                    case 1:
                        tvCollectTitle.setText("历史");
                        edit.setBackground(getResources().getDrawable(R.drawable.header_right_icon_del_selected));
                        fragmentTransaction.replace(R.id.fl_collect, histroyFragment);
                        break;
                }

                fragmentTransaction.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @OnClick({R.id.collect_back, R.id.edit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.collect_back:
                break;
            case R.id.edit:
                break;
        }
    }

}
