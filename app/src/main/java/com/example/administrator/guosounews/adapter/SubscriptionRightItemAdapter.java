package com.example.administrator.guosounews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.bean.SubscriptionChannel;

import java.util.ArrayList;


public class SubscriptionRightItemAdapter extends RecyclerView.Adapter {
    ArrayList<SubscriptionChannel> channels;
    Context ct;

    public SubscriptionRightItemAdapter(ArrayList<SubscriptionChannel> channels, Context ct) {
        this.channels = channels;
        this.ct = ct;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ct).inflate(R.layout.layout_subscription_right_item, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder.item_title.setText(channels.get(position).title);
        ViewHolder.item_content.setText(channels.get(position).content);
        ViewHolder.item_button.setSelected(false);
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public static TextView item_content, item_title;
        public static ImageView item_button;

        public ViewHolder(View view){
            super(view);
            item_content = (TextView)view.findViewById(R.id.subscription_right_item_content);
            item_title = (TextView)view.findViewById(R.id.subscription_right_item_title);
            item_button = (ImageView) view.findViewById(R.id.subscription_right_item_button);
        }
    }

}
