package com.example.cmpt276project.model;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class FlipCoinChildrenList {
    private String childName;
    private String profile;

    // Add singleton support to FlipHistory
    private static FlipCoinChildrenList instance;
    private FlipCoinChildrenList(){}
    public static FlipCoinChildrenList getInstance(){
        if (instance == null){
            instance = new FlipCoinChildrenList();
        }
        return instance;
    }

    public FlipCoinChildrenList(String childName, String profile){
        super();
        setChildName(childName);
        setProfile(profile);
    }


    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

}
