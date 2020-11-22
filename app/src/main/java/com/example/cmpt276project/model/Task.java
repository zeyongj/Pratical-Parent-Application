package com.example.cmpt276project.model;

import android.content.Context;

// Class to represent a task made by the user
// Contains the task name, task description, and the child whose responsible for doing the task
public class Task {
    public static String taskName;
    public static String taskDescription;
    public static Children children;
    public static int childIndex;

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

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
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

    public void updateTask(Children children) {
        this.children = children;
    }
}
