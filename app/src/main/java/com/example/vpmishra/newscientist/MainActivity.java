package com.example.vpmishra.newscientist;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Quadrant;

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

public class MainActivity extends AppCompatActivity implements Callback {
    CardStackView flingContainer;
    MyAdapter adapter;
    List<Data> l;
    ProgressBar bar;
    int index=0;
    String s;
    RecyclerView recyclerView;
    NewAdapter adap;
    List<String> news_source=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        news_source.add(index,"google-news");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flingContainer = (CardStackView) findViewById(R.id.frame);
        bar= (ProgressBar) findViewById(R.id.progressbar);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL));
        adap = new NewAdapter(this, news_source);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        },2000);
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
                bar.setVisibility(View.VISIBLE);
                getData();
            }
        });
        recyclerView.setAdapter(adap);
        generateList();
        bar.setVisibility(View.VISIBLE);
        flingContainer.setCardEventListener(new CardStackView.CardEventListener() {
            @Override
            public void onCardDragging(float percentX, float percentY) {

            }

            @Override
            public void onCardSwiped(Quadrant quadrant) {
                if (flingContainer.getTopIndex()==adapter.getCount()-1&&index<20) {
                    bar.setVisibility(View.VISIBLE);
                    index++;
                    getData();
                    if (index==19) index=0;
                }
            }

            @Override
            public void onCardReversed() {

            }

            @Override
            public void onCardMovedToOrigin() {
            }

            @Override
            public void onCardClicked(int card_index) {
                Log.e("index",card_index+" "+flingContainer.getTopIndex());
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("articles");
                    JSONObject object = jsonArray.getJSONObject(card_index);
                    Intent intent = new Intent(MainActivity.this, News_card.class);
                    intent.putExtra("newspaper",news_source.get(index));
                    intent.putExtra("title",object.getString("title"));
                    intent.putExtra("time",object.getString("publishedAt"));
                    intent.putExtra("des",object.getString("description"));
                    intent.putExtra("url",object.getString("url"));
                    intent.putExtra("image",object.getString("urlToImage"));
                    startActivity(intent);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        getData();
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
        getSupportActionBar().setTitle(Html.fromHtml("News : <font color=\"#e1c2ad\">" +src+"</font>"));
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
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("articles");
            l = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String title = object.getString("title");
                String des = object.getString("description");
                String news_url = object.getString("url");
                String image_url = object.getString("urlToImage");
                String publishedAt = object.getString("publishedAt");
                Data data = new Data(title, des, news_url, image_url, publishedAt);
                l.add(data);
            }
            adapter = new MyAdapter(MainActivity.this,l, R.layout.item);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    bar.setVisibility(View.INVISIBLE);
                    flingContainer.setAdapter(adapter);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
