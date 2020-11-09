package com.example.cmpt276project.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FlipHistoryManager implements Iterable<FlipHistory> {

    public static final String HISTORY_PREFS = "SharedPreferences for History class";
    public static final String HISTORY_INDEX = "History index";
    public static final String NUMBER_OF_HISTORY = "Number of history";

    private int numHistory;



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


    // list of history
    public List<String> historyList = new ArrayList<String>();

    public void addHistory(String history){
        historyList.add(history);
        numHistory ++;
    }

    public int size(){
        return historyList.size();
    }

    // Save the list of Flip history
    public void saveHistory(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(HISTORY_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(NUMBER_OF_HISTORY, numHistory);

        //Store each string individually along with its current index
        for (int i = 0; i < historyList.size(); i++){
            editor.putString(HISTORY_INDEX + i, historyList.get(i));
        }
        editor.apply();
    }

    // Load the list of Flip history
    public void loadHistory(Context context){
        // Empty the list every time the method is called
        historyList = new ArrayList<String>();

        SharedPreferences sharedPreferences = context.getSharedPreferences(HISTORY_PREFS, Context.MODE_PRIVATE);

        numHistory = sharedPreferences.getInt(NUMBER_OF_HISTORY, 0);

        for (int i = 0; i < numHistory; i ++){
            historyList.add(sharedPreferences.getString(HISTORY_INDEX + i, null));
        }
    }


    // get the current history
    public String getHistory(int position) {
        return historyList.get(position);
    }

    // Get the number of history from Shared Preference
    public int getNumHistory(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(HISTORY_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(NUMBER_OF_HISTORY, 0);
    }



    @NonNull
    @Override
    public Iterator<FlipHistory> iterator() {
        return null;
    }
}
