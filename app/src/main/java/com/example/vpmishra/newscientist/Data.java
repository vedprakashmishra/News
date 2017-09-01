package com.example.vpmishra.newscientist;

import android.util.Log;

/**
 * Created by vpmishra on 29-08-2017.
 */

public class Data {
    String title, des, url, image, time;
    public Data(String title,String des,String url,String image,String time) {
        this.title=title;
        this.des=des;
        this.url=url;
        this.image=image;
        this.time=time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
