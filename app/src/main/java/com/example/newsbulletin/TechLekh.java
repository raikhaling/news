package com.example.newsbulletin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class TechLekh {


    ArrayList<NewsItem> news;

    public TechLekh() {
        news = new ArrayList<>();
    }

    public ArrayList<NewsItem> getNews() throws IOException {

        ArrayList<NewsItem> news = new ArrayList<>();
        String url = "https://techlekh.com/category/news/";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request requestHttp = new Request.Builder().url(url).get().build();
        Document document = Jsoup.parse(okHttpClient.newCall(requestHttp).execute().body().string());
        Elements posts = document.select("article");

        for(int i=0; i<posts.size(); i++){
            String imgsrc = posts.get(i).select("img").attr("src");
            String title = posts.get(i).select("h2").text();
            String link = posts.get(i).select("a").first().attr("href");
            String date = posts.get(i).select("a.entry-date").text();

            if(!imgsrc.equals("")){
                NewsItem item = new NewsItem();
                item.link = link;
                item.title =title;
                item.imgsrc = imgsrc;
                item.date = date;
                item.publisher = "techlekh.com";
                item.tag = "tech";
                item.source_logo = "https://techlekh.com/wp-content/uploads/2017/05/TechLekh-Logo.png";


                news.add(item);
            }
        }

        return news;
    }

}
