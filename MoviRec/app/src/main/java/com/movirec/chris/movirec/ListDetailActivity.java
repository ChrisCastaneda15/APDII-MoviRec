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
import android.widget.ListView;

import com.movirec.chris.movirec.customClasses.ListObject;

import java.util.ArrayList;
import java.util.List;

public class ListDetailActivity extends AppCompatActivity {

    ListObject listObject;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listObject = (ListObject) getIntent().getSerializableExtra("LIST");

        setTitle(listObject.getListTitle());

        Log.e("onCreate: ", listObject.toString());

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

        return super.onOptionsItemSelected(item);
    }

}
