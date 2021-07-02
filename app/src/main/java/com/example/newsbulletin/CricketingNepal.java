package com.example.newsbulletin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class CricketingNepal {

    ArrayList<NewsItem> news;
    public CricketingNepal(){
        news = new ArrayList<>();
    }

    public ArrayList<NewsItem> getNews() throws IOException {

        String url = "https://cricketingnepal.com/";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Document document = Jsoup.parse(okHttpClient.newCall(request).execute().body().string());
        Elements article = document.select("li.infinite-post");

        for(int i=0; i<article.size(); i++){
            String link = article.get(i).select("a").first().attr("href");
            String title = article.get(i).select("h2").select("a").text();
            String img = article.get(i).select("img").attr("src");
            String[] date = article.get(i).select("span.mvp-post-info-date").text().split("\\s+");
            NewsItem item = new NewsItem();
            item.link = link;
            item.title =title;
            item.imgsrc = img;
            item.date = date[1] + " " + date[2] + " " + date[3];
            item.tag = "sports, cricket";
            item.publisher = "cricketingnepal.com";
            item.source_logo = "https://cricketingnepal.com/wp-content/uploads/2020/07/cricketingnepallogo-3.png";



            news.add(item);

        }

        return news;
    }
}
