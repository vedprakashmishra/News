package com.example.vpmishra.newscientist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by vpmishra on 29-08-2017.
 */

public class MyAdapter  extends ArrayAdapter {
    public List<Data> list;
    public static ViewHolder viewHolder;
    private int itemLayout;

    public MyAdapter(Context context,List<Data> list, int itemLayout) {
        super(context, itemLayout, list);
        this.list = list;
        this.itemLayout=itemLayout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView=convertView;
        if (rowView == null) {
            rowView = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
            // configure view holder
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) rowView.findViewById(R.id.news_title);
            viewHolder.time = (TextView) rowView.findViewById(R.id.news_time);
            viewHolder.image = (ImageView) rowView.findViewById(R.id.image);

            rowView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(list.get(position).getTitle() + "");
        try {
            String s = list.get(position).getTime() + "";
            if (s != null) {
                s = s.substring(0, 10);
                viewHolder.time.setText(s);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        Glide.with(parent.getContext()).load(list.get(position).getImage()).into(viewHolder.image);
        return rowView;
    }
    public static class ViewHolder {
        public TextView title,time;
        ImageView image;
    }

}
