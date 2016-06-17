package com.example.administrator.guosounews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.guosounews.R;

import java.util.ArrayList;

/**
 * 为RecyclerView添加item的点击事件
 * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0327/2647.html
 */

public class SubscriptionItemAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    public OnRecyclerViewItemClickListener mOnItemClickListener = null;
    ArrayList<String> items;
    private LayoutInflater mInflater;
    Context ct;

    public SubscriptionItemAdapter(ArrayList<String> items, Context ct) {
        this.items = items;
        this.ct = ct;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ct).inflate(R.layout.fragment_subscrip_item, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == (items.size() -1)) {
            ViewHolder.item_title.setText("添加频道");
            ViewHolder.item_content.setVisibility(View.GONE);
            ViewHolder.item_img.setVisibility(View.VISIBLE);
        } else {
            ViewHolder.item_title.setText(items.get(position));
            ViewHolder.item_content.setText(items.get(position));
        }

        //将数据保存在itemView的Tag中，以便点击时进行获取
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
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public static TextView item_title, item_content;
        public static ImageView item_img;

        public ViewHolder(View view){
            super(view);
            item_title = (TextView)view.findViewById(R.id.subscription_item_titel);
            item_content = (TextView)view.findViewById(R.id.subscription_item_content);
            item_img = (ImageView) view.findViewById(R.id.subscription_item_img);
        }
    }

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
