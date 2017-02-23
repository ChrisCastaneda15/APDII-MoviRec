package com.movirec.chris.movirec;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.movirec.chris.movirec.customClasses.Media;
import com.movirec.chris.movirec.customClasses.TVEpisode;
import com.movirec.chris.movirec.customClasses.TVShow;
import com.movirec.chris.movirec.customServices.MovieDetailService;
import com.movirec.chris.movirec.customServices.TVDetailService;
import com.movirec.chris.movirec.listViewAdapters.ListDetailAdapter;
import com.movirec.chris.movirec.listViewAdapters.TVEpisodeAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TVEpisodeActivity extends AppCompatActivity {

    public static final String UPDATE_EPISODES = "com.movirec.chris.movirec.UPDATE_EPISODES";

    TVShow show;
    ArrayList<TVEpisode> episodes = new ArrayList<>();
    ArrayList<TVEpisode> individualSeason = new ArrayList<>();
    ArrayList<String> seasons = new ArrayList<>();

    ProgressDialog loading;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvepisode);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setTitle(getIntent().getStringExtra("NAME") + " Episodes");

        loading = new ProgressDialog(this);
        loading.setCancelable(true);
        loading.setMessage("Getting episodes for " + getIntent().getStringExtra("NAME") + "...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.show();

        listView = (ListView) findViewById(R.id.episodeLV);
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(UPDATE_EPISODES);
        registerReceiver(updateEpisodes, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(updateEpisodes);
    }

    private void showListView(final ArrayList<TVEpisode> media){
        listView.setAdapter(new TVEpisodeAdapter(this, media));
    }

    private final BroadcastReceiver updateEpisodes = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            loading.dismiss();

            int s = intent.getIntExtra("SEASONS", 0);

            Log.e("onReceive: ", String.valueOf(s));
            seasons.add("All");
            int m = 0;
            while (m < s) {
                seasons.add("Season " + (m + 1));
                m++;
            }
            Log.e("onReceive: ", String.valueOf(seasons.size()));
            show = (TVShow) intent.getSerializableExtra("EPS");
            episodes = show.getTvEpisodes();
            //Log.e("onReceive: ", String.valueOf(episodes));
            invalidateOptionsMenu();
            showListView(episodes);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.clear();

        getMenuInflater().inflate(R.menu.menu_episodes, menu);

        MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, seasons){
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTextColor(Color.parseColor("#ffffff"));
                return v;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("onItemClick: ", seasons.get(position));
                if (seasons.get(position).equals("All")){
                    showListView(episodes);
                }
                else {
                    individualSeason = new ArrayList<TVEpisode>();
                    for (int i = 0; i < episodes.size(); i++) {
                        if (episodes.get(i).getEpisodeSeason() == position){
                            individualSeason.add(episodes.get(i));
                        }
                        else if (episodes.get(i).getEpisodeSeason() > position){
                            break;
                        }
                    }
                    showListView(individualSeason);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        if (id == R.id.spinner) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
