package com.example.administrator.guosounews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.guosounews.R;

import java.util.ArrayList;


public class SearchHistroyAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    public OnRecyclerViewItemClickListener mOnItemClickListener = null;
    ArrayList<String> histroyList;
    Context ct;

    public SearchHistroyAdapter(ArrayList<String> histroyList, Context ct) {
        this.histroyList = histroyList;
        this.ct = ct;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ct).inflate(R.layout.layout_search_histroy_item, parent, false);
        HistroyViewHolder viewHolder= new HistroyViewHolder(view);

        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        HistroyViewHolder.tv_histroy_content.setText(histroyList.get(position));

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
        return histroyList.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class HistroyViewHolder extends RecyclerView.ViewHolder {
        public static TextView tv_histroy_content;

        public HistroyViewHolder(View view){
            super(view);
            tv_histroy_content = (TextView)view.findViewById(R.id.tv_histroy_item_content);
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
