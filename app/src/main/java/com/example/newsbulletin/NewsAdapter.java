package com.example.newsbulletin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.squareup.picasso.Picasso;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.time.*;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;


public class NewsAdapter extends BaseAdapter implements ActivityCompat.OnRequestPermissionsResultCallback {

    final Context context;
    ArrayList<NewsItem> newsList;
    ImageView popupImage;
    SaveNews myDb;
    String localDate;
    String[] date;
    String dataPublished;
    ImageView iv1;
    ImageView iv2;
    WebView webView;
    String name;

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
    public NewsAdapter(Context context, ArrayList<NewsItem> news) {
        this.context = context;
        this.newsList = news;
        localDate = LocalDate.now().toString();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = View.inflate(context, R.layout.news_list_item_layout, null);
        }
        final NewsItem currentNews = newsList.get(i);
        iv1 = view.findViewById(R.id.imageview_1);
        iv2 = view.findViewById(R.id.imageview_2);
        TextView tvTitle = view.findViewById(R.id.textview_1);
        TextView tvDate = view.findViewById(R.id.textview_2);
        TextView tvPublisher = view.findViewById(R.id.textview_3);


        if (!currentNews.imgsrc.equals("")) {
            Picasso.with(context).load(currentNews.imgsrc).placeholder(R.drawable.placeholder).into(iv1);
        }
        Picasso.with(context).load(currentNews.source_logo).placeholder(R.drawable.placeholder).into(iv2);
        tvTitle.setText(currentNews.title);
        tvDate.setText(currentNews.date);
        tvPublisher.setText(currentNews.publisher);


        popupImage = view.findViewById(R.id.popup);
        final View finalView1 = view;
        popupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.share:
                                try {
                                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                    sharingIntent.setType("text/plain");
                                    sharingIntent.putExtra(Intent.EXTRA_TEXT, currentNews.link);
                                    context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
                                } catch (Exception e) {
                                    Toast.makeText(context, "Unable to share", Toast.LENGTH_SHORT).show();
                                }
                                return true;
                            case R.id.save:
                                myDb = new SaveNews(context);
                                try {
                                    if (currentNews.date.contains("ago") || currentNews.date.contains("hours") || currentNews.date.contains("hour") || currentNews.date.contains("mins") || currentNews.date.contains("secs")) {
                                        date = Calendar.getInstance().getTime().toString().split("\\s+");
                                        dataPublished = date[0] + " " + date[1] + " " + date[2];
                                    } else
                                        dataPublished = currentNews.date;


                                    name = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
                                    myDb.insertData(name, currentNews.source_logo, currentNews.title, currentNews.publisher, dataPublished, localDate, currentNews.link);


                                    //check permission
                                    if (checkPermission()) {
                                        //   save image for news offline news content
                                        new downloadImage().execute(currentNews.imgsrc, name);


                                        /* save the web page */
                                        webView = new WebView(context);
                                        final ProgressBar progressBar = finalView1.findViewById(R.id.progressBar);
                                        progressBar.setMax(100);
                                        webView.setVisibility(View.GONE);
                                        webView.setWebViewClient(new WebViewClient());
                                        webView.loadUrl(currentNews.link);

                                        webView.setWebChromeClient(new WebChromeClient() {
                                            @Override
                                            public void onProgressChanged(WebView view, int newProgress) {
                                                super.onProgressChanged(view, newProgress);
                                                progressBar.setProgress(newProgress);
                                                progressBar.setVisibility(View.VISIBLE);
                                                progressBar.setProgress(newProgress);

                                                if (newProgress == 100) {
                                                    progressBar.setVisibility(View.GONE);
                                                    Toast.makeText(context, "News saved " +
                                                            "successfully!", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                        webView.setWebViewClient(new WebViewClient() {
                                            @Override
                                            public void onPageFinished(WebView view, String url) {
                                                super.onPageFinished(view, url);

                                                if (checkPermission()) {
                                                    webView.saveWebArchive(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                                                            + File.separator + name + ".mht");
                                                    webView.setVisibility(View.GONE);
                                                } else {
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                        ActivityCompat.requestPermissions((Activity) context, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 111);
                                                    }
                                                }
                                            }
                                        });
                                    } else {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            ActivityCompat.requestPermissions((Activity) context, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, 111);
                                        }
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(context, "Failed to save!", Toast.LENGTH_SHORT).show();
                                }
                                return true;
                            default:
                        }
                        return false;
                    }
                });
            }

        });

        notifyDataSetChanged();
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private class downloadImage extends AsyncTask<String, Void, Bitmap> {

        String imgUrl;
        String name;

        @Override
        protected Bitmap doInBackground(String... strings) {

            imgUrl = strings[0];           //our image url
            name = strings[1];
            Bitmap bitmap = null;         //our image bitmap
            try {

                //here we fetch image bitmap
                //open url inputStream to read url bitmap
                InputStream inputStream = new java.net.URL(imgUrl).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch (Exception e) {
                e.printStackTrace();
            }

            // return our bitmap
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            //here we call save image  function
            try {
                saveImage(bitmap, name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //create saveImage function
    private void saveImage(Bitmap bitmap, String name) throws IOException {
        OutputStream fos; //file output stream

        //now we check android version is Q or lower
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentResolver resolver = context.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, name + ".jpg");  //setting the image name
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
            contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
            Uri ImageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            fos = resolver.openOutputStream(Objects.requireNonNull(ImageUri));
        } else {
            //for below android Q
            String ImagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            File Image = new File(ImagesDir, name + "jpg");
            fos = new FileOutputStream(Image);
        }

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        assert fos != null;
        fos.close();
    }

    //Now check for permission
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private boolean checkPermission() {
        int write = ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE);
        int read = ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE);
        return write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED;
    }

    //Grant permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
