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
import com.movirec.chris.movirec.customClasses.TVEpisode;

import java.util.ArrayList;

import it.sephiroth.android.library.picasso.Picasso;

public class TVEpisodeAdapter extends BaseAdapter {

    private static final long ID_CONSTANT = 0x010000001;

    private ArrayList<TVEpisode> tvEpisodes;
    private Context mContext;

    public TVEpisodeAdapter(Context con, ArrayList<TVEpisode> lists) {
        mContext = con;
        tvEpisodes = lists;
    }

    @Override
    public int getCount() {
        if(tvEpisodes != null) {
            return tvEpisodes.size();
        } else {
            return 0;
        }
    }

    @Override
    public TVEpisode getItem(int position) {
        if(tvEpisodes != null && position < tvEpisodes.size() && position >= 0) {
            return tvEpisodes.get(position);
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

        // Constructor that sets the views up.
        public ViewHolder(View v) {
            title = (TextView) v.findViewById(R.id.episodeTitle);
            desc = (TextView) v.findViewById(R.id.episodeDesc);
            season = (TextView) v.findViewById(R.id.episodeSeason);
            number = (TextView) v.findViewById(R.id.episodeNumber);
            poster = (ImageView) v.findViewById(R.id.episodeImage);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        convertView = LayoutInflater.from(mContext).inflate(R.layout.listviewcell_episode, parent, false);
        holder = new ViewHolder(convertView);
        convertView.setTag(holder);

        TVEpisode item = getItem(position);

        char[] characters = item.getEpisodeTitle().toCharArray();

        // Setup view using the ViewHolder
        if (holder != null){
            holder.title.setText(item.getEpisodeTitle());
            if (characters.length > 30){
                holder.title.setTextSize(20);
            }
            String descrip = item.getEpisodeSummary();
            descrip = descrip.replace("<p>", "");
            descrip = descrip.replace("</p>", "");
            holder.desc.setText(descrip);
            holder.season.setText("Season " + item.getEpisodeSeason());
            holder.number.setText("Episode " + item.getEpisodeNumber());
            if (item.getEpisodeImageURL().equals("N/A")){
                holder.poster.setVisibility(View.GONE);
            }
            else {
                Picasso.with(mContext).load(item.getEpisodeImageURL()).into(holder.poster);
            }
            convertView.setTag(holder);
        }

        return convertView;
    }
}
