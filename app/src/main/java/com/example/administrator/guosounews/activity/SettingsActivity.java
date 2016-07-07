package com.example.administrator.guosounews.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.utils.DataCleanManager;
import com.example.administrator.guosounews.utils.SPUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SettingsActivity extends Activity {
    public static boolean isLoadImage = false;
    public static boolean isSendMsg = true;

    @InjectView(R.id.rl_settings_shared)
    RelativeLayout rlSettingsShared;
    @InjectView(R.id.rl_settings_clean)
    RelativeLayout rlSettingsClean;
    @InjectView(R.id.rl_settings_help)
    RelativeLayout rlSettingsHelp;
    @InjectView(R.id.rl_settings_advice)
    RelativeLayout rlSettingsAdvice;
    @InjectView(R.id.rl_settings_about)
    RelativeLayout rlSettingsAbout;
    @InjectView(R.id.tv_settings_cache)
    TextView tvSettingsCache;
    @InjectView(R.id.tb_settings_msg)
    ToggleButton tbSettingsMsg;
    @InjectView(R.id.tb_settings_2g3g)
    ToggleButton tbSettings2g3g;
    @InjectView(R.id.iv_settings_back)
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);
        ButterKnife.inject(this);

        initData();

        calculateCache();

    }

    private void initData() {
        tbSettings2g3g.setChecked((boolean)SPUtils.get(this, "isLoadImage", false));
        tbSettingsMsg.setChecked((boolean)SPUtils.get(this, "isSendMsg", false));
    }

    private void calculateCache() {
        try {
            String cacheSize = DataCleanManager.getTotalCacheSize(this);
            tvSettingsCache.setText(cacheSize);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.rl_settings_shared, R.id.rl_settings_clean, R.id.rl_settings_help,
            R.id.rl_settings_advice, R.id.rl_settings_about,R.id.tb_settings_msg, R.id.tb_settings_2g3g,
            R.id.iv_settings_back})
    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.rl_settings_shared:
                i = new Intent(SettingsActivity.this, SharedActivity.class);
                startActivity(i);
                break;
            case R.id.rl_settings_clean:
                DataCleanManager.clearAllCache(this);
                tvSettingsCache.setText("0B");
                break;
            case R.id.rl_settings_help:
                i = new Intent(SettingsActivity.this, HelpActivity.class);
                startActivity(i);
                break;
            case R.id.rl_settings_advice:
                break;
            case R.id.rl_settings_about:
                break;
            case R.id.tb_settings_msg:
                if (tbSettingsMsg.isChecked()) {
                    isSendMsg = true;
                } else {
                    isSendMsg = false;
                }

                SPUtils.put(this, "isSendMsg", isSendMsg);
                break;
            case R.id.tb_settings_2g3g:
                if (tbSettings2g3g.isChecked()) {
                    isLoadImage = true;
                } else {
                    isLoadImage = false;
                }

                SPUtils.put(this, "isLoadImage", isLoadImage);
                break;
            case R.id.iv_settings_back:
                finish();
                break;
        }
    }

}
