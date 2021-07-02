package com.example.newsbulletin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class GsmArena {
    ArrayList<NewsItem> news;

    public GsmArena() {
        news = new ArrayList<>();
    }

    public ArrayList<NewsItem> getNews() throws IOException{

        String url = "https://www.gsmarena.com/";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Document document = Jsoup.parse(okHttpClient.newCall(request).execute().body().string());
        Elements articles = document.select("div.news-item");

        for(Element article : articles)
        {
            String link = "https://www.gsmarena.com/"+article.select("a").attr("href");
            String title = article.select("h3").text();
            String img = article.select("img").attr("src");
            String date = article.select("span.meta-item-time").text();

            NewsItem newsItem = new NewsItem();
            newsItem.imgsrc = img;
            newsItem.title = title;
            newsItem.link = link;
            newsItem.date = date;
            newsItem.tag = "tech";
            newsItem.publisher = "gsmarena.com";
            newsItem.source_logo = "https://fdn.gsmarena.com/vv/assets12/css/m/i/logo-fallback.gif";

            news.add(newsItem);
        }

        return news;
    }
}
