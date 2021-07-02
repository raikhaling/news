package com.example.newsbulletin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class GadgetByte {

    ArrayList<NewsItem> news;

    public GadgetByte() {
        news = new ArrayList<>();
    }

    public ArrayList<NewsItem> getNews() throws IOException {

        String url = "https://www.gadgetbytenepal.com/blog-news-list/";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Document document = Jsoup.parse(okHttpClient.newCall(request).execute().body().string());
        Elements posts = document.select("div.td_module_10");

        for(int i=0; i<posts.size(); i++){
            String imgsrc = posts.get(i).select("img").attr("data-img-url");
            String title = posts.get(i).select("a").first().attr("title");
            String link = posts.get(i).select("a").first().attr("href");
            String date = posts.get(i).select("time").text();

            if(!imgsrc.equals("")){
                NewsItem item = new NewsItem();
                item.link = link;
                item.title =title;
                item.imgsrc = imgsrc;
                item.date = date;
                item.source_logo = "https://www.gadgetbytenepal.com/wp-content/uploads/2017/06/gadgetbyte-nepal-300x60.png";
                item.tag = "tech";
                item.publisher = "gadgetbytenepal.com";


                news.add(item);
            }
        }

        return news;
    }
}
