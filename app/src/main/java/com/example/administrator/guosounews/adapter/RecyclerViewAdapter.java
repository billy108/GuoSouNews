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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public OnItemClickListener mListener;
    private NewsCenterCategory ca;
    private Context ct;
    private int type;
    private View convertView1;
    private View convertView2;
    private View mHeaderView;
    private int lastPointPostion;
    private LayoutInflater mLayoutInflater;
    private ArrayList<NewsCenterCategory.Body1> listNews = new ArrayList();

    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    final int TYPE_HEADER = 3;

    public RecyclerViewAdapter(NewsCenterCategory category, Context ct, ArrayList<NewsCenterCategory.Body1> listNews) {
        this.listNews = listNews;
        this.ca = category;
        this.ct = ct;
        mLayoutInflater = LayoutInflater.from(ct);
    }


    /**
     * 获取新闻布局类型
     *
     * @param position 索引
     * @return 布局类型
     */
    @Override
    public int getItemViewType(int position) {
        if (position < 1) { //头部View
            return TYPE_HEADER;
        } else {
            position = position - 1;
            return listNews.get(position).pictures != null ? TYPE_2 : TYPE_1;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            mHeaderView = mLayoutInflater.inflate(R.layout.layout_recycler_header, parent, false);
            return new HeaderViewHolder(mHeaderView);
        }
        if (viewType == TYPE_1) {
            convertView1 = mLayoutInflater.inflate(R.layout.layout_item_news_hot, parent, false);
            return new ContentViewHolder1(convertView1);
        }
        if (viewType == TYPE_2) {
            convertView2 = mLayoutInflater.inflate(R.layout.layout_item_news_hot2, parent, false);
            return new ContentViewHolder2(convertView2);
        }
        return null;
    }

    final ArrayList<ImageView> imageList = new ArrayList<>();;
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        position = position - 1;
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).news_viewpager_text.setText(ca.slide.get(0).title);
            if(imageList.size() == 0){
                //加载图片
                for (int i = 0; i < 4; i++) {
                    ImageView im = new ImageView(ct);
                    im.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    im.setImageResource(R.drawable.dark_dot);

                    if (!SettingsActivity.isLoadImage) {
                        Glide.with(ct).load(ca.slide.get(i).picture).into(im);
                    }

                    final int finalI = i;
                    im.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.onADVItemClick(finalI);
                        }
                    });

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
                    ((HeaderViewHolder) holder).point_group.addView(point);
                }

                ((HeaderViewHolder) holder).news_viewPager.setAdapter(new MyAdvPagerAdapter(imageList));
                ((HeaderViewHolder) holder).news_viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % 4));
                ((HeaderViewHolder) holder).news_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        position = position % 4;
                        ((HeaderViewHolder) holder).news_viewpager_text.setText(ca.slide.get(position).title);
                        ((HeaderViewHolder) holder).point_group.getChildAt(position).setEnabled(true);
                        ((HeaderViewHolder) holder).point_group.getChildAt(lastPointPostion).setEnabled(false);
                        lastPointPostion = position;
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }
        } else if (holder instanceof ContentViewHolder1) {
            if (!SettingsActivity.isLoadImage) {
                Glide.with(ct).load(listNews.get(position).picture).into(((ContentViewHolder1) holder).item_news_image);
            }

            ((ContentViewHolder1) holder).item_news_title.setText(listNews.get(position).title);
            ((ContentViewHolder1) holder).item_news_title.getPaint().setFakeBoldText(true);
            ((ContentViewHolder1) holder).item_news_from.setText(listNews.get(position).mname);
            double time = listNews.get(position).time;
            String fromTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date((int) time));
            ((ContentViewHolder1) holder).item_news_time.setText(fromTime);
        } else if (holder instanceof ContentViewHolder2) {
            ImageView[] images = {((ContentViewHolder2) holder).item2_news_image1,
                    ((ContentViewHolder2) holder).item2_news_image2,
                    ((ContentViewHolder2) holder).item2_news_image3};
            for (int i = 0; i < 3; i++) {
                Glide.with(ct).load(listNews.get(position).pictures.get(i))
                        .into(images[i]);
            }

            ((ContentViewHolder2) holder).item2_news_title.setText(listNews.get(position).title);
            ((ContentViewHolder2) holder).item2_news_title.getPaint().setFakeBoldText(true);
            ((ContentViewHolder2) holder).item2_news_from.setText(listNews.get(position).mname);
            double time2 = listNews.get(position).time;
            String fromTime2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date((int) time2));
            ((ContentViewHolder2) holder).item2_news_time.setText(fromTime2);
        }


        final int finalPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(finalPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNews.size() + 1;
    }

    //内容1 ViewHolder
    public class ContentViewHolder1 extends RecyclerView.ViewHolder {

        public ImageView item_news_image;
        public TextView item_news_title;
        public TextView item_news_from;
        public TextView item_news_time;

        public ContentViewHolder1(View itemView) {
            super(itemView);
            item_news_image = (ImageView) itemView.findViewById(R.id.item_news_image);
            item_news_title = (TextView) itemView.findViewById(R.id.item_news_title);
            item_news_from = (TextView) itemView.findViewById(R.id.item_news_from);
            item_news_time = (TextView) itemView.findViewById(R.id.item_news_time);
        }
    }

    //内容2 ViewHolder
    public class ContentViewHolder2 extends RecyclerView.ViewHolder {
        public ImageView item2_news_image1;
        public ImageView item2_news_image2;
        public ImageView item2_news_image3;
        public TextView item2_news_title;
        public TextView item2_news_from;
        public TextView item2_news_time;


        public ContentViewHolder2(View itemView) {
            super(itemView);
            item2_news_image1 = (ImageView) itemView.findViewById(R.id.item2_news_image1);
            item2_news_image2 = (ImageView) itemView.findViewById(R.id.item2_news_image2);
            item2_news_image3 = (ImageView) itemView.findViewById(R.id.item2_news_image3);
            item2_news_title = (TextView) itemView.findViewById(R.id.item2_news_title);
            item2_news_from = (TextView) itemView.findViewById(R.id.item2_news_from);
            item2_news_time = (TextView) itemView.findViewById(R.id.item2_news_time);
        }
    }

    //头部 ViewHolder
    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public ViewPager news_viewPager;
        public TextView news_viewpager_text;
        public LinearLayout point_group;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            news_viewPager = (ViewPager) itemView.findViewById(R.id.news_viewpager);
            news_viewpager_text = (TextView) itemView.findViewById(R.id.news_viewpager_text);
            point_group = (LinearLayout) itemView.findViewById(R.id.point_group);
        }
    }

    //define interface
    public interface OnItemClickListener {
        void onItemClick(int position);

        void onADVItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemRangeChanged(21, 1);
    }


    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }
//    public void addDatas(ArrayList<String> datas) {
//        mDatas.addAll(datas);
//        notifyDataSetChanged();
//    }

}
