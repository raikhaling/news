package com.example.newsbulletin;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PageAdapter extends FragmentPagerAdapter {
    private int numberOfTabs;
    ArrayList<NewsItem> headlines;
    ArrayList<NewsItem> nepal;
    ArrayList<NewsItem> world;
    ArrayList<NewsItem> sports;
    ArrayList<NewsItem> tech;
    ArrayList<NewsItem> entertainment;

    public PageAdapter(ArrayList<NewsItem> headlines, ArrayList<NewsItem> nepal, ArrayList<NewsItem> world, ArrayList<NewsItem> sports, ArrayList<NewsItem> tech, ArrayList<NewsItem> entertainment, @NonNull FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        this.headlines = headlines;
        this.nepal = nepal;
        this.world = world;
        this.sports = sports;
        this.tech = tech;
        this.entertainment = entertainment;
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new tab1(headlines);

            case 1:
                return new tab2(nepal);

            case 2:
                return new tab3(world);

            case 3:
                return new tab4(sports);

            case 4:
                return new tab5(tech);

            case 5:
                return new tab6(entertainment);

            default:
                return null;
        }
    }
    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }
}
