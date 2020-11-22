package com.example.cmpt276project.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.cmpt276project.R;
import com.example.cmpt276project.model.Children;
import com.example.cmpt276project.model.ChildrenAdapter;
import com.example.cmpt276project.model.Task;
import com.example.cmpt276project.model.TaskManager;
import com.example.cmpt276project.model.WhoseTurnAdapter;

import java.util.Objects;

public class WhoseTurnActivity extends AppCompatActivity {
    // Initiate adapter and variables
    WhoseTurnAdapter whoseTurnAdapter;
    private TaskManager taskManager;
    private Children children;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whose_turn);
        Toolbar toolbar = findViewById(R.id.whoseturntoolbar);
        setSupportActionBar(toolbar);

        setBackButton();

        children = Children.getInstance();
        children.loadChildren(this);

        // Initialize TaskManager
        taskManager = TaskManager.getInstance();
        loadTaskManager(taskManager);


        // Build the RecyclerView
        buildWhoseTurnView(taskManager);

        // Build the delete buttons on the RecyclerView items
        setDeleteButtons();
    }


    // Create the Add Task option on the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_tasks_menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    // Go to add task activity when button is pressed
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_task_button) {
            goToAddTasks();
        }
        return super.onOptionsItemSelected(item);
    }

    // Method to build the RecyclerView
    public void buildWhoseTurnView(TaskManager taskManager) {
        // Find ID of RecyclerView
        RecyclerView whoseTurnRecyclerView = findViewById(R.id.whoseturnview);
        whoseTurnRecyclerView.setHasFixedSize(true);

        // Set Layout for the RecyclerView
        RecyclerView.LayoutManager taskLayoutManager = new LinearLayoutManager(this);
        whoseTurnRecyclerView.setLayoutManager(taskLayoutManager);

        // Set Adapter for the RecyclerView
        whoseTurnAdapter = new WhoseTurnAdapter(taskManager);
        whoseTurnRecyclerView.setAdapter(whoseTurnAdapter);
    }

    // Method to build the Delete buttons on the RecyclerView items
    public void setDeleteButtons() {
        // Use custom OnClickListener to remove items in RecyclerView
        whoseTurnAdapter.setDeleteButtonClickListener(new WhoseTurnAdapter.OnDeleteButtonClickListener() {
            @Override
            public void editTask(int position) {
                // Put what to do when EDIT button is clicked here
                editTasks(position);
            }

            @Override
            public void deleteTask(int position) {
                // Remove the item from TaskManager class and notify RecyclerView that it was removed
                removeTask(position);
            }
        });
    }

    private void setBackButton() {
        // Enable "up" on toolbar
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }


    // Remove the Task at the current position
    public void removeTask(int position) {
        taskManager.removeTask(position);
        taskManager.saveTaskManager(this);
        whoseTurnAdapter.notifyItemRemoved(position);
    }

    // Edit the Task at the current position
    public void editTasks(int position) {
        Intent intent = new Intent(this, EditTaskActivity.class);
        startActivity(intent);
    }

    public void goToAddTasks() {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
    }

    public void loadTaskManager(TaskManager taskManager) {
        taskManager.loadTaskManager(this);
        if (taskManager.checkTaskManagerEmpty()) {
            taskManager.reinitializeTaskManager();
        }
        taskManager.updateTasks(children);
    }
}