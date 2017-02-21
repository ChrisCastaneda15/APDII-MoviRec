package com.movirec.chris.movirec.listViewAdapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.movirec.chris.movirec.R;
import com.movirec.chris.movirec.customClasses.Media;

import java.util.ArrayList;

import it.sephiroth.android.library.picasso.Picasso;

public class ListDetailAdapter extends BaseAdapter {

    private static final long ID_CONSTANT = 0x010000001;

    private ArrayList<Media> listObjects;
    private Context mContext;

    public ListDetailAdapter(Context con, ArrayList<Media> lists) {
        mContext = con;
        listObjects = lists;
    }

    @Override
    public int getCount() {
        if(listObjects != null) {
            return listObjects.size();
        } else {
            return 0;
        }
    }

    @Override
    public Media getItem(int position) {
        if(listObjects != null && position < listObjects.size() && position >= 0) {
            return listObjects.get(position);
        }
        else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return ID_CONSTANT + position;
    }

    static class ViewHolder {
        // Views in your item layouts
        public TextView title;
        public TextView genreYear;
        public ImageView poster;

        // Constructor that sets the views up.
        public ViewHolder(View v) {
            title = (TextView) v.findViewById(R.id.listDetailTitle);
            genreYear = (TextView) v.findViewById(R.id.listDetailGenreYear);
            poster = (ImageView) v.findViewById(R.id.listDetailPoster);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        convertView = LayoutInflater.from(mContext).inflate(R.layout.listviewcell_listdetail, parent, false);
        holder = new ViewHolder(convertView);
        convertView.setTag(holder);

        Media item = getItem(position);

        char[] characters = item.getMediaTitle().toCharArray();

        // Setup view using the ViewHolder
        if (holder != null){
            holder.title.setText(item.getMediaTitle());
            if (characters.length > 30){
                holder.title.setTextSize(20);
            }
            holder.genreYear.setText(item.getMediaGenre() + " - " + item.getMediaYear());
            Picasso.with(mContext).load(item.getMediaPoster()).into(holder.poster);
            convertView.setTag(holder);
        }

        return convertView;
    }
}
