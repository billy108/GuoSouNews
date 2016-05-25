package com.example.administrator.guosounews.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.base.BaseFragment;
import com.example.administrator.guosounews.ui.MainActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

public class RightMenuFragment extends BaseFragment{
//    @ViewInject(R.id.download_for_offline)
//    private TextView download_for_offline;
//
//    @ViewInject(R.id.download_for_offline)
//    private TextView download_for_offline;
//
//    @ViewInject(R.id.download_for_offline)
//    private TextView download_for_offline;
//
//    @ViewInject(R.id.download_for_offline)
//    private TextView download_for_offline;
//
//    @ViewInject(R.id.download_for_offline)
    private TextView download_for_offline;


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
