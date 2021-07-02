package com.example.newsbulletin;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class KathmanduPost {
    ArrayList<NewsItem> news;
    String[] date;
    Context context;

    public KathmanduPost(Context context) {
        news = new ArrayList<>();
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ArrayList<NewsItem> getNews() throws IOException{

        String url = "https://kathmandupost.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request requestHttp = new Request.Builder().url(url).get().build();
        Document document = Jsoup.parse(Objects.requireNonNull(okHttpClient.newCall(requestHttp).execute().body()).string());
        Elements articles = document.select("div.block--morenews").select("article");

        for(Element article : articles)
        {
            String link = url+article.select("a").first().attr("href");
            String title = article.select("h3").text();
            String img = article.select("img").attr("data-src");

            final NewsItem newsItem = new NewsItem();

            newsItem.link = link;
            newsItem.imgsrc = img;
            newsItem.title = title;
            newsItem.tag = "nepal";
            newsItem.publisher = "Kathmandupost.com";
            newsItem.source_logo = "https://jcss-cdn.kathmandupost.com/assets/images/logos/thekathmandupost-logo.png";

            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest request = new StringRequest(link, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Document document = Jsoup.parse(response);
                    date = document.select("div.updated-time").text().split("\\s+");
                    newsItem.date = date[3] + " " + date[4] + " " + date[5];
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            queue.add(request);

            news.add(newsItem);
        }

        return news;
    }

}
