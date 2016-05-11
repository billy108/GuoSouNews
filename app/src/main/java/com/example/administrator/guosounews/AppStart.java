package com.example.administrator.guosounews;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import java.util.HashMap;

public class AppStart extends Activity {
    boolean isFirstIn = false;
    private static final String SHAREDPREFERENCES_NAME = "first_pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.activity_app_splash, null);
        setContentView(view);

//        SharedPreferences preferences = getSharedPreferences(SHAREDPREFERENCES_NAME, MODE_PRIVATE);
//        isFirstIn = preferences.getBoolean("isFirstIn", true);
//
//        // 渐变展示启动屏
//        AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
//        aa.setDuration(2000);
//        view.startAnimation(aa);
//        aa.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationEnd(Animation arg0) {
//
////                HashMap params = new HashMap();
////                params.put("context", AppStart.this);
////                Task ts = new Task(TaskType.TS_EXAM_GETINITIALIZEDATA, params);
////                MainService.newTask(ts);
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//        });
    }
}
