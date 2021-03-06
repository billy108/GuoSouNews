package com.example.administrator.guosounews.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.example.administrator.guosounews.R;
import com.lidroid.xutils.util.LogUtils;

public class AppStart extends Activity {
    boolean isFirstIn = false;
    private static final String SHAREDPREFERENCES_NAME = "first_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.activity_app_splash, null);
        setContentView(view);

        SharedPreferences preferences = getSharedPreferences(SHAREDPREFERENCES_NAME, MODE_PRIVATE);
        isFirstIn = preferences.getBoolean("isFirstIn", true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 渐变展示启动屏
                AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
                aa.setDuration(2000);
                view.startAnimation(aa);
                aa.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        if (!isFirstIn) {
                            Intent homeIntent = new Intent(AppStart.this, MainActivity.class);
                            startActivity(homeIntent);
                            finish();
                            LogUtils.d("MainActivity.class");
                        } else {
                            Intent guideIntent = new Intent(AppStart.this, WelcomeActivity.class);
                            startActivity(guideIntent);
                            finish();
                            LogUtils.d("GuideActivity.class");
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                });
            }
        }).start();

    }
}
