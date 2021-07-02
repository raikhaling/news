package com.example.newsbulletin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NepaliSansar {
    ArrayList<NewsItem> news;

    public NepaliSansar() {
        news = new ArrayList<>();
    }

    public ArrayList<NewsItem> getNews() throws IOException {

        String url = "https://www.nepalisansar.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request requestHttp = new Request.Builder().url(url).get().build();
        Document document = Jsoup.parse(okHttpClient.newCall(requestHttp).execute().body().string());
        Elements posts = document.select("div.latestnews").select("div.article_card");

        for(int i=0; i<posts.size(); i++){
            String imgsrc = posts.get(i).select("img").attr("data-lazy-src");
            String title = posts.get(i).select("a").attr("title");
            String link = posts.get(i).select("a").attr("href");
            String date = posts.get(i).select("div.meta_cus").text();

            if(!imgsrc.equals("")){
                final NewsItem item = new NewsItem();
                item.link = link;
                item.title =title;
                item.imgsrc = imgsrc;
                item.tag = "nepal";
                item.date = date;
                item.publisher = "nepalisansar.com";
                item.source_logo = "https://www.nepalisansar.com/wp-content/uploads/2018/03/nepali-sansar-logo.png";


                news.add(item);
            }
        }

        return news;
    }
}
