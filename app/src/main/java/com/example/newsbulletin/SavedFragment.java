package com.example.newsbulletin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

public class SavedFragment extends Fragment {

    View view;
    public OfflineNewsAdapter adapter;
    public ListView listView;
    TextView saved_articles;
    ArrayList<NewsItem> news;
    SaveNews myDb;
    ArrayList<NewsItem> savedNews;
    int size = 0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_saved, container, false);
        news = new ArrayList<>();
        savedNews = new ArrayList<>();
        ShimmerFrameLayout shimmerFrameLayout = view.findViewById(R.id.shimmer);
        listView = view.findViewById(R.id.listview_news);
        saved_articles = view.findViewById(R.id.saved_articles);
        myDb = new SaveNews(getContext());
        Cursor res = myDb.getAllData();

        while (res.moveToNext()) {
            NewsItem newsItem = new NewsItem();
            String Img_src = res.getString(1);
            String Source_logo = res.getString(2);
            String Title = res.getString(3);
            String Publisher = res.getString(4);
            String Date = res.getString(5);
            String Saved_date = res.getString(6);
            String Link = res.getString(7);

            newsItem.imgsrc = Img_src;
            newsItem.source_logo = Source_logo;
            newsItem.title = Title;
            newsItem.date = Date;
            newsItem.publisher = Publisher;
            newsItem.link = Link;

            news.add(newsItem);
        }
        res.close();

        size = news.size() - 1;
        for (int i = size; i >= 0; i--) {
            savedNews.add(news.get(i));
        }

        if (savedNews.size() >= 1) {
            saved_articles.setVisibility(View.GONE);
        }


        shimmerFrameLayout.setVisibility(View.GONE);
        adapter = new OfflineNewsAdapter(getContext(), savedNews);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsItem currentNews = savedNews.get(i);
                Intent intent = new Intent(getContext(), OfflineNewsDetail.class);
                intent.putExtra("newsitem", currentNews);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        return view;
    }
}