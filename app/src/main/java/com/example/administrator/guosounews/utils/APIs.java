package com.example.administrator.guosounews.utils;

public class APIs {
    //Tab接口
    public static final String HOT_NEWS = "http://mobapp.chinaso.com/1/category/main?version=version%3D2.67.5&cid=1001&page=1&location=xxxxxx";
    public static final String POLITICS_NEWS = "http://mobapp.chinaso.com/1/category/main?version=version%3D2.67.5&cid=1035&page=1&location=xxxxxx";
    public static final String FINANCE_NEWS = "http://mobapp.chinaso.com/1/category/main?version=version%3D2.67.5&cid=1010&page=1&location=xxxxxx";
    public static final String INTENTNET_NEWS = "http://mobapp.chinaso.com/1/category/main?version=version%3D2.67.5&cid=1015&page=1&location=xxxxxx";
    public static final String LAW_NEWS = "http://mobapp.chinaso.com/1/category/main?version=version%3D2.67.5&cid=1007&page=1&location=xxxxxx";

    //adv接口
    public static final String ADV_BASE = "http://mobapp.chinaso.com/1/category/newsDetailHtml?version=version%3D2.67.7&cid=1001&tid=&devid=00000000-56bd-2770-ffff-ffff89d87fe7&nid=";
    public static final String ADV_END = "&extra=norecmd";

    //专题接口
    public static final String SPECIAL_NEWS = "http://mobapp.chinaso.com/1/category/topic?version=version%3D2.67.7&tid=http%3A%2F%2Fmobapp.chinaso.com%2F3272750%2Fxjpzsn.json&page=1";

    //新闻类型
    public static final int ADV_NEWS = 1;
    public static final int LIST_NEWS = 2;
    public static final int SPE_LIST_NEWS = 3;
}
