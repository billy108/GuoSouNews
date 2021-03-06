package com.example.administrator.guosounews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.guosounews.R;

import java.util.ArrayList;

/**
 * 为RecyclerView添加item的点击事件
 * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0327/2647.html
 */

public class SubscriptionLeftItemAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    public OnRecyclerViewItemClickListener mOnItemClickListener = null;
    ArrayList<String> items;
    Context ct;
    private int curPostion = 0;

    public SubscriptionLeftItemAdapter(ArrayList<String> items, Context ct) {
        this.items = items;
        this.ct = ct;
    }

    public void setCurPostion(int postion){
        this.curPostion = postion;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ct).inflate(R.layout.layout_subscription_left_item, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SubscriptionLeftItemAdapter.ViewHolder.item_content.setText(items.get(position));
        if (curPostion == position) {
            SubscriptionLeftItemAdapter.ViewHolder.item_content
                .setTextColor(ct.getResources().getColor(R.color.red));
            SubscriptionLeftItemAdapter.ViewHolder.item_content
                .setBackgroundColor(ct.getResources().getColor(R.color.transparent_black));
        } else {
            SubscriptionLeftItemAdapter.ViewHolder.item_content
                    .setTextColor(ct.getResources().getColor(R.color.white));
            SubscriptionLeftItemAdapter.ViewHolder.item_content
                    .setBackgroundColor(ct.getResources().getColor(R.color.black));
        }
        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public static TextView item_content;

        public ViewHolder(View view){
            super(view);
            item_content = (TextView)view.findViewById(R.id.left_subscription_content);
        }

    }

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
