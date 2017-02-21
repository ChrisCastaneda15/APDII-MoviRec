package com.movirec.chris.movirec.customServices;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.movirec.chris.movirec.TVDetailActivity;
import com.movirec.chris.movirec.TVEpisodeActivity;
import com.movirec.chris.movirec.customClasses.Media;
import com.movirec.chris.movirec.customClasses.TVEpisode;
import com.movirec.chris.movirec.customClasses.TVShow;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TVEpisodeService extends IntentService {

    private Media media;
    private String TVAPI = "http://api.tvmaze.com/singlesearch/shows?q=girls&embed=episodes";
    private String TV_P1 = "http://api.tvmaze.com/singlesearch/shows?q=";
    private String TV_P2 = "&embed=episodes";

    public TVEpisodeService() {
        super("TVEpisodeService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        media = (Media) intent.getSerializableExtra("MEDIA");

        String url = TV_P1;
        String name = media.getMediaTitle();
        name = name.toLowerCase();
        name = name.replace(" ", "%20");
        url += name;
        url += TV_P2;

        Log.e("onHandleIntent: ", url);
        parseShowEpisodes(getShowEpisodes(url));
    }

    private String getShowEpisodes(String u){
        try {
            URL url = new URL(u);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            try (InputStream is = connection.getInputStream()) {
                connection.connect();
                return IOUtils.toString(is);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private void parseShowEpisodes(String data){

        TVShow show = new TVShow(media, "", new ArrayList<TVEpisode>(), "");
        ArrayList<TVEpisode> ep = new ArrayList<>();
        int seasons = 0;

        if (!data.equals("")){
            try {
                JSONObject main = new JSONObject(data);
                //Log.e("parseMovieOMDB: ", main.toString());
                JSONObject embedded = main.getJSONObject("_embedded");
                JSONArray episodes = embedded.getJSONArray("episodes");
                for (int i = 0; i < episodes.length(); i++) {
                    JSONObject e = episodes.getJSONObject(i);
                    TVEpisode episode;
                    if (e.isNull("image")){
                        episode = new TVEpisode(e.getString("name"), e.getInt("season"), e.getInt("number"), "N/A", e.getString("summary"));

                    }
                    else {
                        episode = new TVEpisode(e.getString("name"), e.getInt("season"), e.getInt("number"), e.getJSONObject("image").getString("medium"),
                                e.getString("summary"));
                    }

                    seasons = e.getInt("season");
                    ep.add(episode);
                }

                show.setTvEpisodes(ep);

                Intent intent = new Intent(TVEpisodeActivity.UPDATE_EPISODES);
                intent.putExtra("EPS", show);
                intent.putExtra("SEASONS", seasons);
                sendBroadcast(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
