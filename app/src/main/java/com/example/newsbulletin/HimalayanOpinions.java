package com.example.newsbulletin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HimalayanOpinions {

    ArrayList<OpinionItem> news;

    public HimalayanOpinions() {
        news= new ArrayList<>();
    }
    public ArrayList<OpinionItem> getNews() throws IOException
    {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("https://thehimalayantimes.com/category/opinion/").get().build();
        Document document = Jsoup.parse(okHttpClient.newCall(request).execute().body().string());
        Elements article = document.select("ul.mainNews").select("li");

        for(int i=0; i<article.size(); i++){
            String link = article.get(i).select("a").attr("href");
            String title = article.get(i).select("a").attr("title");
            String img = article.get(i).select("img").attr("data-lazy-src");
            String description = article.get(i).select("p").text();


            OpinionItem item = new OpinionItem();

            item.link = link;
            item.title =title;
            item.imgsrc = img;
            item.description= description;

            if(!img.equals(""))
                news.add(item);

        }
        return news;
    }
}
