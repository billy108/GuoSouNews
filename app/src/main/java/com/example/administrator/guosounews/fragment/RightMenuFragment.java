package com.example.administrator.guosounews.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.guosounews.R;
import com.example.administrator.guosounews.base.BaseFragment;
import com.example.administrator.guosounews.activity.CollectActivity;
import com.example.administrator.guosounews.activity.MainActivity;

public class RightMenuFragment extends BaseFragment implements View.OnClickListener{
    private TextView download_for_offline;

    private TextView righit_menu_collect;

    private TextView right_menu_search;

    private TextView right_menu_settings;

    @Override
    public View initView(LayoutInflater inflater) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_right_menu, null);
        initTextView();
        initListener();

        return view;
    }

    private void initTextView() {
        download_for_offline = (TextView) view.findViewById(R.id.download_for_offline);
        righit_menu_collect = (TextView) view.findViewById(R.id.righit_menu_collect);
        right_menu_search = (TextView) view.findViewById(R.id.right_menu_search);
        right_menu_settings = (TextView) view.findViewById(R.id.right_menu_settings);
    }

    private void initListener() {
        download_for_offline.setOnClickListener(this);
        righit_menu_collect.setOnClickListener(this);
        right_menu_search.setOnClickListener(this);
        right_menu_settings.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.download_for_offline:
                downloadOfflineDilog();
                break;
            case R.id.righit_menu_collect:
                Intent i = new Intent(ct, CollectActivity.class);
                startActivity(i);
                break;
            case R.id.right_menu_search:
                Toast.makeText(ct, "3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.right_menu_settings:
                Toast.makeText(ct, "4", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void downloadOfflineDilog() {
        AlertDialog dialog = new AlertDialog.Builder(ct)
                .setTitle("离线缓存数据流量较大，最好在WIFI环境下进行，您确定下载离线数据么？")
                .setNegativeButton("下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        dialog.show();
    }
}
