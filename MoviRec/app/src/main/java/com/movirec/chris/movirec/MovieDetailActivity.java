package com.movirec.chris.movirec;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.movirec.chris.movirec.customClasses.ListObject;
import com.movirec.chris.movirec.customClasses.Media;

import java.util.ArrayList;

import it.sephiroth.android.library.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String UPDATE_DETAIL = "com.movirec.chris.movirec.UPDATE_DETAIL";

    ListObject listObject;
    Media media;
    Boolean isAdded;

    //UI
    ImageView backdrop;
    ImageView poster;
    TextView genre;
    TextView runtime;
    TextView rating;
    TextView director;
    TextView starring;
    TextView imdbRating;
    TextView plot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        listObject = (ListObject) getIntent().getSerializableExtra("list");
        media = (Media) getIntent().getSerializableExtra("MEDIA");
        isAdded = getIntent().getBooleanExtra("fromList", false);

        setupUI(media);

    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(UPDATE_DETAIL);
        registerReceiver(updateDetail, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(updateDetail);
    }

    private final BroadcastReceiver updateDetail = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            backdrop.setVisibility(View.VISIBLE);
            Picasso.with(MovieDetailActivity.this).load(intent.getStringExtra("BACK")).into(backdrop);
            plot.setText(intent.getStringExtra("PLOT"));
            plot.setMovementMethod(new ScrollingMovementMethod());
        }
    };

    private void setupUI(Media m){
        setTitle(m.getMediaTitle());

        backdrop = (ImageView) findViewById(R.id.movie_backdropIV);
        backdrop.setVisibility(View.GONE);

        poster = (ImageView) findViewById(R.id.movie_posterIV);
        Picasso.with(this).load(m.getMediaPoster()).into(poster);

        genre = (TextView) findViewById(R.id.movie_genreTV);
        genre.setText(m.getMediaGenre());

        runtime = (TextView) findViewById(R.id.movie_runtimeTV);
        runtime.setText(m.getMediaRuntime());

        rating = (TextView) findViewById(R.id.movie_ratingTV);
        rating.setText("Rated " + m.getMediaRating());

        director = (TextView) findViewById(R.id.movie_directorTV);
        director.setText(m.getMediaDirector());

        starring = (TextView) findViewById(R.id.movie_starringTV);
        String actors = m.getMediaActors();
        actors = actors.replace(",", ",\n");
        starring.setText(" " + actors);

        imdbRating = (TextView) findViewById(R.id.movie_imdbRatingTV);
        imdbRating.setText(m.getMediaIMDBScore() + "/10");

        plot = (TextView) findViewById(R.id.movie_plotTV);
        plot.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (isAdded){
            getMenuInflater().inflate(R.menu.menu_detail_added, menu);
        }
        else {
            getMenuInflater().inflate(R.menu.menu_detail_add, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_imdb){
            new AlertDialog.Builder(this)
                    .setTitle("View \"" + media.getMediaTitle() + "\" in IMDb")
                    .setMessage("Are you sure you want to view the IMDb page?")
                    .setPositiveButton("Go to IMDb", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.imdb.com/title/" + media.getMediaID() + "/"));
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Stay", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
            return true;
        }

        if (isAdded){
            if (id == R.id.action_delete){
                new AlertDialog.Builder(this)
                        .setTitle("Delete \"" + media.getMediaTitle() + "\"")
                        .setMessage("Are you sure you want to delete this from the list?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ArrayList<Media> newList = listObject.getListMedia();
                                for (int i = 0; i < listObject.getListMedia().size(); i++) {
                                    if (newList.get(i).getMediaID().equals(media.getMediaID())){
                                        newList.remove(i);

                                        ListStorage listStorage = new ListStorage();
                                        listStorage.load(MovieDetailActivity.this);

                                        listObject.setListMedia(newList);
                                        listObject.setListSize(newList.size());
                                        listStorage.updateList(MovieDetailActivity.this, listObject);

                                        Intent intent = new Intent();
                                        intent.putExtra("LIST", listObject);
                                        intent.putExtra("MEDIA", media);
                                        setResult(RESULT_OK, intent);
                                        break;
                                    }
                                }
                                Toast.makeText(MovieDetailActivity.this, "Movie deleted from " + listObject.getListTitle(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                return true;
            }
        }
        else {
            if (id == R.id.action_add){
                ListStorage listStorage = new ListStorage();
                listStorage.load(MovieDetailActivity.this);
                listObject.getListMedia().add(media);
                listObject.setListSize(listObject.getListMedia().size());
                listStorage.updateList(MovieDetailActivity.this, listObject);
                Toast.makeText(this, "Movie added to " + listObject.getListTitle(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                intent.putExtra("LIST", listObject);
                intent.putExtra("MEDIA", media);
                setResult(RESULT_OK, intent);

                finish();
                return true;
            }
        }


        return super.onOptionsItemSelected(item);
    }
}
