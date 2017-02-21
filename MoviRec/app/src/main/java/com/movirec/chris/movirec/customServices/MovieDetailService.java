package com.movirec.chris.movirec.customServices;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.movirec.chris.movirec.MovieDetailActivity;
import com.movirec.chris.movirec.customClasses.Media;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class MovieDetailService extends IntentService {

    private Media media;
    private String TMDBAPI = "https://api.themoviedb.org/3/movie/tt0068646?api_key=635ac677445153beaaf1944ca96b48f1&language=en-US";
    private String TMDB_P1 = "https://api.themoviedb.org/3/movie/";
    private String TMDB_P2 = "?api_key=635ac677445153beaaf1944ca96b48f1&language=en-US";

    public MovieDetailService() {
        super("MovieDetailService");
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

                Intent intent = new Intent(MovieDetailActivity.UPDATE_DETAIL);
                intent.putExtra("BACK", "https://image.tmdb.org/t/p/w500" + main.getString("backdrop_path"));
                intent.putExtra("PLOT", main.getString("overview"));
                sendBroadcast(intent);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Movie/Show Not Found", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
