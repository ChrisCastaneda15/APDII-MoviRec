package com.movirec.chris.movirec;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.movirec.chris.movirec.customClasses.Media;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MovieService extends IntentService {

    private String movieName;
    private String OMDBAPI = "http://www.omdbapi.com/?t=The+Godfather&y=1972&plot=short&r=json";
    private String OMDB_P1 = "http://www.omdbapi.com/?t=";
    private String OMDB_P2 = "&y=";
    private String OMDB_P3 = "&plot=short&r=json";

    public MovieService() {
        super("MovieService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        movieName = intent.getStringExtra("MOVIENAME");
        movieName = movieName.toLowerCase();
        movieName = movieName.replace(" ", "+");

        String url = OMDB_P1;
        url += movieName + OMDB_P2;

        if (intent.hasExtra("YEAR")){
            url += intent.getStringExtra("YEAR");
        }

        url += OMDB_P3;
        parseMovieOMDB(getMovieOMDB(url));
    }

    private String getMovieOMDB(String u){
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

    private void parseMovieOMDB(String data){
        if (!data.equals("")){
            Boolean isShow = false;

            try {
                JSONObject main = new JSONObject(data);
                Log.e("parseMovieOMDB: ", main.toString());

                if (main.has("Error")){
                    Toast.makeText(this, "Movie/Show Not Found", Toast.LENGTH_SHORT).show();
                }

                if (main.getString("Type").equals("series")){
                    isShow = true;
                }

                Media newMedia = new Media(
                        isShow,
                        main.getString("imdbID"),
                        main.getString("Title"),
                        main.getString("Year"),
                        main.getString("Rated"),
                        main.getString("Released"),
                        main.getString("Runtime"),
                        main.getString("Genre"),
                        main.getString("Director"),
                        main.getString("Actors"),
                        main.getString("Plot"),
                        main.getString("Poster"),
                        main.getString("imdbRating")
                );

                Intent intent = new Intent(SearchMovieActivity.UPDATE_QUICK_LOOK);
                intent.putExtra("MOVIE", newMedia);
                sendBroadcast(intent);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Movie/Show Not Found", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
