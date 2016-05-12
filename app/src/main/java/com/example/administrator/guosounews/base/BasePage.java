package com.example.administrator.guosounews.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public abstract class BasePage {
    public View view;
    public Context ct;

    public BasePage(Context ct) {
        this.ct = ct;
        LayoutInflater inflater =
                (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = initView(inflater);
    }

    public View getRootView(){
        return view;
    }

    public abstract View initView(LayoutInflater inflater);
    public abstract void initData();
}
