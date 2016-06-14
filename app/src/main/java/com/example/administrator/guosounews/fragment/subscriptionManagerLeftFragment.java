package com.example.administrator.guosounews.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.guosounews.R;

public class subscriptionManagerLeftFragment extends Fragment {


    public subscriptionManagerLeftFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subscription_manager_left, container, false);
    }

}
