package com.example.vpmishra.newscientist;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class News_card extends AppCompatActivity implements Callback  {

    ImageView image;
    TextView title,time,des,url;
    RecyclerView recyclerView;
    NewAdapter adap;
    List<String> news_source=new ArrayList<>();
    int index=0;
    ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        news_source.add(index,"google-news");
        setContentView(R.layout.activity_news_card);
        title=(TextView) findViewById(R.id.card_title);
        time=(TextView) findViewById(R.id.card_time);
        des=(TextView) findViewById(R.id.card_des);
        url=(TextView) findViewById(R.id.card_url);
        image=(ImageView) findViewById(R.id.card_image);
        bar= (ProgressBar) findViewById(R.id.bar);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL));
        adap = new NewAdapter(this, news_source);
        adap.setClickListener(new NewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView tv = (TextView) view;
                tv.setTextColor(getResources().getColor(R.color.colorAccent1));
                index=position;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                },2000);
                getData();
            }
        });
        generateList();
        recyclerView.setAdapter(adap);
        bar.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        Log.e("1",bundle.getString("title"));
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#e1c2ad\"News : >" +bundle.getString("newspaper")+"</font>"));
        title.setText(bundle.getString("title"));
        try {
            String s = bundle.getString("time");
            if (s != null) {
                s = s.substring(0, 10);
                time.setText(s);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        url.setText(bundle.getString("url"));
        if (bundle.getString("des").equalsIgnoreCase("null")) des.setText(" ");
         else  des.setText(bundle.getString("des"));
        Glide.with(News_card.this).load(bundle.getString("image")).into(image);
        //scrollView.set
    }

    private void generateList() {
        news_source.add("bbc-news");
        news_source.add("abc-news-au");
        news_source.add("associated-press");
        news_source.add("bild");
        news_source.add("the-hindu");
        news_source.add("the-times-of-india");
        news_source.add("ign");
        news_source.add("breitbart-news");
        news_source.add("cnbc");
        news_source.add("business-insider");
        news_source.add("national-geographic");
        news_source.add("new-scientist");
        news_source.add("engadget");
        news_source.add("hacker-news");
        news_source.add("espn-cric-info");
        news_source.add("bbc-sport");
        news_source.add("talksport");
        news_source.add("entertainment-weekly");
        news_source.add("daily-mail");

    }
    private void getData() {
        bar.setVisibility(View.INVISIBLE);
        String src=news_source.get(index).toUpperCase();
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#e1c2ad\">" +src+"</font>"));
        String url = "https://newsapi.org/v1/articles?source="+news_source.get(index)+"&sortBy=top&apiKey=86865741d11a452e8830d6321992c27b";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(this);
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String s = response.body().string();
        try {
            final JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("articles");
                final JSONObject object = jsonArray.getJSONObject(0);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        title.setText(object.getString("title"));
                        des.setText(object.getString("description"));
                        url.setText(object.getString("url"));
                        Glide.with(News_card.this).load(object.getString("urlToImage")).into(image);
                        String t = object.getString("publishedAt");
                        if (t != null) {
                            t = t.substring(0, 10);
                            time.setText(t);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
