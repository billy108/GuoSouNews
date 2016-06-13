package com.example.administrator.guosounews.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.bean.NewsSpecial;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 新闻List的adapter
 */
public class SpecialNewsListAdapter extends BaseAdapter {
    private Context ct;

    NewsSpecial ca;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;

    public SpecialNewsListAdapter(NewsSpecial category, Context ct) {
        this.ca = category;
        this.ct = ct;
    }

    @Override
    public int getItemViewType(int position) {
        if (ca.list.get(position).pictures != null) {
            return TYPE_2;
        } else {
            return TYPE_1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return ca.list.size();
    }

    @Override
    public Object getItem(int position) {
        return ca.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ViewHolder2 holder2 = null;

        int type = getItemViewType(position);

        if (convertView == null) {
            switch (type) {
                case TYPE_1:
                    convertView = LayoutInflater.from(ct).inflate(R.layout.layout_item_news_hot, null);
                    holder = new ViewHolder();
                    holder.item_news_image = (ImageView) convertView.findViewById(R.id.item_news_image);
                    holder.item_news_title = (TextView) convertView.findViewById(R.id.item_news_title);
                    holder.item_news_from = (TextView) convertView.findViewById(R.id.item_news_from);
                    holder.item_news_time = (TextView) convertView.findViewById(R.id.item_news_time);
                    convertView.setTag(holder);
                    break;
                case TYPE_2:
                    convertView = LayoutInflater.from(ct).inflate(R.layout.layout_item_news_hot2, null);
                    holder2 = new ViewHolder2();
                    holder2.item2_news_image1 = (ImageView) convertView.findViewById(R.id.item2_news_image1);
                    holder2.item2_news_image2 = (ImageView) convertView.findViewById(R.id.item2_news_image2);
                    holder2.item2_news_image3 = (ImageView) convertView.findViewById(R.id.item2_news_image3);
                    holder2.item_news_title = (TextView) convertView.findViewById(R.id.item2_news_title);
                    holder2.item_news_from = (TextView) convertView.findViewById(R.id.item2_news_from);
                    holder2.item_news_time = (TextView) convertView.findViewById(R.id.item2_news_time);
                    convertView.setTag(holder2);
                    break;
            }

        } else {
            switch (type) {
                case TYPE_1:
                    holder = (ViewHolder) convertView.getTag();
                    break;
                case TYPE_2:
                    holder2 = (ViewHolder2) convertView.getTag();
                    break;
            }

        }

        switch (type) {
            case TYPE_1:
                //Picasso 加载网络图片
                Picasso.with(ct).load(ca.list.get(position).picture)
                        .config(Bitmap.Config.RGB_565).error(R.drawable.dot)
                        .into(holder.item_news_image);
                //BitmapUtils 加载网络图片
//					BitmapUtils bitmapUtils = new BitmapUtils(ct);
//					bitmapUtils.display(holder.item_news_image, ca.list.get(position).picture);

                holder.item_news_title.setText(ca.list.get(position).title);
                holder.item_news_title.getPaint().setFakeBoldText(true);
                holder.item_news_from.setText(ca.list.get(position).mname);
                double time = ca.list.get(position).time;
                String fromTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date((int)time));
                holder.item_news_time.setText(fromTime);
                break;
            case TYPE_2:
                ImageView[] images = {holder2.item2_news_image1,
                        holder2.item2_news_image2,
                        holder2.item2_news_image3};
                for (int i = 0; i < 3; i++) {
                    Picasso.with(ct).load(ca.list.get(position).pictures.get(i))
                            .config(Bitmap.Config.RGB_565).error(R.drawable.dot)
                            .into(images[i]);
                }

                holder2.item_news_title.setText(ca.list.get(position).title);
                holder2.item_news_title.getPaint().setFakeBoldText(true);
                holder2.item_news_from.setText(ca.list.get(position).mname);
                double time2 = ca.list.get(position).time;
                String fromTime2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date((int)time2));
                holder2.item_news_time.setText(fromTime2);
                break;
        }


        return convertView;
    }

    class ViewHolder{
        public ImageView item_news_image;
        public TextView item_news_title;
        public TextView item_news_from;
        public TextView item_news_time;
    }

    class ViewHolder2{
        public ImageView item2_news_image1;
        public ImageView item2_news_image2;
        public ImageView item2_news_image3;
        public TextView item_news_title;
        public TextView item_news_from;
        public TextView item_news_time;
    }
}

