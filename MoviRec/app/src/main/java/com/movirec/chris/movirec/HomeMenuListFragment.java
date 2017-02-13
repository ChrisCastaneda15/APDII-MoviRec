package com.movirec.chris.movirec;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.movirec.chris.movirec.customClasses.ListObject;

import java.util.ArrayList;

public class HomeMenuListFragment extends ListFragment {
    public static final String TAG = "HomeMenuListFragment";

    static ArrayList<ListObject> list;

    public static HomeMenuListFragment newInstanceOf(ArrayList<ListObject> l){
        HomeMenuListFragment fragment = new HomeMenuListFragment();
        list = l;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.listviewcell_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setAdapter(new HomeListAdapter(getActivity(), list));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getContext(), "Not Yet Implemented (W2)", Toast.LENGTH_SHORT).show();

    }
}
