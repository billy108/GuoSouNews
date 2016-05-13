package com.example.administrator.guosounews.bean;


import java.util.List;

public class NewsCenterCategory {
    public static class NewsBody{
        public List<Body_subitems> subitems;
        public List<Body_subitems> subitems2;
    }
    public static class Body_subitems{
        public int commentSum;
        public int favSum;
        public int id;
        public String imagePath;
        public int isbig;
        public String pubDate;
        public int shareSum;
        public String title;
        public int type;
        public int viewTimes;
    }
    public static class NewsHead{
        public int resp_code;
        public String resp_msg;
    }

}
