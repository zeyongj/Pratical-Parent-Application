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

import static android.content.Context.MODE_PRIVATE;

// Class that contains the Children class
// Holds an ArrayList of Strings containing only the names of the children
// Is a singleton class to allow the current Children to be found in all activities
// Contents of Children can be changed at will
public class Children {

    public static final String PROFILE_INDEX_ = "profile_index2";
    public static final String CHILDREN_INDEX_PREFS = "Children Index Prefs";
    public static final String NUMBER_OF_CHILDREN_INDEX = "Number of Children Index";
    public static final String CHILDREN_INDEX = "Children Index";
    private String CHILDREN_PROF = "SharePreference for children profile";
    private String NUM_CHILDREN_PROFILE = "The number of children profile saved is: ";
    private String CHILDREN_PREFS = "Shared Preferences for Children Class";
    private String CHILD_INDEX = "Child Index_";
    private String NUM_CHILDREN = "The number of Children saved is: ";
    private int numChildren;
    private int numChildrenProfile;
    private int numChildrenIndex;

    // ArrayList to keep track of names of Children
    ArrayList<String> childrenNames = new ArrayList<>();
    ArrayList<String> profileIDs = new ArrayList<>();
    ArrayList<Integer> ChildrenIndexList = new ArrayList<>();
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
        addChildIndex();
        numChildren++;
    }

    // Get child at current childrenNames
    public String getChild(int position) {
        return childrenNames.get(position);
    }

    // Remove child from childrenNames at given position
    public void removeChild(int position) {
        childrenNames.remove(position);
        removeChildIndex(position);
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
        SharedPreferences sharedPreferences = context.getSharedPreferences(CHILDREN_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Save the current number of children for use in loadChildren
        editor.putInt(NUM_CHILDREN, numChildren);

        // Store each string individually along with its current index
        for (int i = 0; i < childrenNames.size(); i++) {
            editor.putString(CHILD_INDEX + i, childrenNames.get(i));
        }
        editor.apply();
        saveChildrenProfile(context);
        saveChildIndex(context);
    }

    // Load the list of children
    public void loadChildren(Context context) {
        // Empty the list every time the method is called
        childrenNames = new ArrayList<>();

        SharedPreferences sharedPreferences = context.getSharedPreferences(CHILDREN_PREFS, MODE_PRIVATE);
        // Get the number of children to be loaded to loop
        numChildren = sharedPreferences.getInt(NUM_CHILDREN, 0);

        // Add the names of the saved children one by one
        for (int i = 0; i < numChildren; i++) {
            childrenNames.add(sharedPreferences.getString(CHILD_INDEX + i, null));
        }
        loadChildrenProfile(context);
        loadChildrenIndex(context);
    }

    // Encode and Decode Image
    public  String encodeToBase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        //Log.d("Image Log: ", imageEncoded);
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
        SharedPreferences sharedPreferences = context.getSharedPreferences(CHILDREN_PROF, MODE_PRIVATE);
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

        SharedPreferences sharedPreferences = context.getSharedPreferences(CHILDREN_PROF, MODE_PRIVATE);

        numChildrenProfile = sharedPreferences.getInt(NUM_CHILDREN_PROFILE, 0);


        for (int i = 0 ; i < numChildrenProfile; i++) {
            profileIDs.add(sharedPreferences.getString(PROFILE_INDEX_ + i, null));
        }
    }

    // -------------------------------------------------------------------------------------------------------------------
    // Add an index to ChildrenIndex List
    public void addChildIndex(){
        ChildrenIndexList.add(ChildrenIndexList.size());
        numChildrenIndex++;
    }

    // Get child index at given position;
    public int getChildIndex(int position) {
        return ChildrenIndexList.get(position);
    }

    // Remove child index at given position
    public void removeChildIndex(int position) {
        int removedIndex = ChildrenIndexList.get(position);
        for(int i = 0; i < ChildrenIndexList.size(); i++){
            if(ChildrenIndexList.get(i) > removedIndex)
                ChildrenIndexList.set(i, ChildrenIndexList.get(i) - 1);
        }
        ChildrenIndexList.remove(position);
        numChildrenIndex--;
    }

    // Edit the index of the selected child
    public void editChildIndex(int position, int value) {
        ChildrenIndexList.set(position, value);
    }

    // Save the list of children index
    public void saveChildIndex(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CHILDREN_INDEX_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // save the current number of children index for use in loadChildrenIndex
        editor.putInt(NUMBER_OF_CHILDREN_INDEX, numChildrenIndex);

        for (int i = 0; i < ChildrenIndexList.size(); i++) {
            editor.putInt(CHILDREN_INDEX + i, ChildrenIndexList.get(i));
        }
        editor.apply();
    }

    // Load the list of children index
    public void loadChildrenIndex(Context context) {
        // Empty the list every time the method is called
        ChildrenIndexList = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(CHILDREN_INDEX_PREFS, MODE_PRIVATE);
        numChildrenIndex = sharedPreferences.getInt(NUMBER_OF_CHILDREN_INDEX, 0);

        for (int i = 0; i < numChildrenProfile; i++) {
            ChildrenIndexList.add(sharedPreferences.getInt(CHILDREN_INDEX + i, 0));
        }
    }

    public int getCurrentChildIndex(Context context) {
        for(int i = 0; i < ChildrenIndexList.size(); i++) {
            if(ChildrenIndexList.get(i) == 0)
                return i;
        }
        return 0;
    }

    public void setCurrentToNextChild(Context context) {
        for(int i = 0; i < ChildrenIndexList.size(); i++) {
            if(ChildrenIndexList.get(i) != 0)
                ChildrenIndexList.set(i, ChildrenIndexList.get(i) - 1);
            else
                ChildrenIndexList.set(i, ChildrenIndexList.size() - 1);
        }
    }

    public void setCurrentToClickedChild(Context context, int index) {
        for(int i = 0; i < ChildrenIndexList.size(); i++) {
            if(ChildrenIndexList.get(i) < index)
                ChildrenIndexList.set(i, ChildrenIndexList.get(i) + 1);
            else if(ChildrenIndexList.get(i) == index)
                ChildrenIndexList.set(i, 0);
        }
    }

    // Get the number of children from Shared Preference
    public int getNumChildren(Context context) {
        return ChildrenIndexList.size();
    }

}