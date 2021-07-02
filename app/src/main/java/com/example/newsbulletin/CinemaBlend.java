package com.example.newsbulletin;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class CinemaBlend {
    ArrayList<NewsItem> news;

    public CinemaBlend() {
        news = new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArrayList<NewsItem> getNews() throws IOException{

        String url = "https://www.cinemablend.com/news.php";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Document document = Jsoup.parse(Objects.requireNonNull(okHttpClient.newCall(request).execute().body()).string());
        Elements articles = document.select("div.order-of-type-2").select("div.story-related").select("a");

        for(Element article : articles)
        {
            String link = article.attr("href");
            String title = article.attr("title");
            String img = article.select("div.story-related-content").select("span.story-cover-image").select("img").attr("data-src");
            String date = article.select("span.story-related-published-date").text();

            NewsItem newsItem = new NewsItem();
            newsItem.imgsrc = img;
            newsItem.title = title;
            newsItem.link = link;
            newsItem.tag = "entertainment";
            newsItem.publisher = "cinemablend.com";
            newsItem.source_logo = "https://www.cinemablend.com/static/images/cb-logo.jpg";

            if(!date.equals(""))
            {
                newsItem.date = date + " ago";
                news.add(newsItem);
            }
        }

        return news;
    }
}
