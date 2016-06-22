package com.example.administrator.guosounews.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.example.administrator.guosounews.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SettingsActivity extends Activity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.rl_settings_shared, R.id.rl_settings_clean, R.id.rl_settings_help, R.id.rl_settings_advice, R.id.rl_settings_about})
    public void onClick(View view) {
        Intent i ;
        switch (view.getId()) {
            case R.id.rl_settings_shared:
                i = new Intent(SettingsActivity.this, SharedActivity.class);
                startActivity(i);
                break;
            case R.id.rl_settings_clean:
                break;
            case R.id.rl_settings_help:
                break;
            case R.id.rl_settings_advice:
                break;
            case R.id.rl_settings_about:
                break;
        }
    }
}
