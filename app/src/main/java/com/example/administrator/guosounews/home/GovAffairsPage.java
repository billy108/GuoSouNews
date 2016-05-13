package com.example.administrator.guosounews.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.guosounews.base.BasePage;


public class GovAffairsPage extends BasePage {

	public GovAffairsPage(Context ct) {
		super(ct);
	}

	@Override
	public View initView(LayoutInflater inflater) {
		TextView textView = new TextView(ct);
		textView.setText("我是指南");
		return textView;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}

	
}
