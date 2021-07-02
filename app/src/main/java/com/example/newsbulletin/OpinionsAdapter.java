package com.example.newsbulletin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;



public class OpinionsAdapter extends BaseAdapter {

    Context context;
    ArrayList<OpinionItem> newsList;
    ImageView iv1;

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

    public OpinionsAdapter(Context context, ArrayList<OpinionItem> news) {
        this.context = context;
        this.newsList = news;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            view = View.inflate(context, R.layout.opinion_layout, null);
        }
        OpinionItem currentNews = newsList.get(i);
        iv1 = view.findViewById(R.id.image);
        TextView tvTitle = view.findViewById(R.id.title);
        TextView description = view.findViewById(R.id.description);

        Picasso.with(context).load(currentNews.imgsrc).placeholder(R.drawable.placeholder).into(iv1);
        tvTitle.setText(currentNews.title);
        description.setText(currentNews.description);
        notifyDataSetChanged();

        return view;
    }
}
