package com.example.administrator.guosounews.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.bean.NewsChannel;
import com.squareup.picasso.Picasso;

public class NewsChannelAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    public OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private NewsChannel caty;
    private Context ct;

    public NewsChannelAdapter(NewsChannel caty, Context ct) {
        this.caty = caty;
        this.ct = ct;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ct).inflate(R.layout.layout_channle_item, parent, false);
        ViewHolerChandnel holerChandnel = new ViewHolerChandnel(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return holerChandnel;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ViewHolerChandnel.channel_item_titel.setText(caty.articleList.get(position).title);
        ViewHolerChandnel.channel_item_content.setText(caty.articleList.get(position).abstractContent);
        ViewHolerChandnel.channel_item_from.setText(caty.mname);
        ViewHolerChandnel.channel_item_time.setText(caty.articleList.get(position).time);

        Picasso.with(ct).load(caty.articleList.get(position).picture)
                .config(Bitmap.Config.RGB_565).error(R.drawable.dot)
                .into(ViewHolerChandnel.channel_item_img);

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return caty.articleList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public static class ViewHolerChandnel extends RecyclerView.ViewHolder{
        public static TextView channel_item_titel, channel_item_content, channel_header_titel;
        public static TextView channel_item_from, channel_item_time;
        public static ImageView channel_item_img;

        public ViewHolerChandnel(View itemView) {
            super(itemView);
            channel_header_titel = (TextView) itemView.findViewById(R.id.channel_header_title);
            channel_item_titel = (TextView) itemView.findViewById(R.id.channel_item_titel);
            channel_item_content = (TextView) itemView.findViewById(R.id.channel_item_content);
            channel_item_from = (TextView) itemView.findViewById(R.id.channel_item_from);
            channel_item_time = (TextView) itemView.findViewById(R.id.channel_item_time);
            channel_item_img = (ImageView) itemView.findViewById(R.id.channel_item_img);

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
