package com.movirec.chris.movirec.listViewAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.movirec.chris.movirec.R;
import com.movirec.chris.movirec.customClasses.ListObject;

import java.util.ArrayList;

public class HomeListAdapter extends BaseAdapter {

    private static final long ID_CONSTANT = 0x010000000;

    private ArrayList<ListObject> listObjects;
    private Context mContext;

    public HomeListAdapter(Context con, ArrayList<ListObject> lists) {
        mContext = con;
        listObjects = lists;
    }

    @Override
    public int getCount() {
        if(listObjects != null) {
            return listObjects.size();
        } else {
            return 0;
        }
    }

    @Override
    public ListObject getItem(int position) {
        if(listObjects != null && position < listObjects.size() && position >= 0) {
            return listObjects.get(position);
        }
        else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return ID_CONSTANT + position;
    }

    static class ViewHolder {
        // Views in your item layouts
        public TextView title;
        public TextView itemCount;

        // Constructor that sets the views up.
        public ViewHolder(View v) {
            title = (TextView) v.findViewById(R.id.listviewhomecell_listtitle);
            itemCount = (TextView) v.findViewById(R.id.listviewhomecell_itemcount);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listviewcell_home, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        ListObject item = getItem(position);

        // Setup view using the ViewHolder
        if (holder != null){
            Log.e("getView: ", item.getListTitle());

            holder.title.setText(item.getListTitle());
            if (item.getListMedia().size() == 1){
                holder.itemCount.setText(item.getListMedia().size() + " Item");
            }
            else {
                holder.itemCount.setText(item.getListMedia().size() + " Items");
            }

        }

        return convertView;
    }
}
