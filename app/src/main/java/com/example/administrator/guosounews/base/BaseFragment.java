package com.example.administrator.guosounews.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.guosounews.activity.MainActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public abstract class BaseFragment extends Fragment {
    public View view;
    public Context ct;
    public SlidingMenu sm;
    public static boolean flag = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ct = getActivity();
        sm = ((MainActivity)getActivity()).getSlidingMenu();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = initView(inflater);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    public View getRootView(){
        return view;
    }

    public abstract void initData(Bundle savedInstanceState);

    public abstract View initView(LayoutInflater inflater);
}
