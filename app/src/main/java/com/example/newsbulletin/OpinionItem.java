package com.example.newsbulletin;

import java.io.Serializable;

public class OpinionItem implements Serializable {
    String imgsrc;
    String title;
    String link;
    String description;

    public String toString(){
        return title;
    }
}
