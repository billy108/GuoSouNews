package com.example.administrator.guosounews.bean;

import java.util.List;

public class NewsCenterCategory {
    public String cid;
    public String cname;
    public List<Body1> list;
    public double rtime;
    public List<Body2> slide;
    public String statisticURL;

    public static class Body1{
        public String description;
        public String hot;
        public boolean isComment;
        public String label;
        public String label_color;
        public String mname;
        public String nid;
        public String picture;
        public double time;
        public String title;
        public int type;
        public String url;
        public List<String> pictures;
    }

    public static class Body2{
        public String description;
        public String hot;
        public boolean isComment;
        public String mname;
        public String nid;
        public String picture;
        public double time;
        public String title;
        public int type;
        public String url;
    }

}
