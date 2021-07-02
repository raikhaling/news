package com.example.newsbulletin;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;



public class SearchAdapter extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<NewsItem> newsList;
    ArrayList<NewsItem> searchList;

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

    public SearchAdapter(Context context, ArrayList<ArrayList> news) {
        this.context = context;
        this.newsList = news.get(0);
        this.searchList = news.get(1);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            view = View.inflate(context, R.layout.news_list_item_layout, null);
        }
        NewsItem currentNews = newsList.get(i);
        ImageView iv1 = view.findViewById(R.id.imageview_1);
        ImageView iv2 = view.findViewById(R.id.imageview_2);
        TextView tvTitle = view.findViewById(R.id.textview_1);
        TextView tvDate = view.findViewById(R.id.textview_2);
        TextView tvPublisher = view.findViewById(R.id.textview_3);


        Picasso.with(context).load(currentNews.imgsrc).placeholder(R.drawable.placeholder).into(iv1);
        Picasso.with(context).load(currentNews.source_logo).placeholder(R.drawable.placeholder).into(iv2);
        tvTitle.setText(currentNews.title);
        tvDate.setText(currentNews.date);
        tvPublisher.setText(currentNews.publisher);

        return view;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<NewsItem> filteredList = new ArrayList<>();

            if (charSequence.toString().isEmpty()){
                filteredList.addAll(newsList);
            }
            else {
                for (NewsItem news : searchList){
                    if (news.title.toLowerCase().contains(charSequence.toString().toLowerCase()) || news.tag.contains(charSequence.toString().toLowerCase())){
                        filteredList.add(news);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            newsList.clear();
            newsList.addAll((Collection<? extends NewsItem>) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
