package com.example.administrator.guosounews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.bean.NewsList;

import java.util.ArrayList;


public class CollectItemAdapter extends RecyclerView.Adapter {
    ArrayList<NewsList> channels;
    ArrayList<String> picUrls;
    Context ct;

    public CollectItemAdapter(ArrayList<NewsList> channels, Context ct, ArrayList<String> picUrls) {
        this.channels = channels;
        this.ct = ct;
        this.picUrls = picUrls;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ct).inflate(R.layout.layout_collect_item, parent, false);

        CollectViewHolder viewHolder=new CollectViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CollectViewHolder.item_title.setText(channels.get(position).title);
        CollectViewHolder.item_content.setText(channels.get(position).description);
        CollectViewHolder.item_time.setText(channels.get(position).timeString);
        CollectViewHolder.item_from.setText(channels.get(position).mname);

        Glide.with(ct).load(picUrls.get(position)).into(CollectViewHolder.item_img);
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class CollectViewHolder extends RecyclerView.ViewHolder {
        public static TextView item_content, item_title;
        public static TextView item_time, item_from;
        public static ImageView item_img;

        public CollectViewHolder(View view){
            super(view);
            item_content = (TextView)view.findViewById(R.id.tv_collect_item_content);
            item_title = (TextView)view.findViewById(R.id.tv_collect_item_title);
            item_time = (TextView)view.findViewById(R.id.tv_collect_item_time);
            item_from = (TextView)view.findViewById(R.id.tv_collect_item_from);
        }
    }

}
