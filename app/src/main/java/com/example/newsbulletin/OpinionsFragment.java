package com.example.newsbulletin;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;


public class OpinionsFragment extends Fragment {
    OpinionsAdapter adapter;
    ListView listView;

    public View view;
    private ShimmerFrameLayout shimmerFrameLayout;
    private RelativeLayout relativeLayout;
    ArrayList<OpinionItem> opinions;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        opinions = new ArrayList<>();
        view = inflater.inflate(R.layout.fragment_opinions, container, false);
        shimmerFrameLayout = view.findViewById(R.id.shimmer);
        relativeLayout = view.findViewById(R.id.error);
        listView = view.findViewById(R.id.listview_news);


        RequestQueue queue = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        StringRequest request = new StringRequest("https://kathmandupost.com/opinion", new Response.Listener<String>() {
            @Override
            public void onResponse(String response3) {

                relativeLayout.setVisibility(View.GONE);
                KathmanduPostOpinions kathmanduPostOpinions = new KathmanduPostOpinions(response3);
                final HimalayanOpinions himalayanOpinions = new HimalayanOpinions();

                ArrayList<OpinionItem> kathmanduarray;
                kathmanduarray = kathmanduPostOpinions.getNews();
                for (int i=0; i<kathmanduarray.size()/2; i++){
                    opinions.add(kathmanduarray.get(i));
                }

                final Thread thread1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try  {
                            ArrayList<OpinionItem> himalayanarray;
                            try {
                                himalayanarray = himalayanOpinions.getNews();
                                for (int i=0; i<3; i++){
                                    opinions.add(himalayanarray.get(i));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } catch (Exception ignored) {
                        }
                    }
                });
                thread1.start();


                final Handler handler = new Handler();
                Thread finalThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            thread1.join();
                        }catch (Exception e){

                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                shimmerFrameLayout.setVisibility(View.GONE);
                                Collections.shuffle(opinions);
                                adapter = new OpinionsAdapter(getContext(), opinions);
                                adapter.notifyDataSetChanged();
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        OpinionItem currentNews = opinions.get(i);
                                        Intent intent = new Intent(getContext(), OpinionDetailActivity.class);
                                        intent.putExtra("newsitem", currentNews);
                                        startActivity(intent);
                                        Objects.requireNonNull(getActivity()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    }
                                });
                            }
                        });
                    }
                });
                finalThread.start();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Connection Error!", Toast.LENGTH_SHORT).show();
                shimmerFrameLayout.setVisibility(View.GONE);
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

}
