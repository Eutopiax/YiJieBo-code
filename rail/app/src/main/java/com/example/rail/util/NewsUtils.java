package com.example.rail.util;

import android.content.Context;

import com.example.rail.R;
import com.example.rail.db.NewsBean;

import java.util.ArrayList;

/**
 * Created by My on 2016/11/8.
 */

public class NewsUtils {
    /**
     * @param context 上下文环境
     * @return 新闻集合
     */
    public static ArrayList<NewsBean> getAllNews(Context context) {
        ArrayList<NewsBean> arrayList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            NewsBean newsBean1 = new NewsBean();
            newsBean1.title = "您的舒适性偏好是0.45";
            newsBean1.des = "";
            newsBean1.icon = context.getResources().getDrawable(R.drawable.noman);
            newsBean1.news_url = "";
            arrayList.add(newsBean1);

            NewsBean newsBean2 = new NewsBean();
            newsBean2.title = "您的准时性偏好是0.79";
            newsBean2.des ="";
            newsBean2.icon = context.getResources().getDrawable(R.drawable.noman);
            newsBean2.news_url = "";
            arrayList.add(newsBean2);

            NewsBean newsBean3 = new NewsBean();
            newsBean3.title = "您的快捷性偏好是0.59";
            newsBean3.des ="";
            newsBean3.icon = context.getResources().getDrawable(R.drawable.noman);
            newsBean3.news_url = "";
            arrayList.add(newsBean3);


        }
        return arrayList;
    }
}