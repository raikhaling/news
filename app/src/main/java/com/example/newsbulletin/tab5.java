package com.example.newsbulletin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class tab5 extends Fragment {


    ArrayList<NewsItem> tech;

    ListView listView;
    NewsAdapter adapter;
    SaveNews db;
    ArrayList<RecommendationClass> recommendedList;
    String categorySelect = "";
    int highestScore = 0;
    int selectorScore = 0;

    public tab5(ArrayList<NewsItem> tech) {
        this.tech = tech;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bulletins, container, false);
        listView = view.findViewById(R.id.listview_news);
        adapter = new NewsAdapter(this.getActivity(), tech);

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

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsItem currentNews = tech.get(i);
                Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                intent.putExtra("newsitem", currentNews);
                for (RecommendationClass item : recommendedList) {
                    if (currentNews.tag.equals(item.category)) {
                        selectorScore = item.score;
                        categorySelect = item.category;
                        item.category += 1;
                    }
                }
                selectorScore += 1;
                db.updateScore(currentNews.tag, selectorScore);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        return view;
    }
}