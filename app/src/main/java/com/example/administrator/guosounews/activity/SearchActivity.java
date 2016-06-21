package com.example.administrator.guosounews.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.adapter.SearchHistroyAdapter;
import com.example.administrator.guosounews.adapter.SearchResultAdapter;
import com.example.administrator.guosounews.bean.NewsSearch;
import com.example.administrator.guosounews.utils.RecycleViewDivider;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SearchActivity extends Activity {

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

    private ArrayList<String> histroyList;
    private NewsSearch newsSearches;

    private SearchHistroyAdapter histroyAdapter;
    private SearchResultAdapter resultAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);

        textDate();//测试数据
        getJson();
        initHistroy();
//        initResult(newsSearches);
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
    private void getJson() {
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET,
                "http://mobapp.chinaso.com/1/search/mediaAndNews?version=version%3D2.67.7&keywords=%E4%B8%8A",
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.d(responseInfo.result);

                        newsSearches = new Gson().fromJson(responseInfo.result, NewsSearch.class);
                        initResult(newsSearches);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        LogUtils.d("1111111111111111111111111111");
                    }
                });
    }

    private void initResult(NewsSearch news) {
//        View view = getWindow().peekDecorView();
//        if (view != null) {
//            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
        tvSearchClean.setVisibility(View.GONE);
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
