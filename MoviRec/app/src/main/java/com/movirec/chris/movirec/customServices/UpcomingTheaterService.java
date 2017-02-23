package com.movirec.chris.movirec.customServices;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.movirec.chris.movirec.SearchMovieActivity;
import com.movirec.chris.movirec.TVEpisodeActivity;
import com.movirec.chris.movirec.customClasses.Movie;
import com.movirec.chris.movirec.customClasses.TVEpisode;
import com.movirec.chris.movirec.customClasses.TVShow;
import com.movirec.chris.movirec.customClasses.UpcomingReleasedObject;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class UpcomingTheaterService extends IntentService {

    private Boolean upcoming;
    private String UPCOMING_API = "https://api.themoviedb.org/3/movie/upcoming?api_key=635ac677445153beaaf1944ca96b48f1&language=en-US&page=1&region=US";
    private String RELEASED_API = "https://api.themoviedb.org/3/movie/now_playing?api_key=635ac677445153beaaf1944ca96b48f1&language=en-US&page=1&region=US";

    public UpcomingTheaterService() {
        super("UpcomingTheaterService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        upcoming = intent.getBooleanExtra("UP", false);

        if (upcoming){
            parseUpcoming(getUpcoming(UPCOMING_API));
        }
        else {
            parseReleased(getReleased(RELEASED_API));
        }


    }

    private String getReleased(String u){
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

    private void parseReleased(String data){
        UpcomingReleasedObject rel;

        if (!data.equals("")){
            try {
                JSONObject main = new JSONObject(data);
                Log.e("parseReleased: ", main.toString());

                JSONArray results = main.getJSONArray("results");
                ArrayList<Movie> movies = new ArrayList<>();
                for (int i = 0; i < results.length(); i++) {
                    //Log.e("parseUpcoming: ", String.valueOf(results.get(i)));
                    JSONObject result = (JSONObject) results.get(i);
                    String p = "N/A", b = "N/A";

                    if (result.isNull("poster_path")){
                        Log.e("parseUpcoming: ", result.getString("title") + " Poster NOT THERE");
                    }
                    else {
                        p = "https://image.tmdb.org/t/p/w500" + result.getString("poster_path");
                    }

                    if (result.isNull("backdrop_path")){
                        Log.e("parseUpcoming: ", result.getString("title") + " Backdrop NOT THERE");
                    }
                    else {
                        b = "https://image.tmdb.org/t/p/w500" + result.getString("backdrop_path");
                    }

                    movies.add(new Movie(
                            result.getString("title"),
                            result.getString("overview"),
                            p,
                            result.getString("release_date"),
                            b
                    ));
                }

                rel = new UpcomingReleasedObject("Released", movies);

                Intent intent = new Intent(SearchMovieActivity.UPDATE_UP_REL);
                intent.putExtra("UPREL", rel);
                sendBroadcast(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getUpcoming(String u){
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

    private void parseUpcoming(String data){
        UpcomingReleasedObject up;

        if (!data.equals("")){
            try {
                JSONObject main = new JSONObject(data);
                //Log.e("parseUpcoming: ", main.toString());
                JSONArray results = main.getJSONArray("results");
                ArrayList<Movie> movies = new ArrayList<>();
                for (int i = 0; i < results.length(); i++) {
                    //Log.e("parseUpcoming: ", String.valueOf(results.get(i)));
                    JSONObject result = (JSONObject) results.get(i);
                    String p = "N/A", b = "N/A";

                    if (result.isNull("poster_path")){
                        Log.e("parseUpcoming: ", result.getString("title") + " Poster NOT THERE");
                    }
                    else {
                        p = "https://image.tmdb.org/t/p/w500" + result.getString("poster_path");
                    }

                    if (result.isNull("backdrop_path")){
                        Log.e("parseUpcoming: ", result.getString("title") + " Backdrop NOT THERE");
                    }
                    else {
                        b = "https://image.tmdb.org/t/p/w500" + result.getString("backdrop_path");
                    }

                    movies.add(new Movie(
                            result.getString("title"),
                            result.getString("overview"),
                            p,
                            result.getString("release_date"),
                            b
                    ));
                }

                up = new UpcomingReleasedObject("Upcoming", movies);

                Intent intent = new Intent(SearchMovieActivity.UPDATE_UP_REL);
                intent.putExtra("UPREL", up);
                sendBroadcast(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
