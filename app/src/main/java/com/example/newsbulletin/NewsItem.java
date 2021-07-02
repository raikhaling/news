package com.example.newsbulletin;


import java.io.Serializable;

public class NewsItem implements Serializable {
    String imgsrc;
    String title;
    String link;
    String date;
    String publisher;
    String tag;
    String source_logo;

    public String toString() {
        return title;
    }
}
