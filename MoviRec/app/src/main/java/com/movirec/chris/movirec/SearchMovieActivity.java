package com.movirec.chris.movirec;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.movirec.chris.movirec.customClasses.ListObject;
import com.movirec.chris.movirec.customClasses.Media;
import com.movirec.chris.movirec.customClasses.Movie;
import com.movirec.chris.movirec.customClasses.UpcomingReleasedObject;
import com.movirec.chris.movirec.customServices.MovieDetailService;
import com.movirec.chris.movirec.customServices.MovieService;
import com.movirec.chris.movirec.customServices.TVDetailService;
import com.movirec.chris.movirec.customServices.UpcomingTheaterService;
import com.movirec.chris.movirec.listViewAdapters.upcomingReleasedAdapter;

import java.util.ArrayList;

import it.sephiroth.android.library.picasso.Picasso;

import static android.view.View.GONE;

public class SearchMovieActivity extends AppCompatActivity {

    public static final String UPDATE_QUICK_LOOK = "com.movirec.chris.movirec.UPDATE_QUICK_LOOK";
    public static final String UPDATE_UP_REL = "com.movirec.chris.movirec.UPDATE_UP_REL";

    ListObject listObject;

    ProgressDialog loading;

    //UI - Left to Right, Top to Bottom
    EditText movieNameET;
    EditText yearET;
    Button searchButton;
    TextView movieTitleTV;
    TextView movieDescTV;
    Button addMovieButton;
    Button moreInfoButton;
    ImageView moviePosterIV;
    ListView upcomingAndInTheaterLV;
    Button upcomingButton;
    Button releasedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setTitle("Search");

        setContentView(R.layout.activity_search_movie);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        listObject = (ListObject) getIntent().getSerializableExtra("list");
        setUpUI();
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(UPDATE_QUICK_LOOK);
        registerReceiver(updateQuickLook, filter);

        filter = new IntentFilter(UPDATE_UP_REL);
        registerReceiver(updateUpRel, filter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            listObject = (ListObject) data.getSerializableExtra("LIST");
            showQuickLook((Media) data.getSerializableExtra("MEDIA"));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(updateQuickLook);
        unregisterReceiver(updateUpRel);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("LIST", listObject);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent();
            intent.putExtra("LIST", listObject);
            setResult(RESULT_OK, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private final BroadcastReceiver updateQuickLook = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("onReceive: ", String.valueOf(intent.hasExtra("MOVIE")));
            if (intent.hasExtra("MOVIE")){
                upcomingAndInTheaterLV.setVisibility(GONE);
                showQuickLook((Media) intent.getSerializableExtra("MOVIE"));
            }
            else {
                new AlertDialog.Builder(context)
                        .setTitle("Movie not Found!")
                        .setMessage("Sorry but \"" + intent.getStringExtra("N/A") + "\" was not found.\nPlease try again.")
                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                movieNameET.setText("");
                                yearET.setText("");
                            }
                        })
                        .show();
                hideQuickLook();
            }

            loading.dismiss();
        }
    };

    private final BroadcastReceiver updateUpRel = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            UpcomingReleasedObject object = (UpcomingReleasedObject) intent.getSerializableExtra("UPREL");
            showUpRelLV(object.getMovies());
        }
    };

    private void showUpRelLV(final ArrayList<Movie> movies){
        upcomingAndInTheaterLV.setVisibility(View.VISIBLE);
        upcomingAndInTheaterLV.setAdapter(new upcomingReleasedAdapter(this, movies));
        upcomingAndInTheaterLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                movieNameET.setText(movies.get(position).getTitle());
                char[] characters = movies.get(position).getDate().toCharArray();
                String y = String.valueOf(characters[0]) + String.valueOf(characters[1]) + String.valueOf(characters[2]) + String.valueOf(characters[3]);
                Log.e("onItemClick: ", String.valueOf(characters[0]));
                yearET.setText(y);
                searchButton.performClick();
            }
        });

    }

    private void setUpUI() {
        // Initial Search Portion
        movieNameET = (EditText) findViewById(R.id.MovieNameET);
        yearET = (EditText) findViewById(R.id.YearET);
        searchButton = (Button) findViewById(R.id.SearchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Search API
                searchMovie();
            }
        });

        // Movie Quick Look
        movieTitleTV = (TextView) findViewById(R.id.MovieTitleTV);
        movieTitleTV.setVisibility(GONE);

        movieDescTV = (TextView) findViewById(R.id.MovieDescTV);
        movieDescTV.setVisibility(GONE);

        addMovieButton = (Button) findViewById(R.id.AddMovieButton);
        addMovieButton.setVisibility(GONE);

        moreInfoButton = (Button) findViewById(R.id.DetailsButton);
        moreInfoButton.setVisibility(GONE);

        moviePosterIV = (ImageView) findViewById(R.id.posterIV);
        moviePosterIV.setVisibility(GONE);

        // Additional Search Options
        upcomingAndInTheaterLV = (ListView) findViewById(R.id.SearchLV);

        upcomingButton = (Button) findViewById(R.id.upcomingButton);
        upcomingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideQuickLook();
                Intent service = new Intent(SearchMovieActivity.this, UpcomingTheaterService.class);
                service.putExtra("UP", true);
                startService(service);
            }
        });
        releasedButton = (Button) findViewById(R.id.releasedButton);
        releasedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideQuickLook();
                Intent service = new Intent(SearchMovieActivity.this, UpcomingTheaterService.class);
                service.putExtra("UP", false);
                startService(service);
            }
        });

    }

    private void showQuickLook(final Media media){
        movieNameET.setText("");
        yearET.setText("");

        movieTitleTV.setVisibility(View.VISIBLE);
        movieTitleTV.setText(media.getMediaTitle());

        movieDescTV.setVisibility(View.VISIBLE);
        movieDescTV.setText(media.getMediaPlot() + "\nReleased on " + media.getMediaReleased());
        movieDescTV.setMovementMethod(new ScrollingMovementMethod());

        for (int i = 0; i < listObject.getListMedia().size(); i++) {
            if (listObject.getListMedia().get(i).getMediaID().equals(media.getMediaID())){
                addMovieButton.setText("ADDED");
                addMovieButton.setEnabled(false);
                break;
            }
            else {
                addMovieButton.setText("ADD");
                addMovieButton.setEnabled(true);
            }
        }

        addMovieButton.setVisibility(View.VISIBLE);
        addMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchMovieActivity.this, "Added to " + listObject.getListTitle(), Toast.LENGTH_SHORT).show();
                listObject.getListMedia().add(media);
                listObject.setListSize(listObject.getListMedia().size());
                ListStorage listStorage = new ListStorage();
                listStorage.load(SearchMovieActivity.this);
                listStorage.updateList(SearchMovieActivity.this, listObject);
                addMovieButton.setText("ADDED");
                addMovieButton.setEnabled(false);
            }
        });
        moreInfoButton.setVisibility(View.VISIBLE);
        moreInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (media.getShow()){
                    //Start Service
                    Intent service = new Intent(SearchMovieActivity.this, TVDetailService.class);
                    service.putExtra("MEDIA", media);
                    startService(service);
                    //Start Activity
                    Intent intent = new Intent(SearchMovieActivity.this, TVDetailActivity.class);
                    intent.putExtra("list", listObject);
                    intent.putExtra("MEDIA", media);
                    if (addMovieButton.getText().toString().equals("ADDED")){
                        intent.putExtra("fromList", true);
                    }
                    else {
                        intent.putExtra("fromList", false);
                    }

                    startActivityForResult(intent, MainActivity.DETAIL_CODE);
                }
                else {
                    //Start Service
                    Intent service = new Intent(SearchMovieActivity.this, MovieDetailService.class);
                    service.putExtra("MEDIA", media);
                    startService(service);
                    //Start Activity
                    Intent intent = new Intent(SearchMovieActivity.this, MovieDetailActivity.class);
                    intent.putExtra("list", listObject);
                    intent.putExtra("MEDIA", media);
                    if (addMovieButton.getText().toString().equals("ADDED")){
                        intent.putExtra("fromList", true);
                    }
                    else {
                        intent.putExtra("fromList", false);
                    }
                    startActivityForResult(intent, MainActivity.DETAIL_CODE);
                }
            }
        });

        moviePosterIV.setVisibility(View.VISIBLE);
        Picasso.with(this).load(media.getMediaPoster()).into(moviePosterIV);
    }

    private void hideQuickLook(){
        movieTitleTV.setVisibility(GONE);
        movieDescTV.setVisibility(GONE);
        addMovieButton.setVisibility(GONE);
        moreInfoButton.setVisibility(GONE);
        moviePosterIV.setVisibility(GONE);
    }

    private void searchMovie(){
        if (movieNameET.getText().toString().trim().length() == 0){
            new AlertDialog.Builder(this)
                    .setTitle("Incomplete Field!")
                    .setMessage("Please fill out all fields")
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
        else {
            Toast.makeText(this, "Searching!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MovieService.class);
            intent.putExtra("MOVIENAME", movieNameET.getText().toString().trim());
            if (yearET.getText().toString().trim().length() == 4){
                intent.putExtra("YEAR", yearET.getText().toString().trim());
            }
            startService(intent);

            RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.activity_search_movie);
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);

            loading = new ProgressDialog(this);
            loading.setCancelable(true);
            loading.setMessage("Searching for Movie/Show...");
            loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            loading.show();
        }
    }
}
