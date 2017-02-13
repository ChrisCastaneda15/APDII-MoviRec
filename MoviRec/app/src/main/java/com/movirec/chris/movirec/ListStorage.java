package com.movirec.chris.movirec;


import android.content.Context;
import android.util.Log;

import com.movirec.chris.movirec.customClasses.ListObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ListStorage {
    private ArrayList<ListObject> lists = new ArrayList<>();

    public ArrayList<ListObject> getLists() {
        return lists;
    }

    public void save(Context context, ArrayList<ListObject> data) {
        FileOutputStream fOS;
        try {
            fOS = context.openFileOutput("data.txt", Context.MODE_PRIVATE);
            ObjectOutputStream oOS = new ObjectOutputStream(fOS);
            oOS.writeObject(data);
            oOS.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load(Context context) {
        FileInputStream fIS;
        try {
            fIS = context.openFileInput("data.txt");
            ObjectInputStream oIS = new ObjectInputStream(fIS);
            lists = (ArrayList<ListObject>) oIS.readObject();
            Log.e("LOAD", String.valueOf(lists.size()));
            fIS.close();
            oIS.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
