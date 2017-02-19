package com.movirec.chris.movirec;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.movirec.chris.movirec.customClasses.ListObject;
import com.movirec.chris.movirec.customClasses.Media;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListDetailActivity extends AppCompatActivity {

    ListObject listObject;
    ArrayList<Media> mediaList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listObject = (ListObject) getIntent().getSerializableExtra("LIST");
        listView = (ListView) findViewById(R.id.listDetail_list);
        mediaList = listObject.getListMedia();
        sortAddedDate();

        setTitle(listObject.getListTitle());

        Log.e("onCreate: ", String.valueOf(listObject.getListMedia().size()));
        Log.e("onCreate: ", String.valueOf(listObject.getListSize()));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // GO TO MOVIE SEARCH SCREEN
                Intent intent = new Intent(ListDetailActivity.this, SearchMovieActivity.class);
                intent.putExtra("list", listObject);
                startActivityForResult(intent, MainActivity.ADD_CODE);
            }
        });
    }

    private void showListView(ArrayList<Media> media){
        listView.setAdapter(new ListDetailAdapter(this, media));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListDetailActivity.this, "Movie Detail: Not yet implemented", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sortAlpha(){
        if (mediaList.size() > 0) {
            Collections.sort(mediaList, new Comparator<Media>() {
                @Override
                public int compare(Media o1, Media o2) {
                    return o1.getMediaTitle().compareTo(o2.getMediaTitle());
                }
            });
        }
        showListView(mediaList);
    }

    private void sortAddedDate(){
        if (mediaList.size() > 0) {
            Collections.sort(mediaList, new Comparator<Media>() {
                @Override
                public int compare(Media o1, Media o2) {
                    return o1.getDateAdded().compareTo(o2.getDateAdded());
                }
            });
        }
        showListView(mediaList);
    }

    private void sortReleaseDate(){
        if (mediaList.size() > 0) {
            Collections.sort(mediaList, new Comparator<Media>() {
                @Override
                public int compare(Media o1, Media o2) {
                    return o1.getMediaYear().compareTo(o2.getMediaYear());
                }
            });
        }
        showListView(mediaList);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            listObject = (ListObject) data.getSerializableExtra("LIST");
            // refresh LV
            showListView(listObject.getListMedia());
            mediaList = listObject.getListMedia();
            Log.e("onActivityResult: ", String.valueOf(listObject.getListMedia().size()));
            Log.e("onActivityResult: ", String.valueOf(listObject.getListSize()));

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            new AlertDialog.Builder(this)
                    .setTitle("Delete \"" + listObject.getListTitle() + "\"")
                    .setMessage("Are you sure you want to delete this list?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ListStorage listStorage = new ListStorage();
                            listStorage.deleteList(ListDetailActivity.this, listObject);
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
        else if (id == R.id.action_sort_alpha){
            Toast.makeText(this, "Sorted by Name", Toast.LENGTH_SHORT).show();
            sortAlpha();
            return true;
        }
        else if (id == R.id.action_sort_created){
            Toast.makeText(this, "Sorted by Date Added", Toast.LENGTH_SHORT).show();
            sortAddedDate();
            return true;
        }
        else if (id == R.id.action_sort_released){
            Toast.makeText(this, "Sorted by Release Date", Toast.LENGTH_SHORT).show();
            sortReleaseDate();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
