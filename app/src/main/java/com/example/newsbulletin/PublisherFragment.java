package com.example.newsbulletin;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class PublisherFragment extends Fragment {

    public View view;
    SiteAdapter adapter;
    GridView nepalView;
    GridView worldView;
    GridView nepaliView;
    GridView sportView;
    GridView techView;
    GridView entertainmentView;

    ArrayList<NewsItem> nepal;
    ArrayList<NewsItem> world;
    ArrayList<NewsItem> nepali;
    ArrayList<NewsItem> sports;
    ArrayList<NewsItem> tech;
    ArrayList<NewsItem> entertainment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_publisher, container, false);
        nepalView = view.findViewById(R.id.nepal);
        worldView = view.findViewById(R.id.world);
        nepaliView = view.findViewById(R.id.nepali);
        sportView = view.findViewById(R.id.sports);
        techView = view.findViewById(R.id.tech);
        entertainmentView = view.findViewById(R.id.entertainment);

        nepal = new ArrayList<>();
        world = new ArrayList<>();
        nepali = new ArrayList<>();
        sports = new ArrayList<>();
        tech = new ArrayList<>();
        entertainment = new ArrayList<>();

        NewsItem onlineKhabar = new NewsItem();
        onlineKhabar.title = "OnlineKhabar";
        onlineKhabar.link = "https://english.onlinekhabar.com/";
        onlineKhabar.source_logo = "https://www.onlinekhabar.com/wp-content/themes/onlinekhabar-2018/img/logoMainWht.png";
        nepal.add(onlineKhabar);

        NewsItem kathmandupost = new NewsItem();
        kathmandupost.link = "https://kathmandupost.com";
        kathmandupost.title = "Kathmandu Post";
        kathmandupost.source_logo = "https://jcss-cdn.kathmandupost.com/assets/images/logos/thekathmandupost-logo.png";
        nepal.add(kathmandupost);

        NewsItem himalyantimes = new NewsItem();
        himalyantimes.link = "https://thehimalayantimes.com";
        himalyantimes.title = "Himalayan Times";
        himalyantimes.source_logo = "https://thehimalayantimes.com/wp-content/themes/tht/images/the-himalayan-times.png";
        nepal.add(himalyantimes);

        NewsItem nepalitimes = new NewsItem();
        nepalitimes.title = "Nepali Times";
        nepalitimes.link = "https://www.nepalitimes.com";
        nepalitimes.source_logo = "https://www.nepalitimes.com/wp-content/themes/nepalitimes/assets/images/nepalitimes-logo.png";
        nepal.add(nepalitimes);

        NewsItem setopati = new NewsItem();
        setopati.link = "https://en.setopati.com";
        setopati.title = "Setopati";
        setopati.source_logo = "https://en.setopati.com/images/logo.jpg?v=1.2";
        nepal.add(setopati);

        NewsItem techlekh = new NewsItem();
        techlekh.link = "https://techlekh.com";
        techlekh.title = "Techlekh";
        techlekh.source_logo = "https://techlekh.com/wp-content/uploads/2017/05/TechLekh-Logo.png";
        tech.add(techlekh);

        NewsItem gadgetbyte = new NewsItem();
        gadgetbyte.link = "https://www.gadgetbytenepal.com";
        gadgetbyte.title = "Gadgetbyte";
        gadgetbyte.source_logo = "https://www.gadgetbytenepal.com/wp-content/uploads/2017/06/gadgetbyte-nepal-300x60.png";
        tech.add(gadgetbyte);

        NewsItem cinemablend = new NewsItem();
        cinemablend.title = "Cinemablend";
        cinemablend.link = "https://www.cinemablend.com/news.php";
        cinemablend.source_logo = "https://www.cinemablend.com/static/images/cb-logo.jpg";
        entertainment.add(cinemablend);

        NewsItem nepalisansar = new NewsItem();
        nepalisansar.link = "https://www.nepalisansar.com";
        nepalisansar.title = "Nepali Sansar";
        nepalisansar.source_logo = "https://www.nepalisansar.com/wp-content/uploads/2018/03/nepali-sansar-logo.png";
        nepal.add(nepalisansar);

        NewsItem goalnepal = new NewsItem();
        goalnepal.link = "https://www.goalnepal.com";
        goalnepal.title = "Goal Nepal";
        goalnepal.source_logo="https://www.goalnepal.com/layouts/img/default.png";
        sports.add(goalnepal);

        NewsItem globalnews = new NewsItem();
        globalnews.title = "Global News";
        globalnews.link = "https://globalnews.ca";
        globalnews.source_logo = "https://www.readspeaker.com/wp-content/uploads/globalnews.png";
        world.add(globalnews);

        NewsItem gsmarena = new NewsItem();
        gsmarena.title = "Gsm Arena";
        gsmarena.link = "https://www.gsmarena.com/";
        gsmarena.source_logo = "https://fdn.gsmarena.com/vv/assets12/css/m/i/logo-fallback.gif";
        tech.add(gsmarena);

        NewsItem bbc = new NewsItem();
        bbc.title = "BBC";
        bbc.link = "https://www.bbc.com/";
        bbc.source_logo = "https://ichef.bbci.co.uk/images/ic/1200x675/p07jbsw9.jpg";
        world.add(bbc);

        NewsItem cnn = new NewsItem();
        cnn.link = "https://edition.cnn.com/";
        cnn.title = "CNN";
        cnn.source_logo = "https://cdn.cnn.com/cnn/.element/img/4.0/subscription/logo_cnn10.jpg";
        world.add(cnn);

        NewsItem aljazeera = new NewsItem();
        aljazeera.title = "Aljazeera";
        aljazeera.link = "https://www.aljazeera.com/";
        aljazeera.source_logo = "https://www.aljazeera.com/assets/images/aj-logo-lg-124.png";
        world.add(aljazeera);

        NewsItem nbc = new NewsItem();
        nbc.title = "Nbc news";
        nbc.link = "https://www.nbcnews.com/";
        nbc.source_logo = "https://www.nbc.com/generetic/images/nbc_logo_og.jpg";
        world.add(nbc);

        NewsItem forbes = new NewsItem();
        forbes.link = "https://www.forbes.com/";
        forbes.title = "Forbes";
        forbes.source_logo = "https://thumbor.forbes.com/thumbor/filters%3Aformat%28jpg%29/https%3A%2F%2Fi.forbesimg.com%2Fmedia%2Fassets%2Fforbes_1200x1200.jpg";
        world.add(forbes);

        NewsItem ekantipur = new NewsItem();
        ekantipur.link = "https://ekantipur.com/";
        ekantipur.title = "kantipur";
        ekantipur.source_logo = "https://jcss-cdn.kantipurdaily.com/kantipurdaily/images/logo.png";
        nepali.add(ekantipur);


        NewsItem news24 = new NewsItem();
        news24.link = "https://www.news24nepal.tv/";
        news24.title = "New24Nepal";
        news24.source_logo = "https://www.news24nepal.tv/wp-content/themes/news24nepal/img/logo.png";
        nepali.add(news24);

        NewsItem nagariknews = new NewsItem();
        nagariknews.link = "https://nagariknews.nagariknetwork.com/";
        nagariknews.title = "Nagarik News";
        nagariknews.source_logo = "https://myrepublica.nagariknetwork.com/bundles/nagarikfrontend/images/app-download-logo.png";
        nepali.add(nagariknews);

        NewsItem espn = new NewsItem();
        espn.link = "https://www.espn.in/";
        espn.title = "ESPN";
        espn.source_logo = "https://a1.espncdn.com/combiner/i?img=%2Fi%2Fespn%2Fespn_logos%2Fespn_red.png";
        sports.add(espn);

        NewsItem goal = new NewsItem();
        goal.link = "https://www.goal.com/en-in";
        goal.title = "Goal.com";
        goal.source_logo = "https://secure.static.goal.com/280900/280936_gallery.jpg";
        sports.add(goal);


        NewsItem sportsndtv = new NewsItem();
        sportsndtv.link = "https://sports.ndtv.com/";
        sportsndtv.title = "SportsNDTV";
        sportsndtv.source_logo = "https://drop.ndtv.com/homepage/ndtv_sports/images/ndtv_sports1200x630.png";
        sports.add(sportsndtv);


        NewsItem gorkhapatra = new NewsItem();
        gorkhapatra.link = "https://gorkhapatraonline.com/";
        gorkhapatra.title = "Gorkha Patra";
        gorkhapatra.source_logo ="https://gorkhapatraonline.com/logo/5d5e64583543a_final-logo-gopa.png";
        nepali.add(gorkhapatra);

        NewsItem geekynepal = new NewsItem();
        geekynepal.link ="https://geekynepal.com/";
        geekynepal.title = "Geeky Nepal";
        geekynepal.source_logo = "https://geekynepal.com/content/uploads/2016/11/272x90-2.png";
        tech.add(geekynepal);

        NewsItem onlinekhabarnepali = new NewsItem();
        onlinekhabarnepali.title = "OnlineKhabar";
        onlinekhabarnepali.link = "https://www.onlinekhabar.com/";
        onlinekhabarnepali.source_logo = "https://www.onlinekhabar.com/wp-content/themes/onlinekhabar-2018/img/logoMainWht.png";
        nepali.add(onlinekhabarnepali);


        NewsItem setopatineplai = new NewsItem();
        setopatineplai.link = "https://www.setopati.com";
        setopatineplai.title = "Setopati";
        setopatineplai.source_logo = "https://en.setopati.com/images/logo.jpg?v=1.2";
        nepali.add(setopatineplai);


        NewsItem msn = new NewsItem();
        msn.link = "https://www.msn.com/en-us/entertainment";
        msn.title = "MSN News";
        msn.source_logo = "https://img-s-msn-com.akamaized.net/tenant/amp/entityid/BB15XYwL.img?h=206&w=320&m=6&q=60&u=t&o=t&l=f&f=jpg";
        entertainment.add(msn);

        NewsItem mtv = new NewsItem();
        mtv.link = "http://www.mtv.com/news/";
        mtv.title = "MTV";
        mtv.source_logo = "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcS1wC21obSZxagYrAReCsg719pA-IxyHm1zIQ&usqp=CAU";
        entertainment.add(mtv);

        adapter = new SiteAdapter(getContext(), nepal);
        nepalView.setAdapter(adapter);


        nepalView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsItem currentNews = nepal.get(i);
                Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                intent.putExtra("newsitem", currentNews);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });


        adapter = new SiteAdapter(getContext(), world);
        worldView.setAdapter(adapter);

        worldView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsItem currentNews = world.get(i);
                Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                intent.putExtra("newsitem", currentNews);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        adapter = new SiteAdapter(getContext(), nepali);
        nepaliView.setAdapter(adapter);

        nepaliView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsItem currentNews = nepali.get(i);
                Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                intent.putExtra("newsitem", currentNews);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        adapter = new SiteAdapter(getContext(), sports);
        sportView.setAdapter(adapter);

        sportView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsItem currentNews = sports.get(i);
                Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                intent.putExtra("newsitem", currentNews);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        adapter = new SiteAdapter(getContext(), tech);
        techView.setAdapter(adapter);

        techView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsItem currentNews = tech.get(i);
                Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                intent.putExtra("newsitem", currentNews);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        adapter = new SiteAdapter(getContext(), entertainment);
        entertainmentView.setAdapter(adapter);

        entertainmentView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsItem currentNews = entertainment.get(i);
                Intent intent = new Intent(getContext(), NewsDetailActivity.class);
                intent.putExtra("newsitem", currentNews);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        return view;
    }
}