package com.example.vpmishra.newscientist;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
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

    ImageView image,left_image,right_image;
    TextView title,time,des,url;
    RecyclerView recyclerView;
    NewAdapter adap;
    List<String> news_source=new ArrayList<>();
    int index=0;
    ProgressBar bar;
    RelativeLayout rl;
    int pos,max;
    String json,news,s;
    Toast toast;
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
        left_image=(ImageView) findViewById(R.id.left_image);
        right_image=(ImageView) findViewById(R.id.right_image);
        bar= (ProgressBar) findViewById(R.id.bar);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        rl= (RelativeLayout) findViewById(R.id.relativelayout);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL));
        adap = new NewAdapter(this, news_source);

        OnSwipeTouchListener onSwipeTouchListener = new OnSwipeTouchListener(News_card.this) {
            @Override
            public void onSwipeLeft() {
                //your actions
                if (pos>=1) {
                    right_image.setImageResource(R.drawable.right);
                try {
                    final JSONObject jsonObject = new JSONObject(json);
                    JSONArray jsonArray = jsonObject.getJSONArray("articles");
                    pos = pos - 1;
                    Log.e("pos", "" + pos);
                    final JSONObject object = jsonArray.getJSONObject(pos);
                    getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#e1c2ad\"News : >" + news + "</font>"));
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
                    if (pos==0)left_image.setImageDrawable(null);
            }
            else {
                    left_image.setImageDrawable(null);
                    Toast.makeText(News_card.this, "News Feed empty", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onSwipeRight() {
                //your actions
                if (pos<=max-2&&pos>=0) {
                    left_image.setImageResource(R.drawable.left);
                    try {
                        final JSONObject jsonObject = new JSONObject(json);
                        JSONArray jsonArray = jsonObject.getJSONArray("articles");
                        pos = pos + 1;
                        Log.e("pos", "" + pos);
                        final JSONObject object = jsonArray.getJSONObject(pos);
                        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#e1c2ad\"News : >" + news + "</font>"));
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
                if (pos==max-1)right_image.setImageDrawable(null);
                else {
                    right_image.setImageDrawable(null);
                    Toast.makeText(News_card.this, "Last Feed", Toast.LENGTH_SHORT).show();
                }
            }
        };
        rl.setOnTouchListener(onSwipeTouchListener);
        left_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos>=1) {
                    right_image.setImageResource(R.drawable.right);
                    try {
                        final JSONObject jsonObject = new JSONObject(json);
                        JSONArray jsonArray = jsonObject.getJSONArray("articles");
                        pos = pos - 1;
                        Log.e("pos", "" + pos);
                        final JSONObject object = jsonArray.getJSONObject(pos);
                        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#e1c2ad\"News : >" + news + "</font>"));
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
                    if (pos==0)left_image.setImageDrawable(null);
                }
                else {
                    left_image.setImageDrawable(null);
                    Toast.makeText(News_card.this, "News Feed empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
        right_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos<=max-2&&pos>=0) {
                    left_image.setImageResource(R.drawable.left);
                    try {
                        final JSONObject jsonObject = new JSONObject(json);
                        JSONArray jsonArray = jsonObject.getJSONArray("articles");
                        pos = pos + 1;
                        Log.e("pos", "" + pos);
                        final JSONObject object = jsonArray.getJSONObject(pos);
                        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#e1c2ad\"News : >" + news + "</font>"));
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
                    if (pos==max-1)right_image.setImageDrawable(null);
                }
                else {
                    right_image.setImageDrawable(null);
                    Toast.makeText(News_card.this, "Last Feed", Toast.LENGTH_SHORT).show();
                }

            }
        });
        adap.setClickListener(new NewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TextView tv = (TextView) view;
                //tv.setTextColor(getResources().getColor(R.color.colorAccent1));
                index=position;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                },2000);
                left_image.setImageDrawable(null);
                getData();
            }
        });
        generateList();
        recyclerView.setAdapter(adap);
        //bar.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getExtras();
        Log.e("1",bundle.getString("title"));
        pos= Integer.parseInt(bundle.getString("position"));
        max= Integer.parseInt(bundle.getString("max"));
        Log.e("pos", "" + pos);
        if (pos==0) left_image.setImageDrawable(null);
        if (pos==9) right_image.setImageDrawable(null);
        json=bundle.getString("json");
        news=bundle.getString("news").toUpperCase();
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#e1c2ad\">" +news+"</font>"));
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
        bar.setVisibility(View.VISIBLE);
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
        s = response.body().string();
        try {
            final JSONObject jsonObject = new JSONObject(s);
            final JSONArray jsonArray = jsonObject.getJSONArray("articles");
                final JSONObject object = jsonArray.getJSONObject(0);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    json=s;
                    pos=0;
                    max=jsonArray.length();
                    try {
                        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#e1c2ad\"News : >" + jsonObject.getString("source").toUpperCase() + "</font>"));
                        title.setText(object.getString("title"));
                        des.setText(object.getString("description"));
                        url.setText(object.getString("url"));
                        Glide.with(News_card.this).load(object.getString("urlToImage")).into(image);
                        String t = object.getString("publishedAt");
                        if (t != null) {
                            t = t.substring(0, 10);
                            time.setText(t);
                        }
                        bar.setVisibility(View.INVISIBLE);
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
