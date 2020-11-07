package com.example.cmpt276project.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FlipHistoryManager implements Iterable<FlipHistory> {

    // singleton Support
    private static FlipHistoryManager instance;
    private FlipHistoryManager() {

    }
    public static FlipHistoryManager getInstance(){
        if (instance == null) {
            instance = new FlipHistoryManager();
        }
        return instance;
    }

    public List<FlipHistory> historyList = new ArrayList<>();

    public void add(FlipHistory history){
        historyList.add(history);
    }

    public int size(){
        return historyList.size();
    }


    @NonNull
    @Override
    public Iterator<FlipHistory> iterator() {
        return null;
    }
}
