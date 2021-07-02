package com.example.newsbulletin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

public class tab1 extends Fragment {


    ArrayList<NewsItem> headlines;

    ListView listView;
    ListView recommendationView;
    NewsAdapter adapter;
    NewsAdapter recommendedAdapter;
    SaveNews db;
    ArrayList<RecommendationClass> recommendedList;
    String categorySelect = "";
    int highestScore = 0;
    int selectorScore = 0;
    ArrayList<NewsItem> recommendedNews;

    public tab1(ArrayList<NewsItem> headlines) {
        this.headlines = headlines;
        this.recommendedNews = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bulletins, container, false);


        recommendationView = view.findViewById(R.id.recommendations);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            recommendedAdapter = new NewsAdapter(this.getActivity(), recommendedNews);
        }


        listView = view.findViewById(R.id.listview_news);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            adapter = new NewsAdapter(this.getActivity(), headlines);
        }


        // get the score of the news category to update the recommendation
        db = new SaveNews(getContext());
        Cursor recommended = db.getRecommended();
        recommendedList = new ArrayList<>();
        int n = 0;
        while (recommended.moveToNext()) {
            RecommendationClass recommendationClass = new RecommendationClass();
            String category = recommended.getString(1);
            int score = recommended.getInt(2);

            recommendationClass.category = category;
            recommendationClass.score = score;
            recommendedList.add(recommendationClass);

        }
        recommended.close();

        categorySelect = recommendedList.get(0).category;
        highestScore = recommendedList.get(0).score;
        for (RecommendationClass item : recommendedList) {
            if (item.score > highestScore) {
                highestScore = item.score;
                categorySelect = item.category;
            }
        }


        for (NewsItem item : headlines) {
            if (item.tag.equals(categorySelect)) {
                recommendedNews.add(item);
                if (n == 2){
                    break;
                }
                n++;
            }
        }


        recommendationView.setAdapter(recommendedAdapter);
        Utility.setListViewHeightBasedOnChildren(recommendationView);

        listView.setAdapter(adapter);
        // adapter.notifyDataSetChanged();
        Utility.setListViewHeightBasedOnChildren(listView);

        recommendationView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsItem currentNews = recommendedNews.get(i);
                Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                for (RecommendationClass item : recommendedList) {
                    if (currentNews.tag.equals(item.category)) {
                        selectorScore = item.score;
                        categorySelect = item.category;
                        item.category += 1;
                    }
                }
                selectorScore += 1;
                db.updateScore(currentNews.tag, selectorScore);
                intent.putExtra("newsitem", currentNews);
                startActivity(intent);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsItem currentNews = headlines.get(i);
                Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                for (RecommendationClass item : recommendedList) {
                    if (currentNews.tag.equals(item.category)) {
                        selectorScore = item.score;
                        categorySelect = item.category;
                        item.category += 1;
                    }
                }
                selectorScore += 1;
                db.updateScore(currentNews.tag, selectorScore);
                intent.putExtra("newsitem", currentNews);
                startActivity(intent);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        return view;
    }


}