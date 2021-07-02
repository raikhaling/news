package com.example.newsbulletin;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class SiteAdapter extends BaseAdapter {

    Context context;
    ArrayList<NewsItem> newsList;

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int i) {
        return newsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public SiteAdapter(Context context, ArrayList<NewsItem> news) {
        this.context = context;
        this.newsList = news;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            view = View.inflate(context, R.layout.publisher, null);
        }
        NewsItem currentNews = newsList.get(i);
        ImageView iv1 = view.findViewById(R.id.image);
        TextView tvTitle = view.findViewById(R.id.title);
        Picasso.with(context).load(currentNews.source_logo).placeholder(R.drawable.placeholder).into(iv1);
        tvTitle.setText(currentNews.title);

        return view;
    }
}
