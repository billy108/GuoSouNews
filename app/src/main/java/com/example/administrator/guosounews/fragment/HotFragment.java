package com.example.administrator.guosounews.fragment;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.activity.MainActivity;
import com.example.administrator.guosounews.base.BaseFragment;
import com.example.administrator.guosounews.bean.NewsCenterCategory;
import com.example.administrator.guosounews.ui.ListViewForScrollView;
import com.example.administrator.guosounews.ui.RefreshLayout;
import com.example.administrator.guosounews.utils.APIs;
import com.example.administrator.guosounews.utils.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HotFragment extends BaseFragment{
	private static final String NEWSCENTERPAGE = "HotFragment";

	private ViewPager news_viewPager;

	private TextView news_viewpager_text;

	private ListViewForScrollView news_list;

	private LinearLayout point_group;

	protected RefreshLayout myRefreshListView;

	private ScrollView scrollView;

	private List<ImageView> imageList;
	private List<ImageView> imageNewsList;
	private int lastPointPostion;
	public boolean isRuning = false;
	private MyNewsListAdapter myNewsListAdapter;
	private MyPagerAdapter myPagerAdapter;

	private boolean isLastItem;

	@Override
	public void initData(Bundle savedInstanceState) {
		String vaule = SharedPreferencesUtils.getString(ct, NEWSCENTERPAGE);
		scrollView.smoothScrollTo(0, 0);
	}

	private List<String> menuNewCenterList = new ArrayList<>();

	public void initMenu2() {

		if (menuNewCenterList.size() == 0) {
			BaseFragment.flag = true;
			menuNewCenterList.add("新闻");
			menuNewCenterList.add("订阅");
			menuNewCenterList.add("投票");
		}

		MenuFragment2 menuFragment2 = ((MainActivity)ct).getMenuFragment2();
		menuFragment2.initMenu(menuNewCenterList);

	}

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.layout_hot, null);

		news_viewpager_text = (TextView) view.findViewById(R.id.news_viewpager_text);
		news_viewPager = (ViewPager) view.findViewById(R.id.news_viewpager);
		point_group = (LinearLayout) view.findViewById(R.id.point_group);
		news_list = (ListViewForScrollView) view.findViewById(R.id.news_list);
		myRefreshListView = (RefreshLayout)view.findViewById(R.id.swipe_layout);
		scrollView = (ScrollView) view.findViewById(R.id.scroll);

		initRefreshListView();
		initMenu2();
		getJson();
		initViewPager();
		initListView();

		return view;
	}

	@TargetApi(Build.VERSION_CODES.M)
	private void initListView() {

		scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
			@Override
			public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
				int heigh = getActivity().getWindowManager().getDefaultDisplay().getHeight();
				LogUtils.d("scrollY is " + scrollY);
				LogUtils.d("heigh is " + heigh);

				if (scrollY == 0) {
					myRefreshListView.setEnabled(true);
				} else {
					myRefreshListView.setEnabled(false);
				}

				if (scrollY == 5745) {
					isLastItem = true;
					myRefreshListView.postDelayed(new Runnable() {

						@Override
						public void run() {
							// 加载完后调用该方法
							LogUtils.d("load done~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						}
					}, 1500);
				} else {
					isLastItem = false;
				}
			}
		});
	}

	private void initRefreshListView() {
		// 设置下拉刷新时的颜色值,颜色值需要定义在xml中
		myRefreshListView.setColorSchemeResources(android.R.color.holo_blue_light,
				android.R.color.holo_red_light, android.R.color.holo_orange_light,
				android.R.color.holo_green_light);
		// 设置下拉刷新监听器
		myRefreshListView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

			@Override
			public void onRefresh() {

				Toast.makeText(ct, "refresh", Toast.LENGTH_SHORT).show();

				myRefreshListView.postDelayed(new Runnable() {

					@Override
					public void run() {
						// 更新数据
//						getJson();
//						initViewPager();
						myNewsListAdapter.notifyDataSetChanged();
						// 更新完后调用该方法结束刷新
						myRefreshListView.setRefreshing(false);
					}
				}, 1000);
			}
		});

		// 加载监听器
		myRefreshListView.setOnLoadListener(new RefreshLayout.OnLoadListener() {

			@Override
			public void onLoad() {

				Toast.makeText(ct, "load", Toast.LENGTH_SHORT).show();
				myRefreshListView.postDelayed(new Runnable() {

					@Override
					public void run() {
						// 加载完后调用该方法
						myRefreshListView.setLoading(false);
					}
				}, 1500);

			}
		});
	}

	private void initList(NewsCenterCategory category) {
		imageNewsList = new ArrayList<ImageView>();

		for (int i = 0; i < 20; i++) {
			ImageView im = new ImageView(ct);
			im.setImageResource(R.drawable.dark_dot);
			imageNewsList.add(im);
			myNewsListAdapter = new MyNewsListAdapter(category);
			news_list.setAdapter(myNewsListAdapter);

		}
	}

	private void getJson() {
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.GET,
				APIs.HOT_NEWS,
				new RequestCallBack<String>(){

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						LogUtils.d(responseInfo.result);

						category = new Gson().fromJson(responseInfo.result, NewsCenterCategory.class);
						news_viewpager_text.setText(category.slide.get(0).title);
						showImage(category.slide.size(), imageList);
						initList(category);
						SharedPreferencesUtils.saveString(ct, NEWSCENTERPAGE, responseInfo.result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						LogUtils.d("1111111111111111111111111111");
					}
				});
	}

	private NewsCenterCategory category;

	private void showImage(int size, List<ImageView> image) {
		for (int i = 0; i < size; i++) {
			Picasso.with(ct).load(category.slide.get(i).picture)
					.config(Bitmap.Config.RGB_565).error(R.drawable.dot)
					.into(image.get(i));
		}
	}


	private void initViewPager() {
		//加载图片
		imageList = new ArrayList<ImageView>();

		for (int i = 0; i < 4; i++) {
			ImageView im = new ImageView(ct);
			im.setScaleType(ImageView.ScaleType.CENTER_CROP);
			im.setImageResource(R.drawable.dark_dot);
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
			point_group.addView(point);
		}

		myPagerAdapter = new MyPagerAdapter();
		news_viewPager.setAdapter(myPagerAdapter);
		news_viewPager.setCurrentItem(Integer.MAX_VALUE/2 - (Integer.MAX_VALUE/2%imageList.size()));
		news_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				position = position%imageList.size();
				news_viewpager_text.setText(category.slide.get(position).title);
				point_group.getChildAt(position).setEnabled(true);
				point_group.getChildAt(lastPointPostion).setEnabled(false);
				lastPointPostion = position;

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		isRuning = true;
//		handler.sendEmptyMessageDelayed(0, 2000);

	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			news_viewPager.setCurrentItem(news_viewPager.getCurrentItem() + 1);
			if (isRuning) handler.sendEmptyMessageDelayed(0, 2000);
		}
	};

	private class MyPagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object ;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(imageList.get(position%imageList.size()));
			return imageList.get(position%imageList.size());
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(imageList.get(position%imageList.size()));
			object = null;
		}

	}

	private class MyNewsListAdapter extends BaseAdapter{
		NewsCenterCategory ca;
		final int TYPE_1 = 0;
		final int TYPE_2 = 1;

		public MyNewsListAdapter(NewsCenterCategory category) {
			this.ca = category;
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

}
