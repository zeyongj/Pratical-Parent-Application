package com.example.cmpt276project.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

// Class to manage a list of Task class objects
public class TaskManager {
    private String SHARED_PREFS = "Task Manager Shared Preferences";
    private String TASK_MANAGER_SHARED_PREF = "Shared Preferences Code for Task Manager Json";
    private ArrayList<Task> taskManager = new ArrayList<>();

    private static TaskManager instance;
    public TaskManager() {
    }

    public static TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

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

    public int getSize() {
        return taskManager.size();
    }

    public void saveTaskManager(Context context) {
        SharedPreferences taskManagerPrefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor taskManagerPrefsEditor = taskManagerPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(taskManager);
        taskManagerPrefsEditor.putString(TASK_MANAGER_SHARED_PREF, json);
        taskManagerPrefsEditor.apply();
    }

    public void loadTaskManager(Context context) {
        SharedPreferences taskManagerPrefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = taskManagerPrefs.getString(TASK_MANAGER_SHARED_PREF, "");
        Type type = new TypeToken<List<Task>>(){}.getType();
        taskManager = gson.fromJson(json, type);
    }

    public boolean checkTaskManagerEmpty() {
        return taskManager == null;
    }

    public void reinitializeTaskManager() {
        taskManager = new ArrayList<>();
    }
}
