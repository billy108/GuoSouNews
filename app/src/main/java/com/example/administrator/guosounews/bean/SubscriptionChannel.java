package com.example.administrator.guosounews.bean;

public class SubscriptionChannel {
    public String title;
    public String content;
    public boolean isChecked;
    public String url;

    public SubscriptionChannel(String title, String content, boolean isChecked) {
        this.title = title;
        this.content = content;
        this.isChecked = isChecked;
    }


}
