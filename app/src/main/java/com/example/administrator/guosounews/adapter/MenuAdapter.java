package com.example.administrator.guosounews.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.guosounews.R;

import java.util.List;

public class MenuAdapter extends MyBaseAdapter {
    private int curPostion = 0;
    private Context context;
    private List<String> menuList;
    private View view;

    Integer[] left_menu_item_icon = {R.drawable.left_menu_home,
            R.drawable.left_menu_subscription,
            R.drawable.left_menu_vote
    };

    Integer[] left_menu_item_icon_selected = {R.drawable.left_menu_home_selected,
            R.drawable.left_menu_subscription_selected,
            R.drawable.left_menu_vote_selected
    };

    public MenuAdapter(List list, Context context, View view) {
        super(list, context);
        this.context = context;
        this.menuList = list;
        this.view = view;
    }

    public void setCurPostion(int postion){
        this.curPostion = postion;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = view.inflate(context, R.layout.layout_item_menu, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.tv_menu_item);
        ImageView iv = (ImageView) convertView.findViewById(R.id.iv_menu_item);
        tv.setText(menuList.get(position));
        iv.setBackgroundResource(left_menu_item_icon[position]);

        if (curPostion == position) {
            tv.setTextColor(context.getResources().getColor(R.color.colorAccent));
            iv.setBackgroundResource(left_menu_item_icon_selected[position]);
        } else {
            tv.setTextColor(context.getResources().getColor(R.color.white));
            iv.setBackgroundResource(left_menu_item_icon[position]);
        }
        return convertView;
    }
}
