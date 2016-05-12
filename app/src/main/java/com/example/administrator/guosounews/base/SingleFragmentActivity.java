package com.example.administrator.guosounews.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.example.administrator.guosounews.R;

public abstract class SingleFragmentActivity extends FragmentActivity{
	
	public abstract Fragment creatFragment();
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_fragment);
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		
		if (fragment == null) {
			fragment = creatFragment();
			fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
		}
		
	}
}
