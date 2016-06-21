package com.example.administrator.guosounews.activity;

import android.app.Activity;
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
import android.widget.Toast;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.adapter.SearchAutoAdapter;
import com.example.administrator.guosounews.adapter.SearchHistroyAdapter;
import com.example.administrator.guosounews.adapter.SearchResultAdapter;
import com.example.administrator.guosounews.bean.NewsAUTO;
import com.example.administrator.guosounews.bean.NewsSearch;
import com.example.administrator.guosounews.utils.APIs;
import com.example.administrator.guosounews.utils.RecycleViewDivider;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.orhanobut.logger.Logger;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SearchActivity extends Activity {

    public static final String AUTO = "auto";
    public static final String RESULT = "result";

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

    private ArrayList<String> histroyList = new ArrayList<String>();
    private ArrayList<String> autoList = new ArrayList<String>();
    private NewsSearch newsSearches;
    private NewsAUTO newsAUTO;

    private SearchHistroyAdapter histroyAdapter;
    private SearchResultAdapter resultAdapter;
    private SearchAutoAdapter autoAdapter;

    private StringBuffer auto_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);

       // textDate();//测试数据

        initEditText();
        initHistroy();
    }

    private void initEditText() {
        etSearch.addTextChangedListener(new EditTextChangedListener());
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
     * 测试数据
     */
    private void textDate() {
        histroyList = new ArrayList<String>();
        histroyList.add("test1");
        histroyList.add("test2");
        histroyList.add("test3");

        newsSearches = new NewsSearch();

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
                        LogUtils.d(responseInfo.result);
                        switch (type) {
                            case AUTO:
                                StringBuffer buf = new StringBuffer(responseInfo.result);
                                String str = "window.baidu.sug(";
                                buf.delete(0, str.length());
                                buf.delete(buf.length() - 2 , buf.length());
                                newsAUTO = new Gson().fromJson(buf.toString(), NewsAUTO.class);
                                Logger.d(newsAUTO.q);
                                initAuto(newsAUTO);
                                break;
                            case RESULT:
                                newsSearches = new Gson().fromJson(responseInfo.result, NewsSearch.class);
                                initResult(newsSearches);
                                break;
                        }

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                    }
                });
    }

    private void initAuto(NewsAUTO newsAUTO) {
        rvSearchHistroy.setVisibility(View.GONE);
        rvSearchResult.setVisibility(View.GONE);
        tvSearchClean.setVisibility(View.GONE);

        autoList.clear();
        autoList.add(newsAUTO.q);
        for (int i = 0; i < newsAUTO.s.size(); i++) {
            autoList.add(newsAUTO.s.get(i));
        }

        rvSearchAuto.setLayoutManager(new LinearLayoutManager(this));
        rvSearchAuto.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL));

        autoAdapter = new SearchAutoAdapter(autoList, this);
        rvSearchAuto.setAdapter(autoAdapter);
        autoAdapter.setOnItemClickListener(new SearchAutoAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Logger.d(autoList.get(position));
            }
        });
    }

    private void initResult(NewsSearch news) {
        rvSearchResult.setLayoutManager(new LinearLayoutManager(this));
        rvSearchResult.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL));

        resultAdapter = new SearchResultAdapter(news, this);
        resultAdapter.setOnItemClickListener(new SearchResultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(SearchActivity.this, newsSearches.newsList.get(position).title, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(SearchActivity.this, histroyList.get(position), Toast.LENGTH_SHORT).show();
                tvSearchClean.setVisibility(View.GONE);
                rvSearchHistroy.setVisibility(View.GONE);
                rvSearchResult.setAdapter(resultAdapter);
            }
        });
    }

    /**
     * String 类型转换成UTF-8格式
     * @param str 要转换的参数
     * @return 转换后的String
     */
    public static String toUtf8(String str) {
        String result = null;
        try {
            result = new String(str.getBytes("UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
