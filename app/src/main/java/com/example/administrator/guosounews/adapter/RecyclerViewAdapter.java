package com.example.administrator.guosounews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.bean.NewsCenterCategory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements View.OnClickListener{
    public OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private NewsCenterCategory ca;
    private Context ct;
    private int type;
    private View convertView;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;

    public RecyclerViewAdapter(NewsCenterCategory category, Context ct) {
        this.ca = category;
        this.ct = ct;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        convertView = LayoutInflater.from(ct).inflate(R.layout.layout_item_news_hot3, null);
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        type = getItemViewType(position);

        switch (type) {
            case TYPE_1:
                holder.ll_hot_news_item1.setVisibility(View.GONE);
                holder.ll_hot_news_item.setVisibility(View.VISIBLE);
                Glide.with(ct).load(ca.list.get(position).picture).into(holder.item_news_image);
                holder.item_news_title.setText(ca.list.get(position).title);
                holder.item_news_title.getPaint().setFakeBoldText(true);
                holder.item_news_from.setText(ca.list.get(position).mname);
                double time = ca.list.get(position).time;
                String fromTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date((int)time));
                holder.item_news_time.setText(fromTime);
                break;
            case TYPE_2:
                holder.ll_hot_news_item1.setVisibility(View.VISIBLE);
                holder.ll_hot_news_item.setVisibility(View.GONE);
                ImageView[] images = {holder.item2_news_image1,
                        holder.item2_news_image2,
                        holder.item2_news_image3};
                for (int i = 0; i < 3; i++) {
                    Glide.with(ct).load(ca.list.get(position).pictures.get(i))
                            .into(images[i]);
                }

                holder.item2_news_title.setText(ca.list.get(position).title);
                holder.item2_news_title.getPaint().setFakeBoldText(true);
                holder.item2_news_from.setText(ca.list.get(position).mname);
                double time2 = ca.list.get(position).time;
                String fromTime2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date((int)time2));
                holder.item2_news_time.setText(fromTime2);
                break;
        }
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
        return ca.list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout ll_hot_news_item;
        public ImageView item_news_image;
        public TextView item_news_title;
        public TextView item_news_from;
        public TextView item_news_time;

        public LinearLayout ll_hot_news_item1;
        public ImageView item2_news_image1;
        public ImageView item2_news_image2;
        public ImageView item2_news_image3;
        public TextView item2_news_title;
        public TextView item2_news_from;
        public TextView item2_news_time;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_hot_news_item = (LinearLayout) itemView.findViewById(R.id.ll_hot_news_item);
            item_news_image = (ImageView) itemView.findViewById(R.id.copy_item_news_image);
            item_news_title = (TextView) itemView.findViewById(R.id.copy_item_news_title);
            item_news_from = (TextView) itemView.findViewById(R.id.copy_item_news_from);
            item_news_time = (TextView) itemView.findViewById(R.id.copy_item_news_time);

            ll_hot_news_item1 = (LinearLayout) itemView.findViewById(R.id.ll_hot_news_item1);
            item2_news_image1 = (ImageView) itemView.findViewById(R.id.copy_item2_news_image1);
            item2_news_image2 = (ImageView) itemView.findViewById(R.id.copy_item2_news_image2);
            item2_news_image3 = (ImageView) itemView.findViewById(R.id.copy_item2_news_image3);
            item2_news_title = (TextView) itemView.findViewById(R.id.copy_item2_news_title);
            item2_news_from = (TextView) itemView.findViewById(R.id.copy_item2_news_from);
            item2_news_time = (TextView) itemView.findViewById(R.id.copy_item2_news_time);

        }
    }

    /**
     * 获取新闻布局类型
     * @param position
     * @return
     */
    public int getItemViewType(int position) {
        if (ca.list.get(position).pictures == null) {
            return 0;
        } else {
            return 1;
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
