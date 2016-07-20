package com.example.administrator.guosounews.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.activity.SubscriptionActivity;
import com.example.administrator.guosounews.adapter.MenuAdapter;
import com.example.administrator.guosounews.base.BaseFragment;
import com.example.administrator.guosounews.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment2 extends BaseFragment {
    private View view;
    private ListView lv_menu_news_center;
    private MenuAdapter adapter;

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.layout_left_menu, null);
        lv_menu_news_center = (ListView) view.findViewById(R.id.lv_menu_news_center);
        lv_menu_news_center.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setCurPostion(position);
                switch (position) {
                    case 1:
                        Intent i = new Intent(ct, SubscriptionActivity.class);
                        startActivity(i);
                        break;
                    case 2:
                        ToastUtil.showShort(ct, "敬请期待！");
                        break;
                    default:
                        sm.toggle();
                }

            }
        });
        return view;
    }

    private List<String> menuList = new ArrayList<String>();
    public void initMenu(List<String> menuNewCenterList) {
        menuList.clear();
        menuList.addAll(menuNewCenterList);
        if (adapter == null) {
            adapter = new MenuAdapter(menuList, ct, view);
            lv_menu_news_center.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        adapter.setCurPostion(0);
    }
}
