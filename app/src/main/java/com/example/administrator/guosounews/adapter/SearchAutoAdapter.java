package com.example.administrator.guosounews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.guosounews.R;

import java.util.ArrayList;


public class SearchAutoAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    public OnRecyclerViewItemClickListener mOnItemClickListener = null;
    ArrayList<String> autoList;
    Context ct;

    public SearchAutoAdapter(ArrayList<String> autoList, Context ct) {
        this.autoList = autoList;
        this.ct = ct;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ct).inflate(R.layout.layout_search_auto_item, parent, false);
        AutoViewHolder viewHolder= new AutoViewHolder(view);

        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        AutoViewHolder.tv_auto_content.setText(autoList.get(position));

        holder.itemView.setTag(position);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    @Override
    public int getItemCount() {
        return autoList.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class AutoViewHolder extends RecyclerView.ViewHolder {
        public static TextView tv_auto_content;

        public AutoViewHolder(View view){
            super(view);
            tv_auto_content = (TextView)view.findViewById(R.id.tv_auto_item_content);
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
