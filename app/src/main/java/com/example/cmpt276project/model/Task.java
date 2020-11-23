package com.example.cmpt276project.model;

import android.content.Context;
import android.widget.Toast;

// Class to represent a task made by the user
// Contains the task name, task description, and the child whose responsible for doing the task
public class Task {
    private String taskName;
    private String taskDescription;
    private Children children;
    private int childIndex;

    // Constructor for AddTaskActivity
    public Task(String taskName, String taskDescription, Children children) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.children = children;
        this.childIndex = 0;
    }

    // Constructor for editing a task
    public Task(String taskName, String taskDescription, Children children, int childIndex) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.children = children;
        this.childIndex = childIndex;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getChild() {
        if (children.getSize() == 0) {
            return "";
        }
        return children.getChild(childIndex);
    }

    public Children getChildren(){
        return children;
    }

    public void assignNewChild (int newIndex, Context context){
        if (newIndex < children.getNumChildren(context)){
        childIndex = newIndex;
        }
        else
            childIndex = 0;
    }



    public void updateTask(Children children) {
        this.children = children;
        if (childIndex > children.getSize()-1) {
            childIndex = 0;
        }
    }

    public int getChildIndex() {
        return childIndex;
    }
}
