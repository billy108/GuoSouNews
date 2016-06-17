package com.example.administrator.guosounews.bean;

import java.io.Serializable;

public class SubscriptionChannel implements Serializable{
    public String title;
    public String content;
    public boolean isChecked;
    public String url;

    public SubscriptionChannel(String title, String content, boolean isChecked, String url) {
        this.title = title;
        this.content = content;
        this.isChecked = isChecked;
        this.url = url;
    }


}
