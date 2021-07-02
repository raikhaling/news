package com.example.newsbulletin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OnlineKhabar {


    ArrayList<NewsItem> news;

    public OnlineKhabar() {
        news = new ArrayList<>();
    }

    public ArrayList<NewsItem> getNews() throws IOException {

        String url = "https://english.onlinekhabar.com/last-24-hours";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request requestHttp = new Request.Builder().url(url).get().build();
        Document document = Jsoup.parse(okHttpClient.newCall(requestHttp).execute().body().string());
        Elements posts = document.select("div.listical-news-big").select("div.ok-news-post");

        for(int i=0; i<posts.size(); i++){
            String imgsrc = posts.get(i).select("img").attr("src");
            String title = posts.get(i).select("h2").text();
            String link = posts.get(i).select("h2").select("a").attr("href");
            String date = posts.get(i).select("span.ok-post-hours").text();

            if(!imgsrc.equals("")){
                NewsItem item = new NewsItem();
                item.link = link;
                item.title =title;
                item.imgsrc = imgsrc;
                item.date = date;
                item.publisher = "onlinekhabar.com";
                item.tag = "nepal";
                item.source_logo = "https://www.onlinekhabar.com/wp-content/themes/onlinekhabar-2018/img/logoMainWht.png";


                news.add(item);
            }
        }

        return news;
    }
}
