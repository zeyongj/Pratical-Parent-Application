package com.example.cmpt276project.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.cmpt276project.ui.FlipCoinActivity;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

// Class that contains the Children class
// Holds an ArrayList of Strings containing only the names of the children
// Is a singleton class to allow the current Children to be found in all activities
// Contents of Children can be changed at will
public class Children {

    public static final String PROFILE_INDEX_ = "profile_index2";
    private String CHILD_INDEX_PREF = "Shared preference for current child index";
    private String CURRENT_CHILD_INDEX = "current child index";
    private String CHILDREN_PROF = "SharePreference for children profile";
    private String NUM_CHILDREN_PROFILE = "The number of children profile saved is: ";
    private String CHILDREN_PREFS = "Shared Preferences for Children Class";
    private String CHILD_INDEX = "Child Index_";
    private String NUM_CHILDREN = "The number of Children saved is: ";
    private int numChildren;

    private int numChildrenProfile;

    // ArrayList to keep track of names of Children
    ArrayList<String> childrenNames = new ArrayList<>();

    ArrayList<String> profileIDs = new ArrayList<>();



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
        saveChildrenProfile(context);
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
        loadChildrenProfile(context);
    }


    // *********************************************************************************************

    /* handling profile images*/



    // Encode and Decode Image
    public  String encodeToBase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.d("Image Log: ", imageEncoded);
        return imageEncoded;
    }
    public  Bitmap decodeToBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }



    // Add childProfile to profileIDs
    public void addChildProfile(Bitmap profileImage) {
        profileIDs.add(encodeToBase64(profileImage));
        numChildrenProfile++;
    }

    // Get childProfile at current childrenNames;
    public String getChildProfile(int position) {
        return profileIDs.get(position);
    }

    // Remove childProfile from children profiles at a given position
    public void removeChildProfile(int position) {
        profileIDs.remove(position);
        numChildrenProfile --;
    }


    // Edit the profile of the selected child
    public void editChildProfile(int position, Bitmap image) {
        profileIDs.set(position, encodeToBase64(image));
    }

    // Save the list of children profiles
    public void saveChildrenProfile(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CHILDREN_PROF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // save the current number of children profile for use in loadChildrenProfile
        editor.putInt(NUM_CHILDREN_PROFILE, numChildrenProfile);


        for (int i = 0; i < profileIDs.size(); i++) {
            editor.putString("profile_index2" + i, profileIDs.get(i) );
        }
        editor.apply();
    }

    // Load the list of childrenProfile
    public void loadChildrenProfile(Context context) {

        // Empty the list every time the method is called
        profileIDs = new ArrayList<>();

        SharedPreferences sharedPreferences = context.getSharedPreferences(CHILDREN_PROF, Context.MODE_PRIVATE);

        numChildrenProfile = sharedPreferences.getInt(NUM_CHILDREN_PROFILE, 0);


        for (int i = 0 ; i < numChildrenProfile; i++) {
            profileIDs.add(sharedPreferences.getString(PROFILE_INDEX_ + i, null));
        }
    }


// *************************************************************************************************


    // Get the current child index from Shared Preference
    public int getCurrentChildIndex(Context context) {
        SharedPreferences sp = context.getSharedPreferences(CHILD_INDEX_PREF, Context.MODE_PRIVATE);
        return sp.getInt(CURRENT_CHILD_INDEX, 0);
    }

    // Set the current child index to next child index and save the index to Shared Preference
    public void setCurrentToNextChild(Context context) {
        SharedPreferences sp = context.getSharedPreferences(CHILD_INDEX_PREF, Context.MODE_PRIVATE);
        int currentChildIndex = sp.getInt(CURRENT_CHILD_INDEX, 0);
        currentChildIndex = (currentChildIndex + 1) % childrenNames.size();

        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(CURRENT_CHILD_INDEX, currentChildIndex);
        editor.apply();
    }

    // Set the current child index to next child index and save the index to Shared Preference
    public void setCurrentToClickedChild(Context context, int index) {
        SharedPreferences sp = context.getSharedPreferences(CHILD_INDEX_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(CURRENT_CHILD_INDEX, index);
        editor.apply();
    }

    // Get the number of children from Shared Preference
    public int getNumChildren(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CHILDREN_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(NUM_CHILDREN, 0);
    }

    //Set the current child index to first child index and save the index to Shared Preference
    public void setCurrentToFirstChild(Context context) {
        SharedPreferences sp = context.getSharedPreferences(CHILD_INDEX_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(CURRENT_CHILD_INDEX, 0);
        editor.apply();
    }
}
