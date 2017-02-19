package com.movirec.chris.movirec;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.movirec.chris.movirec.customClasses.ListObject;
import com.movirec.chris.movirec.customClasses.Media;

import it.sephiroth.android.library.picasso.Picasso;

import static android.view.View.GONE;

public class SearchMovieActivity extends AppCompatActivity {

    public static final String UPDATE_QUICK_LOOK = "com.movirec.chris.movirec.UPDATE_QUICK_LOOK";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(updateQuickLook);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("LIST", listObject);
        setResult(RESULT_OK, intent);
        finish();
    }

    private final BroadcastReceiver updateQuickLook = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("onReceive: ", String.valueOf(intent.hasExtra("MOVIE")));
            if (intent.hasExtra("MOVIE")){
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

            }

            loading.dismiss();
        }
    };

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

    }

    private void showQuickLook(final Media media){
        movieNameET.setText("");
        yearET.setText("");

        movieTitleTV.setVisibility(View.VISIBLE);
        movieTitleTV.setText(media.getMediaTitle());

        movieDescTV.setVisibility(View.VISIBLE);
        movieDescTV.setText(media.getMediaPlot() + "\nReleased on " + media.getMediaReleased());
        movieDescTV.setMovementMethod(new ScrollingMovementMethod());

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
            }
        });
        moreInfoButton.setVisibility(View.VISIBLE);
        moreInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchMovieActivity.this, "SHOW DETAIL: Not yet implemented", Toast.LENGTH_SHORT).show();
            }
        });

        moviePosterIV.setVisibility(View.VISIBLE);
        Picasso.with(this).load(media.getMediaPoster()).into(moviePosterIV);
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
