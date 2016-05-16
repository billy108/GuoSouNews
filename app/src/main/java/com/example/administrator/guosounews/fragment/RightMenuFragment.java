package com.example.administrator.guosounews.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.base.BaseFragment;
import com.example.administrator.guosounews.ui.MainActivity;

public class RightMenuFragment extends BaseFragment{

    @Override
    public View initView(LayoutInflater inflater) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_right_menu, null);
        return view;
    }


    @Override
    public void initData(Bundle savedInstanceState) {


    }

    private void switchFragment(Fragment fragment) {
        if (fragment != null) {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).switchFragment(fragment);
            }
        }
    }
}
