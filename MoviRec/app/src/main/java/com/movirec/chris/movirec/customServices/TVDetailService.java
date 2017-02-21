package com.movirec.chris.movirec.customServices;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.movirec.chris.movirec.TVDetailActivity;
import com.movirec.chris.movirec.customClasses.Media;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Chris on 2/20/17.
 */

public class TVDetailService extends IntentService {

    private Media media;
    private String TMDBAPI = "https://api.themoviedb.org/3/find/tt0318871?api_key=635ac677445153beaaf1944ca96b48f1&language=en-US&external_source=imdb_id";
    private String TMDB_P1 = "https://api.themoviedb.org/3/find/";
    private String TMDB_P2 = "?api_key=635ac677445153beaaf1944ca96b48f1&language=en-US&external_source=imdb_id";

    public TVDetailService() {
        super("TVDetailService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        media = (Media) intent.getSerializableExtra("MEDIA");

        String url = TMDB_P1;
        url += media.getMediaID();
        url += TMDB_P2;

        Log.e("onHandleIntent: ", url);
        parseMovieTMDB(getMovieTMDB(url));
    }

    private String getMovieTMDB(String u){
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

    private void parseMovieTMDB(String data){
        if (!data.equals("")){
            try {
                JSONObject main = new JSONObject(data);
                Log.e("parseMovieOMDB: ", main.toString());
                JSONArray results = main.getJSONArray("tv_results");
                JSONObject result = (JSONObject) results.get(0);

                Intent intent = new Intent(TVDetailActivity.UPDATE_DETAIL_TV);
                intent.putExtra("BACK", "https://image.tmdb.org/t/p/w500" + result.getString("backdrop_path"));
                intent.putExtra("PLOT", result.getString("overview"));
                sendBroadcast(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}