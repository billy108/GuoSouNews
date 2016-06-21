package com.example.administrator.guosounews.bean;

import java.util.List;

public class NewsSearch {

    /**
     * description : 女子<em>不满与公婆住悔婚</em>，说不结就不结拿婚姻当儿戏。太任性!究竟婚后应不应该和父母<em>住</em>，这个问题让很多人...
     * mname : 互联网
     * nid : C3D9C5E74E341B79DCE4931F063A4035166BD710EEACDFE7883F5B1A9D3C45F2A4F8494A1BFA67F74BB069FF93F945EC
     * time : 1465023205494
     * title : 女子<em>不满与公婆住悔婚</em> 归还饰品赔偿3万
     * type : 0
     * url : C3D9C5E74E341B79DCE4931F063A4035166BD710EEACDFE7883F5B1A9D3C45F2A4F8494A1BFA67F74BB069FF93F945EC
     */

    public List<NewsListBean> newsList;

    public static class NewsListBean {
        public String description;
        public String mname;
        public String nid;
        public long time;
        public String title;
        public int type;
        public String url;
    }
}
