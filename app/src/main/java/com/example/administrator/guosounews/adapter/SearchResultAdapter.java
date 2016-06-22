package com.example.administrator.guosounews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.bean.NewsSearch;


public class SearchResultAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    public OnRecyclerViewItemClickListener mOnItemClickListener = null;
    NewsSearch newsSearches;
    Context ct;

    public SearchResultAdapter(NewsSearch newsSearches, Context ct) {
        this.newsSearches = newsSearches;
        this.ct = ct;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ct).inflate(R.layout.layout_search_result_item, parent, false);

        ResultViewHolder viewHolder=new ResultViewHolder(view);

        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ResultViewHolder.tv_reslut_title.setText(Html.fromHtml(newsSearches.newsList.get(position).title));
        ResultViewHolder.tv_reslut_from.setText(newsSearches.newsList.get(position).mname);
        ResultViewHolder.tv_reslut_time.setText(newsSearches.newsList.get(position).time + "");

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return newsSearches.newsList.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ResultViewHolder extends RecyclerView.ViewHolder {
        public static TextView tv_reslut_title;
        public static TextView tv_reslut_from;
        public static TextView tv_reslut_time;

        public ResultViewHolder(View view){
            super(view);
            tv_reslut_title = (TextView)view.findViewById(R.id.tv_result_item_title);
            tv_reslut_from = (TextView)view.findViewById(R.id.tv_result_item_from);
            tv_reslut_time = (TextView)view.findViewById(R.id.tv_result_item_time);
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
