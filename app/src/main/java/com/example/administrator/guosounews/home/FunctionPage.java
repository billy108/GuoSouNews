package com.example.administrator.guosounews.home;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.base.BaseFragment;
import com.example.administrator.guosounews.base.BasePage;
import com.example.administrator.guosounews.bean.NewsCenterCategory;
import com.example.administrator.guosounews.fragment.MenuFragment2;
import com.example.administrator.guosounews.ui.MainActivity;
import com.example.administrator.guosounews.utils.GsonUtils;
import com.example.administrator.guosounews.utils.SharedPreferencesUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import java.util.ArrayList;
import java.util.List;


public class FunctionPage extends BasePage {
	private static final String NEWSCENTERPAGE = "NewsCenterPage";

	private ViewPager news_viewPager;

	private TextView news_viewpager_text;

	private LinearLayout point_group;
	private List<ImageView> imageList;
	private int lastPointPostion;
	public boolean isRuning = false;
	
	public FunctionPage(Context ct) {
		super(ct);
	}

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.layout_hot, null);


		news_viewpager_text = (TextView) view.findViewById(R.id.news_viewpager_text);
		news_viewPager = (ViewPager) view.findViewById(R.id.news_viewpager);
		point_group = (LinearLayout) view.findViewById(R.id.point_group);

		initViewPager();

		return view;
	}

	@Override
	public void initData() {
		String vaule = SharedPreferencesUtils.getString(ct, NEWSCENTERPAGE);
		if (TextUtils.isEmpty(vaule)) {
			processData(vaule);
		}
		TestPost();
	}



	private void initViewPager() {
		 int[] imageIds = {R.drawable.comments_avatars, R.drawable.dark_dot,
				R.drawable.left_menu_vote_selected, R.drawable.dot,
				R.drawable.icon_fav};

		final String[] imageDes = {"第1111张", "第二张", "第三张", "第四张", "第五张"};

		news_viewpager_text.setText(imageDes[0]);

		imageList = new ArrayList<ImageView>();
		for (int i = 0; i < imageIds.length; i++) {
			ImageView im = new ImageView(ct);
			im.setBackgroundResource(imageIds[i]);
			imageList.add(im);

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

		news_viewPager.setAdapter(new MyPagerAdapter());
		news_viewPager.setCurrentItem(Integer.MAX_VALUE/2 - (Integer.MAX_VALUE/2%imageList.size()));
		news_viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				position = position%imageList.size();
				news_viewpager_text.setText(imageDes[position]);
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

	private void TestPost() {
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.GET,
				"http://mobapp.chinaso.com/1/category/main?version=version%3D2.67.5&cid=1001&page=1&location=xxxxxx",
				new RequestCallBack<String>(){

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
//						textView.setText(responseInfo.result);
						LogUtils.d(responseInfo.result);
						SharedPreferencesUtils.saveString(ct, NEWSCENTERPAGE, responseInfo.result);
						processData(responseInfo.result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
					}
				});
	}


	private List<String> menuNewCenterList = new ArrayList<>();
	private void processData(String result) {
		NewsCenterCategory category = GsonUtils.jsonToBean(result, NewsCenterCategory.class);
		if (menuNewCenterList.size() == 0 && result != null) {
			BaseFragment.flag = true;
			menuNewCenterList.add("新闻");
			menuNewCenterList.add("订阅");
			menuNewCenterList.add("投票");
		}

		MenuFragment2 menuFragment2 = ((MainActivity)ct).getMenuFragment2();
		menuFragment2.initMenu(menuNewCenterList);
	}

}
