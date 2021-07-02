package com.example.newsbulletin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Search extends AppCompatActivity {

    ArrayList<NewsItem> news;
    ArrayList<NewsItem> searchResult;
    ArrayList<ArrayList> twoList;
    String search;

    ListView listView;
    SearchAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.search_toolbar);
        searchResult = new ArrayList<>();
        twoList = new ArrayList<>();

        search = Objects.requireNonNull(getIntent().getExtras()).getString("search");
        news = (ArrayList<NewsItem>) getIntent().getExtras().getSerializable("news");

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.listview_news);


        for(NewsItem item: news){
            if (item.title.toLowerCase().contains(search.toLowerCase()) || item.tag.contains(search.toLowerCase())){
                searchResult.add(item);
            }
        }

        if(!searchResult.isEmpty()){
            getSupportActionBar().setTitle("Showing results for " + search);
        }
        else {
            getSupportActionBar().setTitle("No results for " + search);
        }

        twoList.addAll(Collections.singleton(searchResult));
        twoList.addAll(Collections.singleton(news));
        adapter = new SearchAdapter(Search.this, twoList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsItem currentNews = searchResult.get(i);
                Intent intent = new Intent(Search.this, NewsDetailActivity.class);
                intent.putExtra("newsitem", currentNews);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                if(!searchResult.isEmpty()){
                    getSupportActionBar().setTitle("Showing results for " + s);
                }
                else {
                    getSupportActionBar().setTitle("No results for " + s);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                if(!searchResult.isEmpty()){
                    getSupportActionBar().setTitle("Showing results for " + s);
                }
                else {
                    getSupportActionBar().setTitle("No results for " + s);
                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}