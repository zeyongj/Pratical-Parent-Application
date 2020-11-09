package com.example.cmpt276project.model;

// Class that contains information for the FlipCoinHistoryActivity
// Receives data from FlipCoinActivity when the user flips a coin
public class FlipHistory {

    private String currentDateAndTime;
    private String childName;
    private String flipResult;
    private int iconID;

    // Add singleton support to FlipHistory
    private static FlipHistory instance;
    private FlipHistory(){}
    public static FlipHistory getInstance(){
        if (instance == null){
            instance = new FlipHistory();
        }
        return instance;
    }

    public FlipHistory(String currentDateAndTime, String childName, String flipResult, int iconID){
        super();
        setCurrentDateAndTime(currentDateAndTime);
        setChildName(childName);
        setFlipResult(flipResult);
        setIconID(iconID);
    }

    public String getCurrentDateAndTime() {
        return currentDateAndTime;
    }

    public void setCurrentDateAndTime(String currentDateAndTime) {
        this.currentDateAndTime = currentDateAndTime;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getFlipResult() {
        return flipResult;
    }

    public void setFlipResult(String flipResult) {
        this.flipResult = flipResult;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }
}
