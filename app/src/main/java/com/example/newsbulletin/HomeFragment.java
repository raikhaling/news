package com.example.newsbulletin;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

public class HomeFragment extends Fragment {

    ArrayList<NewsItem> news;   // All the news collection here
    ArrayList<NewsItem> headlines;
    ArrayList<NewsItem> finalHeadlines;  //Headlines news here!
    ArrayList<NewsItem> nepal;
    ArrayList<NewsItem> world;
    ArrayList<NewsItem> sports;
    ArrayList<NewsItem> tech;
    ArrayList<NewsItem> entertainment;
    ListView listView;


    private ViewPager viewPager;
    public View view;
    public PageAdapter pagerAdapter;
    private ShimmerFrameLayout shimmerFrameLayout;
    TextView date;
    RelativeLayout relativeLayout;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.fragment_home, container, false);
        shimmerFrameLayout = view.findViewById(R.id.shimmer);
        setHasOptionsMenu(true);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle(null);

        relativeLayout = view.findViewById(R.id.error);


//        putting date
        date = view.findViewById(R.id.date);
        Date datetime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        formatter = new SimpleDateFormat("EEEE, dd MMMM, yyyy");
        String time = formatter.format(datetime);
        date.setText(time);


        finalHeadlines = new ArrayList<>();
        nepal = new ArrayList<>();
        world = new ArrayList<>();
        sports = new ArrayList<>();
        tech = new ArrayList<>();
        entertainment = new ArrayList<>();


        final TabLayout tabLayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.viewpager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        news = new ArrayList<>();
        headlines = new ArrayList<>();
        listView = view.findViewById(R.id.listview_news);
        final RequestQueue queue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));


//        Checking the connection
        final StringRequest request = new StringRequest("https://www.google.com/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                relativeLayout.setVisibility(View.GONE);


//                instances for each required website
                final HimalayanTimes himalayanTimes = new HimalayanTimes(getContext());
                final GsmArena gsmArena = new GsmArena();
                final CinemaBlend cinemaBlend = new CinemaBlend();
                final KathmanduPost kathmanduPost = new KathmanduPost(getContext());
                final GlobalNews globalNews = new GlobalNews();
                final NepaliTimes nepaliTimes = new NepaliTimes(getContext());
                final GoalNepal goalNepal = new GoalNepal(getContext());
                final GadgetByte gadgetByte = new GadgetByte();
                final TechLekh techLekh = new TechLekh();
                final OnlineKhabar onlineKhabar = new OnlineKhabar();
                final NepaliSansar nepaliSansar = new NepaliSansar();
                final CricketingNepal cricketingNepal = new CricketingNepal();


                //  thread for each website
                //  thread for thehimalayantimes


                final Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<NewsItem> himalyannews;
                            himalyannews = himalayanTimes.getNews();
                            news.addAll(himalyannews);
                            for (int i = 0; i < 4; i++) {
                                finalHeadlines.add(himalyannews.get(i));
                            }
                        } catch (Exception ignored) {
                        }
                    }
                });
                thread.start();


//                thread for gsmArena
                final Thread thread1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<NewsItem> gsmarenanews;
                            gsmarenanews = gsmArena.getNews();
                            news.addAll(gsmarenanews);
                            for (int i = 0; i < 3; i++) {
                                headlines.add(gsmarenanews.get(i));
                            }
                        } catch (Exception ignored) {
                        }
                    }
                });
                thread1.start();


//                thread for cinemaBlend
                final Thread thread2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<NewsItem> cinemablendnews;
                            cinemablendnews = cinemaBlend.getNews();
                            news.addAll(cinemablendnews);
                            for (int i = 0; i < 4; i++) {
                                headlines.add(cinemablendnews.get(i));
                            }
                        } catch (Exception ignored) {
                        }
                    }
                });
                thread2.start();


//                thread for kathmanduPost
                final Thread thread3 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<NewsItem> kathmandupostnews;
                            kathmandupostnews = kathmanduPost.getNews();
                            news.addAll(kathmandupostnews);
                            for (int i = 0; i < 3; i++) {
                                finalHeadlines.add(kathmandupostnews.get(i));
                            }
                        } catch (IOException ignored) {
                        }
                    }
                });
                thread3.start();


//                thread for globalNews
                final Thread thread4 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<NewsItem> globalnewsnews;
                            globalnewsnews = globalNews.getNews();
                            news.addAll(globalnewsnews);
                            for (int i = 0; i < 5; i++) {
                                finalHeadlines.add(globalnewsnews.get(i));
                            }
                        } catch (IOException ignored) {
                        }
                    }
                });
                thread4.start();


//                thread for nepaliTimes
                final Thread thread5 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<NewsItem> nepalitimesnews;
                            nepalitimesnews = nepaliTimes.getNews();
                            news.addAll(nepalitimesnews);
                            for (int i = 0; i < 3; i++) {
                                finalHeadlines.add(nepalitimesnews.get(i));
                            }
                        } catch (IOException ignored) {
                        }
                    }
                });
                thread5.start();


//                thread for GoalNepal
                final Thread thread6 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<NewsItem> goalNepalNews;
                            goalNepalNews = goalNepal.getNews();
                            news.addAll(goalNepalNews);
                            for (int i = 0; i < 4; i++) {
                                headlines.add(goalNepalNews.get(i));
                            }
                        } catch (IOException ignored) {
                        }
                    }
                });
                thread6.start();


//                thread for GadgetByteNepal
                final Thread thread7 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<NewsItem> gadgetbytenews;
                            gadgetbytenews = gadgetByte.getNews();
                            news.addAll(gadgetbytenews);
                            for (int i = 0; i < 3; i++) {
                                headlines.add(gadgetbytenews.get(i));
                            }
                        } catch (IOException ignored) {
                        }
                    }
                });
                thread7.start();


//                thread for Techlekh
                final Thread thread8 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<NewsItem> techlekhnews;
                            techlekhnews = techLekh.getNews();
                            news.addAll(techlekhnews);
                            for (int i = 0; i < 3; i++) {
                                headlines.add(techlekhnews.get(i));
                            }
                        } catch (IOException ignored) {
                        }
                    }
                });
                thread8.start();


                //                thread for onlinekhabar
                final Thread thread9 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<NewsItem> onlineKhabarnews;
                            onlineKhabarnews = onlineKhabar.getNews();
                            news.addAll(onlineKhabarnews);
                            for (int i = 0; i < 4; i++) {
                                finalHeadlines.add(onlineKhabarnews.get(i));
                            }
                        } catch (IOException ignored) {
                        }
                    }
                });
                thread9.start();


                //thread for nepalisansar
                final Thread thread11 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<NewsItem> nepalisansarnews;
                            nepalisansarnews = nepaliSansar.getNews();
                            news.addAll(nepalisansarnews);
                            for (int i = 0; i < 4; i++) {
                                finalHeadlines.add(nepalisansarnews.get(i));
                            }
                        } catch (IOException ignored) {
                        }
                    }
                });
                thread11.start();


//                thread for cricketingNepal
                final Thread thread12 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<NewsItem> cricketnews;
                            cricketnews = cricketingNepal.getNews();
                            news.addAll(cricketnews);
                            for (int i = 0; i < 4; i++) {
                                headlines.add(cricketnews.get(i));
                            }
                        } catch (IOException ignored) {
                        }
                    }
                });
                thread12.start();


                // This thread is created just to wait for other child threads to complete
                final Handler handler = new Handler();   // The handler is used to update the ui upon completion
                Thread finalThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            thread.join();
                            thread1.join();
                            thread2.join();
                            thread3.join();
                            thread4.join();
                            thread5.join();
                            thread6.join();
                            thread7.join();
                            thread8.join();
                            thread9.join();
                            thread11.join();
                            thread12.join();


                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                for (NewsItem item : news) {
                                    if (item.tag.contains("kathmandu"))
                                        nepal.add(item);
                                    if (item.tag.contains("football"))
                                        sports.add(item);
                                    if (item.tag.contains("cricket"))
                                        sports.add(item);
                                    switch (item.tag) {
                                        case "nepal":
                                            nepal.add(item);
                                            break;
                                        case "world":
                                            world.add(item);
                                            break;
                                        case "sports":
                                            sports.add(item);
                                            break;
                                        case "tech":
                                            tech.add(item);
                                            break;
                                        case "entertainment":
                                            entertainment.add(item);
                                            break;
                                    }
                                }


                                //                putting each news item to the main container
                                Collections.shuffle(headlines);

                                Collections.shuffle(finalHeadlines);
                                finalHeadlines.addAll(headlines);
                                Collections.shuffle(nepal);
                                Collections.shuffle(world);
                                Collections.shuffle(sports);
                                Collections.shuffle(tech);
                                Collections.shuffle(entertainment);


                                tab1 t1 = new tab1(finalHeadlines);
                                t1.setRetainInstance(true);

                                tab2 t2 = new tab2(nepal);
                                t2.setRetainInstance(true);

                                tab3 t3 = new tab3(world);
                                t3.setRetainInstance(true);

                                tab4 t4 = new tab4(sports);
                                t4.setRetainInstance(true);

                                tab5 t5 = new tab5(tech);
                                t5.setRetainInstance(true);

                                tab6 t6 = new tab6(entertainment);
                                t6.setRetainInstance(true);

                                assert getFragmentManager() != null;
                                pagerAdapter = new PageAdapter(finalHeadlines, nepal, world, sports, tech, entertainment, getFragmentManager(), tabLayout.getTabCount());
                                viewPager.setAdapter(pagerAdapter);
                                shimmerFrameLayout.setVisibility(View.GONE);

                            }
                        });
                    }
                });
                finalThread.start();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Internet Connection Error!", Toast.LENGTH_SHORT).show();
                shimmerFrameLayout.setVisibility(View.GONE);
                tabLayout.setVisibility(View.GONE);
            }
        });
        queue.add(request);


        this.setRetainInstance(true);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmer();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewItem.setActionView(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getContext(), Search.class);
                intent.putExtra("search", query);
                intent.putExtra("news", news);
                startActivity(intent);
                Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}