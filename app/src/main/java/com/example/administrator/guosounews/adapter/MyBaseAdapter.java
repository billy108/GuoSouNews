package com.example.administrator.guosounews.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class MyBaseAdapter<T, Q> extends BaseAdapter{
    public List<T> list;
    private Context context;
    private Q view;

    public MyBaseAdapter(List<T> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public MyBaseAdapter(List<T> list, Context context, Q view) {

        this.list = list;
        this.context = context;
        this.view = view;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
