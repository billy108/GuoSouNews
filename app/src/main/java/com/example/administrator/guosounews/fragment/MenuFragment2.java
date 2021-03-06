package com.example.administrator.guosounews.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.activity.SubscriptionActivity;
import com.example.administrator.guosounews.adapter.MyBaseAdapter;
import com.example.administrator.guosounews.base.BaseFragment;
import com.lidroid.xutils.ViewUtils;

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
        ViewUtils.inject(this, view);
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

                        break;
                }
                sm.toggle();
            }
        });
        return view;
    }

    private List<String> menuList = new ArrayList<String>();
    public void initMenu(List<String> menuNewCenterList) {
        menuList.clear();
        menuList.addAll(menuNewCenterList);
        if (adapter == null) {
            adapter = new MenuAdapter(menuList, ct);
            lv_menu_news_center.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        adapter.setCurPostion(0);
    }

    class MenuAdapter extends MyBaseAdapter {
        private int curPostion = 0;
        Integer[] left_menu_item_icon = {R.drawable.left_menu_home,
                R.drawable.left_menu_subscription,
                R.drawable.left_menu_vote
        };

        Integer[] left_menu_item_icon_selected = {R.drawable.left_menu_home_selected,
                R.drawable.left_menu_subscription_selected,
                R.drawable.left_menu_vote_selected
        };

        public MenuAdapter(List list, Context context) {
            super(list, context);
        }

        public void setCurPostion(int postion){
            this.curPostion = postion;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = view.inflate(ct, R.layout.layout_item_menu, null);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.tv_menu_item);
            ImageView iv = (ImageView) convertView.findViewById(R.id.iv_menu_item);
            tv.setText(menuList.get(position));
            iv.setBackgroundResource(left_menu_item_icon[position]);

            if (curPostion == position) {
                tv.setTextColor(getResources().getColor(R.color.colorAccent));
                iv.setBackgroundResource(left_menu_item_icon_selected[position]);
            } else {
                tv.setTextColor(getResources().getColor(R.color.white));
                iv.setBackgroundResource(left_menu_item_icon[position]);
//                convertView.setBackgroundResource(R.drawable.enter_btn_normal);
            }
            return convertView;
        }
    }
}
