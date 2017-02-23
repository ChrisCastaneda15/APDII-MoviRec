package com.movirec.chris.movirec.listViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.movirec.chris.movirec.R;
import com.movirec.chris.movirec.customClasses.Movie;
import com.movirec.chris.movirec.customClasses.TVEpisode;

import java.util.ArrayList;

import it.sephiroth.android.library.picasso.Picasso;

public class upcomingReleasedAdapter extends BaseAdapter {

    private static final long ID_CONSTANT = 0x010000001;

    private ArrayList<Movie> movies;
    private Context mContext;

    public upcomingReleasedAdapter(Context con, ArrayList<Movie> lists) {
        mContext = con;
        movies = lists;
    }

    @Override
    public int getCount() {
        if(movies != null) {
            return movies.size();
        } else {
            return 0;
        }
    }

    @Override
    public Movie getItem(int position) {
        if(movies != null && position < movies.size() && position >= 0) {
            return movies.get(position);
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
        public TextView desc;
        public TextView season;
        public TextView number;
        public ImageView poster;
        public ImageView backdrop;

        // Constructor that sets the views up.
        public ViewHolder(View v) {
            title = (TextView) v.findViewById(R.id.episodeTitle);
            desc = (TextView) v.findViewById(R.id.episodeDesc);
            season = (TextView) v.findViewById(R.id.episodeSeason);
            number = (TextView) v.findViewById(R.id.episodeNumber);
            poster = (ImageView) v.findViewById(R.id.posterImageView);
            backdrop = (ImageView) v.findViewById(R.id.episodeImage);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_uprel, parent, false);
        holder = new ViewHolder(convertView);
        convertView.setTag(holder);

        Movie item = getItem(position);

        char[] characters = item.getTitle().toCharArray();

        // Setup view using the ViewHolder
        if (holder != null){
            holder.title.setText(item.getTitle());
            if (characters.length > 30){
                holder.title.setTextSize(20);
            }
            holder.desc.setText(item.getPlot());
            holder.season.setText("Released on ");
            holder.number.setText(item.getDate());
            if (item.getPoster().equals("N/A")){
                holder.poster.setVisibility(View.GONE);
            }
            else {
                Picasso.with(mContext).load(item.getPoster()).into(holder.poster);
            }
            if (item.getBackDrop().equals("N/A")){
                holder.backdrop.setVisibility(View.GONE);
            }
            else {
                Picasso.with(mContext).load(item.getPoster()).into(holder.backdrop);
            }
            convertView.setTag(holder);
        }

        return convertView;
    }
}
