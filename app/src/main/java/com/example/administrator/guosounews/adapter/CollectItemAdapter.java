package com.example.administrator.guosounews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.bean.NewsList;

import java.util.ArrayList;


public class CollectItemAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener;
    private OnRecyclerViewItemDeleteClickListener mOnItemDeleteClickListener;
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
        CollectViewHolder.rl_collcet_item.setOnClickListener(this);
        CollectViewHolder.delete_img.setOnClickListener(this);

        if (picUrls.size() != 0) {
            Glide.with(ct).load(picUrls.get(position)).into(CollectViewHolder.item_img);
        }
        CollectViewHolder.delete_img.setTag(position);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_collect_item:
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.OnItemClick(v, (int)v.getTag());
                }
                break;
            case R.id.iv_collect_item_delete:
                mOnItemDeleteClickListener.onItemDeleteClick((int)v.getTag());
                break;
        }

    }

    public void setDeleteShown(boolean shown) {
        if (shown) {
            CollectViewHolder.delete_img.setVisibility(View.VISIBLE);
        } else {
            CollectViewHolder.delete_img.setVisibility(View.GONE);
        }
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class CollectViewHolder extends RecyclerView.ViewHolder {
        public static TextView item_content, item_title;
        public static TextView item_time, item_from;
        public static ImageView item_img;
        public static ImageView delete_img;
        public static RelativeLayout rl_collcet_item;

        public CollectViewHolder(View view){
            super(view);
            item_content = (TextView)view.findViewById(R.id.tv_collect_item_content);
            item_title = (TextView)view.findViewById(R.id.tv_collect_item_title);
            item_time = (TextView)view.findViewById(R.id.tv_collect_item_time);
            item_from = (TextView)view.findViewById(R.id.tv_collect_item_from);
            item_img = (ImageView) view.findViewById(R.id.iv_collect_item_img);
            item_img.setSelected(false);
            rl_collcet_item = (RelativeLayout) view.findViewById(R.id.rl_collect_item);
            item_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            delete_img = (ImageView) view.findViewById(R.id.iv_collect_item_delete);
        }
    }

    public static interface OnRecyclerViewItemClickListener{
        void OnItemClick(View v, int position);
    }

    public static interface OnRecyclerViewItemDeleteClickListener{
        void onItemDeleteClick(int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemClickListener(OnRecyclerViewItemDeleteClickListener listener) {
        this.mOnItemDeleteClickListener = listener;
    }
}
