package com.example.cmpt276project.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Children {
    private String CHILDREN_PREFS = "Shared Preferences for Children Class";
    private String CHILD_INDEX = "Child Index_";
    private String NUM_CHILDREN = "The number of Children saved is: ";
    private int currentChildIndex;
    private int numChildren;

    // ArrayList to keep track of names of Children
    ArrayList<String> childrenNames = new ArrayList<>();

    // Constructor
    private static Children instance;
    public Children() {
    }

    public static Children getInstance() {
        if (instance == null) {
            instance = new Children();
        }
        return instance;
    }

    // Add child to childrenNames
    public void addChild(String childName) {
        childrenNames.add(childName);
        numChildren++;
    }

    // Get child at current childrenNames
    public String getChild(int position) {
        return childrenNames.get(position);
    }

    // Remove child from childrenNames at given position
    public void removeChild(int position) {
        childrenNames.remove(position);
        numChildren--;
    }

    // Get size of childrenNames
    public int getSize() {
        return childrenNames.size();
    }

    // Edit the name of the selected child
    public void editChild(int position, String name) {
        childrenNames.set(position, name);
    }

    // Save the list of children
    public void saveChildren(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CHILDREN_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Save the current number of children for use in loadChildren
        editor.putInt(NUM_CHILDREN, numChildren);

        // Store each string individually along with its current index
        for (int i = 0; i < childrenNames.size(); i++) {
            editor.putString(CHILD_INDEX + i, childrenNames.get(i));
        }
        editor.apply();
    }

    // Load the list of children
    public void loadChildren(Context context) {
        // Empty the list every time the method is called
        childrenNames = new ArrayList<>();

        SharedPreferences sharedPreferences = context.getSharedPreferences(CHILDREN_PREFS, Context.MODE_PRIVATE);
        // Get the number of children to be loaded to loop
        numChildren = sharedPreferences.getInt(NUM_CHILDREN, 0);

        // Add the names of the saved children one by one
        for (int i = 0; i < numChildren; i++) {
            childrenNames.add(sharedPreferences.getString(CHILD_INDEX + i, null));
        }
    }

    // Get the current child index
    public int getCurrentChildIndex(Context context) {
        SharedPreferences sp = context.getSharedPreferences("Shared preference for current child index", Context.MODE_PRIVATE);
        currentChildIndex = sp.getInt("current child index", 0);
        return currentChildIndex;

       // return currentChildIndex;
    }

    // Set the current child index to next child index and save the index to Shared Preference
    public void setCurrentToNextChild(Context context) {
        SharedPreferences sp = context.getSharedPreferences("Shared preference for current child index", Context.MODE_PRIVATE);
        currentChildIndex = sp.getInt("current child index", 0);
        currentChildIndex = (currentChildIndex + 1) % childrenNames.size();

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("current child index", currentChildIndex);
        editor.commit();
    }

    // Get the number of children
    public int getNumChildren(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CHILDREN_PREFS, Context.MODE_PRIVATE);
        numChildren = sharedPreferences.getInt(NUM_CHILDREN, 0);
        return numChildren;
    }

}
