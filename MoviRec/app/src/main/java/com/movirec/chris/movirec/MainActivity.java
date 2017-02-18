package com.movirec.chris.movirec;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.movirec.chris.movirec.customClasses.ListObject;
import com.movirec.chris.movirec.customClasses.Media;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int LIST_CODE = 0x00001;
    public static final int ADD_CODE = 0x00002;

    ListView homeListView;
    ArrayList<ListObject> listObjectArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        homeListView = (ListView) findViewById(R.id.list);

        showListView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });
    }

    private void showListView(){
        ListStorage listStorage =  new ListStorage();
        listStorage.load(this);

        listObjectArrayList = listStorage.getLists();

        homeListView.setAdapter(new HomeListAdapter(this, listStorage.getLists()));
        homeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ListDetailActivity.class);
                intent.putExtra("LIST", listObjectArrayList.get(position));
                startActivityForResult(intent, MainActivity.LIST_CODE);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        showListView();
    }

    private void showAddDialog(){
        LayoutInflater li = LayoutInflater.from(this);
        View addDialogView = li.inflate(R.layout.home_adddialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(addDialogView);

        final EditText userInput = (EditText) addDialogView.findViewById(R.id.AddDialog_ET);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                String title = userInput.getText().toString();

                                ListStorage listStorage =  new ListStorage();
                                listStorage.load(MainActivity.this);

                                ListObject newList = new ListObject(title, new Date(), new ArrayList<Media>());

                                ArrayList<ListObject> allLists = listStorage.getLists();
                                allLists.add(newList);

                                listStorage.save(MainActivity.this, allLists);

                                Toast.makeText(MainActivity.this, "Added List: " + title, Toast.LENGTH_SHORT).show();
                                showListView();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
