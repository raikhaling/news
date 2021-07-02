package com.example.newsbulletin;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import okhttp3.OkHttpClient;
import okhttp3.Request;


public class NepaliTimes{
    ArrayList<NewsItem> news;
    Context context;
    String date = "";

    public NepaliTimes(Context context) {
        this.context = context;
        news= new ArrayList<>();
    }
    public ArrayList<NewsItem> getNews() throws IOException {
        String url = "https://www.nepalitimes.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request requestHttp = new Request.Builder().url(url).get().build();
        Document document = Jsoup.parse(okHttpClient.newCall(requestHttp).execute().body().string());
        Elements articles = document.select("div#secondary1").select("li");
        articles.addAll(document.select("div.here-now-section-height").select("li"));

        for(Element article : articles)
        {
            String link = article.select("a").attr("href");
            String title = article.select("h6").text();

            final NewsItem newsItem = new NewsItem();
            newsItem.link = link;
            newsItem.title = title;
            newsItem.source_logo = "https://www.nepalitimes.com/wp-content/themes/nepalitimes/assets/images/nepalitimes-logo.png";
            newsItem.tag = "nepal";
            newsItem.publisher = "nepalitimes.com";


            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest request = new StringRequest(link, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Document document = Jsoup.parse(response);
                    date = document.select("span.dates").text();

                    newsItem.imgsrc = document.select("figure.wp-caption").select("img").attr("src");
                    newsItem.date = date;
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            queue.add(request);



            if (!link.equals("")){
                news.add(newsItem);
            }
        }
        return news;
    }
}