package com.example.newsbulletin;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class OfflineNewsAdapter extends BaseAdapter implements ActivityCompat.OnRequestPermissionsResultCallback{

    Context context;
    ArrayList<NewsItem> newsList;
    ImageView popupImage;
    SaveNews myDb;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public OfflineNewsAdapter(Context context, ArrayList<NewsItem> news) {
        this.context = context;
        this.newsList = news;
        myDb = new SaveNews(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            view = View.inflate(context, R.layout.offline_list_layout, null);
        }
        final NewsItem currentNews = newsList.get(i);

        ImageView iv1 = view.findViewById(R.id.imageview_1);
        TextView tvTitle = view.findViewById(R.id.textview_1);
        TextView tvDate = view.findViewById(R.id.textview_2);
        TextView tvPublisher = view.findViewById(R.id.textview_3);
        popupImage = view.findViewById(R.id.imageview_3);



        //check permission
        if (checkPermission()){
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), currentNews.imgsrc + ".jpg");
            Picasso.with(context).load(file).placeholder(R.drawable.placeholder).into(iv1);
        }

        tvTitle.setText(currentNews.title);
        tvDate.setText(currentNews.date);
        tvPublisher.setText(currentNews.publisher);



        popupImage = view.findViewById(R.id.imageview_3);
        final View finalView = view;
        final View finalView1 = view;
        popupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.inflate(R.menu.offline_popup);
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.share:
                                try {
                                    Intent sharingIntent= new Intent(Intent.ACTION_SEND);
                                    sharingIntent.setType("text/plain");
                                    sharingIntent.putExtra(Intent.EXTRA_TEXT, currentNews.link);
                                    context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
                                }
                                catch (Exception e){
                                    Toast.makeText(context,"Unable to share", Toast.LENGTH_SHORT).show();
                                }
                                return true;
                            case R.id.delete:
                                try{
                                    SaveNews myDb = new SaveNews(context);
                                    myDb.deleteData(currentNews.imgsrc);
                                    File imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), currentNews.imgsrc + ".jpg");
                                    File webFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), currentNews.imgsrc + ".mht");
                                    imageFile.delete();
                                    webFile.delete();

                                    final Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
                                    finalView1.startAnimation(animation);
                                    Handler handle = new Handler();
                                    handle.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            newsList.remove(i);
                                            notifyDataSetChanged();
                                            animation.cancel();
                                        }
                                    },100);

                                    Toast.makeText(context,"News deleted successfully!", Toast.LENGTH_SHORT).show();
                                }
                                catch (Exception e){
                                    Toast.makeText(context,"Failed to delete", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            default:
                        }
                        return false;
                    }
                });
            }

        });


        return view;
    }

    //Now check for permission
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private boolean checkPermission(){
        int write = ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE);
        return write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED;
    }

    //Grant permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int grantResult: grantResults){
            if (grantResult == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
