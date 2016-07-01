package com.example.administrator.guosounews.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.activity.NewsActivity;
import com.example.administrator.guosounews.adapter.RecyclerViewAdapter;
import com.example.administrator.guosounews.base.BaseFragment;
import com.example.administrator.guosounews.bean.NewsCenterCategory;
import com.example.administrator.guosounews.bean.NewsMore;
import com.example.administrator.guosounews.utils.APIs;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class IntentNetFragment extends BaseFragment {

	PullLoadMoreRecyclerView news_list;
	ScrollView scrollView;

	private RecyclerViewAdapter myNewsListAdapter;

	private NewsCenterCategory category;
	private NewsMore newsMore;

	private List<String> slide_url_list = new ArrayList<String>();
	private List<String> list_url_list = new ArrayList<String>();
	private ArrayList<NewsCenterCategory.Body1> listNews = new ArrayList();

	View view;

	/**
	 * 初始化控件
	 *
	 * @param inflater
	 * @return
	 */
	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.copy_of_layout_hot, null);

		news_list = (PullLoadMoreRecyclerView) view.findViewById(R.id.news_pullLoadMoreRecyclerView);
		news_list.setLinearLayout();
		scrollView = (ScrollView) view.findViewById(R.id.scroll);

		getJson(APIs.INTENTNET_NEWS);
		return view;
	}

	/**
	 * 初始化List新闻
	 *
	 */
	private void initNewsList() {

		for (int i = 0; i < category.list.size(); i++) {
			list_url_list.add(APIs.ADV_BASE + category.list.get(i).nid + APIs.ADV_END);
		}

		myNewsListAdapter = new RecyclerViewAdapter(category, ct, listNews);
		news_list.setAdapter(myNewsListAdapter);

		news_list.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
			@Override
			public void onRefresh() {
				news_list.postDelayed(new Runnable() {
					@Override
					public void run() {
						// 更新数据
						getJson(APIs.INTENTNET_NEWS);
						myNewsListAdapter.notifyDataSetChanged();
						// 更新完后调用该方法结束刷新
						news_list.setPullLoadMoreCompleted();
					}
				}, 3000);
			}

			@Override
			public void onLoadMore() {
				news_list.postDelayed(new Runnable() {
					@Override
					public void run() {
						category = null;
						HttpUtils http = new HttpUtils();
						http.send(HttpRequest.HttpMethod.GET,
								APIs.INTENTNET2,
								new RequestCallBack<String>() {

									@Override
									public void onSuccess(ResponseInfo<String> responseInfo) {
										newsMore = new Gson().fromJson(responseInfo.result, NewsMore.class);
										listNews.addAll(newsMore.list);
										news_list.setPullLoadMoreCompleted();
										myNewsListAdapter.notifyDataSetChanged();
									}

									@Override
									public void onFailure(HttpException error, String msg) {
									}
								});
					}
				}, 3000);
			}
		});
		news_list.setFooterViewText("加载更多...");


		myNewsListAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(int position) {
				intentToNews(list_url_list.get(position), APIs.LIST_NEWS, position);
			}

			@Override
			public void onADVItemClick(int position) {
				intentToNews(slide_url_list.get(position), APIs.ADV_NEWS, position);
			}
		});
	}

	/**
	 * 获取json
	 */
	private void getJson(String url) {
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.GET,
				url,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

						category = new Gson().fromJson(responseInfo.result, NewsCenterCategory.class);
						listNews.addAll(category.list);
						if (category.slide != null) {
							//添加滚动图片的url
							initSlideList();
							//初始化newsList
							initNewsList();
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
					}
				});
	}

	/**
	 * 添加滚动图片的url
	 */
	private void initSlideList() {
		for (int i = 0; i < category.slide.size(); i++) {
			slide_url_list.add(APIs.ADV_BASE + category.slide.get(i).url + APIs.ADV_END);
		}

	}

	/**
	 * 跳转到新闻页面
	 */
	private void intentToNews(String url, final int type, final int position) {
		//获取adv的json
		HttpUtils http = new HttpUtils();
		http.send(HttpRequest.HttpMethod.GET,
				url,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						LogUtils.d(responseInfo.result);
						//跳转并传递json
						Intent i = new Intent(getActivity(), NewsActivity.class);
						i.putExtra(CopyOfHotFragment.HOTFRAGMENT, responseInfo.result);
						i.putExtra(CopyOfHotFragment.NEWS_TYPE, type);
						if (category.list.get(position).pictures == null) {
							i.putExtra("url", category.list.get(position).picture);
						} else {
							i.putExtra("url", category.list.get(position).pictures.get(0));
						}
						startActivity(i);
					}

					@Override
					public void onFailure(HttpException error, String msg) {

					}
				});
	}

	/**
	 * 初始化数据
	 *
	 * @param savedInstanceState
	 */
	@Override
	public void initData(Bundle savedInstanceState) {
	}

}
