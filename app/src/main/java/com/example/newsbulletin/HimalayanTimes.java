package com.example.newsbulletin;

import android.content.Context;
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

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HimalayanTimes {

    ArrayList<NewsItem> news;
    Context context;
    String[] date;

    public HimalayanTimes(Context context) {
        this.context = context;
        news= new ArrayList<>();
    }
    public ArrayList<NewsItem> getNews() throws IOException
    {

        String url = "https://thehimalayantimes.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request requestHttp = new Request.Builder().url(url).get().build();
        Document document = Jsoup.parse(okHttpClient.newCall(requestHttp).execute().body().string());



        Elements newsItems1 = document.select("div.ht-homepage-left-articles").select("ul.mainNews");
        Elements items = newsItems1.select("li");

        for(Element item : items)
        {
            String imgsrc = item.getElementsByTag("img").attr("data-lazy-src");
            String title = item.select("h4").select("a").attr("title");
            String link = item.select("h4").select("a").attr("href");

            if(!imgsrc.equals("")){
                NewsItem article = new NewsItem();
                article.link = link;
                article.title =title;
                article.imgsrc = imgsrc;
                article.tag = "nepal";
                article.publisher = "thehimalayantimes.com";
                article.source_logo = "https://thehimalayantimes.com/wp-content/themes/tht/images/the-himalayan-times.png";

                news.add(article);
            }

        }


        Elements KtmItems = document.select("div.capital").select("ul.mainNews").select("li");

        for(Element item: KtmItems){
            String imgsrc = item.getElementsByTag("img").attr("data-lazy-src");
            String title = item.select("h4").select("a").attr("title");
            String link = item.select("h4").select("a").attr("href");
            if(!imgsrc.equals("")){
                NewsItem article = new NewsItem();
                article.link = link;
                article.title =title;
                article.imgsrc = imgsrc;
                article.tag = "kathmandu, nepal";
                article.publisher = "thehimalayantimes.com";
                article.source_logo = "https://thehimalayantimes.com/wp-content/themes/tht/images/the-himalayan-times.png";

                news.add(article);
            }
        }


        Elements newsItems2 = document.select("div.world").select("ul.mainNews");
        Elements items2 = newsItems2.select("li");

        for(Element item : items2)
        {
            String imgsrc = item.getElementsByTag("img").attr("data-lazy-src");
            String title = item.select("h4").select("a").attr("title");
            String link = item.select("h4").select("a").attr("href");
            if(!imgsrc.equals("")){
                NewsItem article = new NewsItem();
                article.link = link;
                article.title =title;
                article.imgsrc = imgsrc;
                article.tag = "world";
                article.publisher = "thehimalayantimes.com";
                article.source_logo = "https://thehimalayantimes.com/wp-content/plugins/tht-mobile/theme/mobile/images/the-himalayan-times.png";

                news.add(article);
            }

        }



        Elements newsItems3 = document.select("div.sports").select("ul.mainNews");
        Elements items3 = newsItems3.select("li");

        for(Element item : items3)
        {

            String imgsrc = item.getElementsByTag("img").attr("data-lazy-src");
            String title = item.select("h4").select("a").attr("title");
            String link = item.select("h4").select("a").attr("href");
            if(!imgsrc.equals("")){
                NewsItem article = new NewsItem();
                article.link = link;
                article.title =title;
                article.imgsrc = imgsrc;
                article.tag = "sports";
                article.publisher = "thehimalayantimes.com";
                article.source_logo = "https://thehimalayantimes.com/wp-content/plugins/tht-mobile/theme/mobile/images/the-himalayan-times.png";

                news.add(article);
            }
        }




        Elements newsItems4 = document.select("div.entertainment").select("ul.mainNews");
        Elements items4 = newsItems4.select("li");

        for(Element item : items4)
        {

            String imgsrc = item.getElementsByTag("img").attr("data-lazy-src");
            String title = item.select("h4").select("a").attr("title");
            String link = item.select("h4").select("a").attr("href");
            if(!imgsrc.equals("")){
                NewsItem article = new NewsItem();
                article.link = link;
                article.title =title;
                article.imgsrc = imgsrc;
                article.tag = "entertainment";
                article.publisher = "thehimalayantimes.com";
                article.source_logo = "https://thehimalayantimes.com/wp-content/plugins/tht-mobile/theme/mobile/images/the-himalayan-times.png";

                news.add(article);
            }

        }

        for(int i=0; i<news.size(); i++){

            RequestQueue queue = Volley.newRequestQueue(context);
            final int finalI = i;
            StringRequest request = new StringRequest(news.get(finalI).link, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Document document = Jsoup.parse(response);
                    date = document.select("div.postMeta").text().split("\\s+");
                    news.get(finalI).date = date[1] + " " + date[2] + " " + date[3];
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            queue.add(request);

        }

        return news;
    }
}
