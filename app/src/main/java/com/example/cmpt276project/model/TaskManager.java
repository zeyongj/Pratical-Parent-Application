package com.example.cmpt276project.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

// Class to manage a list of Task class objects
public class TaskManager {
    private ArrayList<Task> taskManager = new ArrayList<>();

    public void addTask(Task task) {
        taskManager.add(task);
    }

    public void removeTask(int position) {
        taskManager.remove(position);
    }

    public void editTask(int position, Task task) {
        taskManager.set(position, task);
    }

    public Task getTask(int position) {
        return taskManager.get(position);
    }
}
