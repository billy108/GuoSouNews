package com.example.administrator.guosounews.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.adapter.SearchAutoAdapter;
import com.example.administrator.guosounews.adapter.SearchHistroyAdapter;
import com.example.administrator.guosounews.adapter.SearchResultAdapter;
import com.example.administrator.guosounews.bean.NewsAUTO;
import com.example.administrator.guosounews.bean.NewsSearch;
import com.example.administrator.guosounews.fragment.CopyOfHotFragment;
import com.example.administrator.guosounews.utils.APIs;
import com.example.administrator.guosounews.utils.RecycleViewDivider;
import com.example.administrator.guosounews.utils.SPUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SearchActivity extends Activity implements View.OnClickListener{
    public static final String SEARCHACTIVITY = "searchActivity";

    public static final String AUTO = "auto";
    public static final String RESULT = "result";
    public static final String NEWS = "news";

    @InjectView(R.id.iv_search_back)
    ImageView ivSearchBack;
    @InjectView(R.id.et_search)
    EditText etSearch;
    @InjectView(R.id.rv_search_histroy)
    RecyclerView rvSearchHistroy;
    @InjectView(R.id.tv_search_clean)
    TextView tvSearchClean;
    @InjectView(R.id.rv_search_result)
    RecyclerView rvSearchResult;
    @InjectView(R.id.rv_search_auto)
    RecyclerView rvSearchAuto;
    @InjectView(R.id.iv_search_clean)
    ImageView clean_iv;

    private ArrayList<String> histroyList = new ArrayList<String>();
    private ArrayList<String> autoList = new ArrayList<String>();
    private NewsSearch newsSearches;
    private NewsAUTO newsAUTO;

    private SearchHistroyAdapter histroyAdapter;
    private SearchResultAdapter resultAdapter;
    private SearchAutoAdapter autoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);

        initData();
        initEditText();
        initHistroy();

        //清除历史记录
        tvSearchClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                histroyList.clear();
                rvSearchHistroy.setAdapter(histroyAdapter);
            }
        });

        clean_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setText("");
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        SPUtils.loadArray(this,histroyList);
        rvSearchHistroy.setVisibility(View.VISIBLE);
        histroyAdapter = new SearchHistroyAdapter(histroyList, this);
        rvSearchHistroy.setAdapter(histroyAdapter);
    }

    private void initEditText() {
        etSearch.addTextChangedListener(new EditTextChangedListener());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search_back:
                finish();
                break;
        }
    }

    class EditTextChangedListener implements TextWatcher {
        StringBuffer buf = new StringBuffer();
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (buf.length() > 0) {
                buf.delete(0, buf.length());
            }
            buf.append(APIs.AUTO_BASE);
            buf.append(s.toString());
            buf.append(APIs.AUTO_END);

            getJson(buf.toString(), AUTO);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    /**
     * 获取json
     */
    private void getJson(String url, final String type) {
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET,
                url,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Logger.d(responseInfo.result);
                        switch (type) {
                            case AUTO:
                                StringBuffer buf = new StringBuffer(responseInfo.result);
                                String str = "window.baidu.sug(";
                                buf.delete(0, str.length());
                                buf.delete(buf.length() - 2 , buf.length());
                                newsAUTO = new Gson().fromJson(buf.toString(), NewsAUTO.class);

                                initAuto(newsAUTO);
                                break;
                            case RESULT:
                                newsSearches = new Gson().fromJson(responseInfo.result, NewsSearch.class);
                                initResult(newsSearches);
                                break;
                            case NEWS:
                                Intent i = new Intent(SearchActivity.this, NewsActivity.class);
                                i.putExtra(CopyOfHotFragment.HOTFRAGMENT, responseInfo.result);
                                startActivity(i);
                                break;
                        }

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                    }
                });
    }

    /**
     * 初始化自动填充数据框
     * @param newsAUTO
     */
    private void initAuto(NewsAUTO newsAUTO) {
        autoList.clear();

        if (newsAUTO.q.length() != 0) {
            rvSearchHistroy.setVisibility(View.GONE);
            rvSearchResult.setVisibility(View.GONE);
            tvSearchClean.setVisibility(View.GONE);
            rvSearchAuto.setVisibility(View.VISIBLE);

            autoList.add(newsAUTO.q);
            for (int i = 0; i < newsAUTO.s.size(); i++) {
                autoList.add(newsAUTO.s.get(i));
            }
        } else {
            rvSearchHistroy.setVisibility(View.VISIBLE);
            tvSearchClean.setVisibility(View.VISIBLE);
            rvSearchResult.setVisibility(View.GONE);
        }

            rvSearchAuto.setLayoutManager(new LinearLayoutManager(this));
            rvSearchAuto.addItemDecoration(new RecycleViewDivider(
                    this, LinearLayoutManager.VERTICAL));

        autoAdapter = new SearchAutoAdapter(autoList, this);
        rvSearchAuto.setAdapter(autoAdapter);
        autoAdapter.setOnItemClickListener(new SearchAutoAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                etSearch.setText(autoList.get(position));
                rvSearchAuto.setVisibility(View.GONE);
                String url = APIs.SEARCH_BASE + autoList.get(position);
                getJson(url, RESULT);
                rvSearchResult.setVisibility(View.VISIBLE);

                histroyList.add(autoList.get(position));
                etSearch.setSelection(autoList.get(position).length());

            }
        });
    }

    /**
     * 搜索结果
     * @param news
     */
    private void initResult(final NewsSearch news) {
        rvSearchResult.setVisibility(View.VISIBLE);
        rvSearchResult.setLayoutManager(new LinearLayoutManager(this));
        rvSearchResult.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL));

        resultAdapter = new SearchResultAdapter(news, this);
        rvSearchResult.setAdapter(resultAdapter);
        resultAdapter.setOnItemClickListener(new SearchResultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String nid = news.newsList.get(position).nid;
                String resultURL = APIs.RESULT_BASE + nid + APIs.RESULT_END;

                getJson(resultURL, NEWS);
            }
        });
    }

    /**
     * 初始化历史记录控件
     */
    private void initHistroy() {
        rvSearchHistroy.setLayoutManager(new LinearLayoutManager(this));

        rvSearchHistroy.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL));

        histroyAdapter = new SearchHistroyAdapter(histroyList, this);
        rvSearchHistroy.setAdapter(histroyAdapter);

        histroyAdapter.setOnItemClickListener(new SearchHistroyAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                tvSearchClean.setVisibility(View.GONE);
                rvSearchHistroy.setVisibility(View.GONE);
                rvSearchResult.setAdapter(resultAdapter);

                String url = APIs.SEARCH_BASE + histroyList.get(position);
                etSearch.setText(histroyList.get(position));
                getJson(url, RESULT);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SPUtils.saveArray(this, histroyList);
    }
}
