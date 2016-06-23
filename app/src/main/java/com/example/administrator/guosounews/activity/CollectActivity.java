package com.example.administrator.guosounews.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.bean.NewsList;

import java.util.ArrayList;

public class CollectActivity extends Activity {
    public static ArrayList<NewsList> collectNews;
    public static ArrayList<NewsList> histroyNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_collect);
    }
}
