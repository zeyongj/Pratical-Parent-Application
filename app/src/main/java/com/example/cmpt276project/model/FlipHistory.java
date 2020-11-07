package com.example.cmpt276project.model;

public class FlipHistory {

    private String currentDateAndTime;

    private String childName;
    private String flipResult;


    // Add singleton support to FlipHistory
    private static FlipHistory instance;
    private FlipHistory(){}
    public static FlipHistory getInstance(){
        if (instance == null){
            instance = new FlipHistory();
        }
        return instance;
    }


    //TODO: add data validation

    // getters and setters


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
}
