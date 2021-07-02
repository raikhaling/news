package com.example.newsbulletin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class GlobalNews {
    ArrayList<NewsItem> news;

    public GlobalNews() {
        news = new ArrayList<>();
    }

    public ArrayList<NewsItem> getNews() throws IOException {
        String url = "https://globalnews.ca/world";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Document document = Jsoup.parse(okHttpClient.newCall(request).execute().body().string());

        Elements articles = document.getElementById("archive-latestStories").select("li");
        for (Element article : articles) {
            String link = article.select("a").attr("href");
            String title = article.select("span.c-posts__headlineText").text();
            String img = article.select("img").attr("data-src");
            String date = article.select("div.c-posts__info").text();
            NewsItem newsItem = new NewsItem();
            if(!img.equals("")) {
                newsItem.imgsrc = img;
                newsItem.title = title;
                newsItem.link = link;

                if(date.contains("sports")){
                    newsItem.tag = "sports";
                }
                if(date.contains("World"))
                {
                    newsItem.tag = "world";
                }
                else if(date.contains("Tech")){
                    newsItem.tag = "tech";
                }
                else if(date.contains("Politics"))
                {
                    newsItem.tag = "politics";
                }
                else
                    newsItem.tag = "world";

                String[] valid_date = date.split("\\s+");
                date = valid_date[1] + " " + valid_date[2];
                newsItem.date = date;
                newsItem.source_logo = "https://www.readspeaker.com/wp-content/uploads/globalnews.png";
                newsItem.publisher = "globalnews.ca";
                news.add(newsItem);
            }

        }
        return news;
    }
}
