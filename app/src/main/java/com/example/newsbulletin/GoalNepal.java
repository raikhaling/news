package com.example.newsbulletin;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class GoalNepal {

    ArrayList<NewsItem> news;
    Context context;
    String[] date;

    public GoalNepal(Context context) {
        this.context = context;
        news = new ArrayList<>();
    }

    public ArrayList<NewsItem> getNews() throws IOException {

        String url = "https://www.goalnepal.com/news/live/";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request requestHttp = new Request.Builder().url(url).get().build();
        Document document = Jsoup.parse(okHttpClient.newCall(requestHttp).execute().body().string());
        Elements posts = document.select("div.list-news");

        for(int i=0; i<posts.size(); i++){
            String imgsrc = posts.get(i).select("img").attr("src");
            String title = posts.get(i).getElementsByClass("media-heading").text();
            String link = posts.get(i).select("a").first().attr("href");

            if(!imgsrc.equals("")){
                final NewsItem item = new NewsItem();
                item.link = link;
                item.title =title;
                item.imgsrc = imgsrc;
                item.tag = "sports, football";
                item.publisher="goalnepal.com";
                item.source_logo="https://www.goalnepal.com/layouts/img/default.png";

                RequestQueue queue = Volley.newRequestQueue(context);
                StringRequest request = new StringRequest(link, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Document document = Jsoup.parse(response);
                        date = document.select("span.date-line").text().split("\\s+");
                        item.date = date[0] + " " + date[1] + " " + date[2];
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                queue.add(request);

                news.add(item);
            }
        }

        return news;
    }

}
