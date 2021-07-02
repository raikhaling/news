package com.example.newsbulletin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class KathmanduPostOpinions {

    ArrayList<OpinionItem> news;
    String response;

    public KathmanduPostOpinions(String response) {
        this.response = response;
        news= new ArrayList<>();
    }
    public ArrayList<OpinionItem> getNews()
    {

        Document document = Jsoup.parse(response);
        Elements article = document.select("div.block--morenews").select("article.article-image");

        for(int i=0; i<article.size(); i++){
            String link = "https://kathmandupost.com" + article.get(i).select("a").attr("href");
            String title = article.get(i).select("h3").text();
            String img = article.get(i).select("img").attr("data-src");
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
