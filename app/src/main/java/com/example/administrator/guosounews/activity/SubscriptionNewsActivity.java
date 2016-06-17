package com.example.administrator.guosounews.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.example.administrator.guosounews.R;

public class SubscriptionNewsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_subscription_news);
    }
}
