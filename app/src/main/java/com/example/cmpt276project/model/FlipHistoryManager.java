package com.example.cmpt276project.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cmpt276project.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// Class that contains a List of FlipHistory class objects
// Used in the FlipCoinHistoryActivity
// Receives input data from FlipCoinActivity
public class FlipHistoryManager implements Iterable<FlipHistory> {

    private static FlipHistoryManager instance;
    public FlipHistoryManager() {
    }
    public static FlipHistoryManager getInstance(){
        if (instance == null) {
            instance = new FlipHistoryManager();
        }
        return instance;
    }

    private List<FlipHistory> myHistory = new ArrayList<>();


    public void addHistory(String Time, String ChildName, String result, boolean WinOrLoss, String profile){
        if(WinOrLoss)
            myHistory.add(new FlipHistory(Time, "ChildName: " + ChildName, "choose " + result, R.drawable.win, profile));
        else
            myHistory.add(new FlipHistory(Time, "ChildName: " + ChildName, "choose " + result, R.drawable.loss, profile));
    }

    public List<FlipHistory> getMyHistory() {
        return myHistory;
    }

    public void setMyHistory(List<FlipHistory> myHistory) {
        this.myHistory = myHistory;
    }

    @NonNull
    @Override
    public Iterator<FlipHistory> iterator() {
        return null;
    }
}
