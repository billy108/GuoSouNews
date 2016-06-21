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

    //订阅频道URL
    public static final String READING_TIGERSMELL = "http://mobapp.chinaso.com/1/subscribe/newsList?version=version%3D2.67.7&mid=116";
    public static final String READING_NEXTCAR = "http://mobapp.chinaso.com/1/subscribe/newsList?version=version%3D2.67.7&mid=121";
    public static final String READING_SINA = "http://mobapp.chinaso.com/1/subscribe/newsList?version=version%3D2.67.7&mid=13";
    public static final String READING_SINAINSIED = "http://mobapp.chinaso.com/1/subscribe/newsList?version=version%3D2.67.7&mid=13";
    public static final String ENTERTAINMENT_MAFADA = "http://mobapp.chinaso.com/1/subscribe/newsList?version=version%3D2.67.7&mid=285";
    public static final String ENTERTAINMENT_ATS = "http://mobapp.chinaso.com/1/subscribe/newsList?version=version%3D2.67.7&mid=286";
    public static final String ENTERTAINMENT_STAR = "http://mobapp.chinaso.com/1/subscribe/newsList?version=version%3D2.67.7&mid=287";

    //搜索URL
    public static final String SEARCH_BASE = "http://mobapp.chinaso.com/1/search/mediaAndNews?version=version%3D2.67.7&keywords=";

    //搜索自动补全URL
    public static final String AUTO_BASE = "http://nssug.baidu.com/su?wd=";
    public static final String AUTO_END = "&ie=utf-8&prod=news";

    /**
     * 订阅频道条目
     * 阅读频道索引 0~2
     * 娱乐频道索引 3~5
     */
    public static final String[] reading_channel_title = {
        "虎嗅","NextCar","新浪网-新闻要闻","新浪网-国内新闻"
    };
    public static final String[] reading_channel_content = {
         "商业资讯与观点交流平台","汽车在线问答网站","新浪焦点新闻","新浪国内重要新闻"
    };
    public static final String[] entertainment_channel_title = {
            "玛法达博客", "星译社ATS博客", "摘星工厂博客"
    };
    public static final String[] entertainment_channel_content = {
            "玛法达看星座运程", "专业翻译占星资料", "全球最大的华文星座网站"
    };

}
