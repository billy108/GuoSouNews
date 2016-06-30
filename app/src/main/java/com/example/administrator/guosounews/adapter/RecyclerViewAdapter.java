package com.example.administrator.guosounews.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.activity.SettingsActivity;
import com.example.administrator.guosounews.bean.NewsCenterCategory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements View.OnClickListener{
    public OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private NewsCenterCategory ca;
    private Context ct;
    private int type ;
    private View convertView;
    private View mHeaderView;
    private int lastPointPostion;

    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    final int TYPE_HEADER = 3;

    public RecyclerViewAdapter(NewsCenterCategory category, Context ct) {
        this.ca = category;
        this.ct = ct;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (type) {
            case TYPE_1:
                convertView = LayoutInflater.from(ct).inflate(R.layout.layout_item_news_hot, null);
                break;
            case TYPE_2:
                convertView = LayoutInflater.from(ct).inflate(R.layout.layout_item_news_hot2, null);
                break;
            case TYPE_HEADER:
                convertView = mHeaderView;
                break;
        }
        return new ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        position = getRealPosition(holder);
        switch (type) {
            case TYPE_1:
                if (!SettingsActivity.isLoadImage) {
                    Glide.with(ct).load(ca.list.get(position).picture).into(holder.item_news_image);
                }

                holder.item_news_title.setText(ca.list.get(position).title);
                holder.item_news_title.getPaint().setFakeBoldText(true);
                holder.item_news_from.setText(ca.list.get(position).mname);
                double time = ca.list.get(position).time;
                String fromTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date((int)time));
                holder.item_news_time.setText(fromTime);
                break;
            case TYPE_2:
                ImageView[] images = {holder.item2_news_image1,
                        holder.item2_news_image2,
                        holder.item2_news_image3};
                for (int i = 0; i < 3; i++) {
                    Glide.with(ct).load(ca.list.get(position).pictures.get(i))
                            .into(images[i]);
                }

                holder.item_news_title.setText(ca.list.get(position).title);
                holder.item_news_title.getPaint().setFakeBoldText(true);
                holder.item_news_from.setText(ca.list.get(position).mname);
                double time2 = ca.list.get(position).time;
                String fromTime2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date((int)time2));
                holder.item_news_time.setText(fromTime2);
                break;
            case TYPE_HEADER:
                final ArrayList<ImageView> imageList = new ArrayList<>();
                holder.news_viewpager_text.setText(ca.slide.get(0).title);
                //加载图片
                for (int i = 0; i < 4; i++) {
                    ImageView im = new ImageView(ct);
                    im.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    im.setImageResource(R.drawable.dark_dot);

                    if (!SettingsActivity.isLoadImage) {
                        Glide.with(ct).load(ca.slide.get(i).picture).into(im);
                    }

                    imageList.add(im);

                    //加载圆点
                    ImageView point = new ImageView(ct);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.rightMargin = 20;
                    point.setLayoutParams(params);
                    point.setEnabled(false);
                    if (i == 0) {
                        point.setEnabled(true);
                    }
                    point.setBackgroundResource(R.drawable.point_bg);
                    holder.point_group.addView(point);
                }

                holder.news_viewPager.setAdapter(new MyAdvPagerAdapter(imageList));
                holder.news_viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % 4));
                holder.news_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        position = position % 4;
                        holder.news_viewpager_text.setText(ca.slide.get(position).title);
                        holder.point_group.getChildAt(position).setEnabled(true);
                        holder.point_group.getChildAt(lastPointPostion).setEnabled(false);
                        lastPointPostion = position;
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

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

        public ImageView item_news_image;
        public TextView item_news_title;
        public TextView item_news_from;
        public TextView item_news_time;

        public ImageView item2_news_image1;
        public ImageView item2_news_image2;
        public ImageView item2_news_image3;

        public ViewPager news_viewPager;
        public TextView news_viewpager_text;
        public LinearLayout point_group;

        public ViewHolder(View itemView) {
            super(itemView);
            switch (type) {
                case TYPE_1:
                    item_news_image = (ImageView) itemView.findViewById(R.id.item_news_image);
                    item_news_title = (TextView) itemView.findViewById(R.id.item_news_title);
                    item_news_from = (TextView) itemView.findViewById(R.id.item_news_from);
                    item_news_time = (TextView) itemView.findViewById(R.id.item_news_time);
                    break;
                case TYPE_2:
                    item2_news_image1 = (ImageView) itemView.findViewById(R.id.item2_news_image1);
                    item2_news_image2 = (ImageView) itemView.findViewById(R.id.item2_news_image2);
                    item2_news_image3 = (ImageView) itemView.findViewById(R.id.item2_news_image3);
                    item_news_title = (TextView) itemView.findViewById(R.id.item2_news_title);
                    item_news_from = (TextView) itemView.findViewById(R.id.item2_news_from);
                    item_news_time = (TextView) itemView.findViewById(R.id.item2_news_time);
                    break;
                case TYPE_HEADER:
                    news_viewPager = (ViewPager) itemView.findViewById(R.id.news_viewpager);
                    news_viewpager_text = (TextView) itemView.findViewById(R.id.news_viewpager_text);
                    point_group = (LinearLayout) itemView.findViewById(R.id.point_group);
                    break;
            }
        }
    }

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /**
     * 获取新闻布局类型
     * @param position 索引
     * @return 布局类型
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            type = TYPE_HEADER;
        } else {
            type = ca.list.get(position).pictures != null ? TYPE_2 : TYPE_1;
        }

        return type;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

//    public void addDatas(ArrayList<String> datas) {
//        mDatas.addAll(datas);
//        notifyDataSetChanged();
//    }

}
