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

import com.example.cmpt276project.R;
import com.example.cmpt276project.model.Children;
import com.example.cmpt276project.model.ChildrenAdapter;
import com.example.cmpt276project.model.TaskManager;
import com.example.cmpt276project.model.WhoseTurnAdapter;

import java.util.Objects;

public class WhoseTurnActivity extends AppCompatActivity {
    // Initiate adapter and variables
    WhoseTurnAdapter whoseTurnAdapter;
    private TaskManager taskManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whose_turn);
        Toolbar toolbar = findViewById(R.id.whoseturntoolbar);
        setSupportActionBar(toolbar);

        setBackButton();

        // Create children to test with
        taskManager = TaskManager.getInstance();

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

    // Save the children when the activity is closed
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    // Open add child dialog when the add child button is pressed
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
        RecyclerView.LayoutManager childLayoutManager = new LinearLayoutManager(this);
        whoseTurnRecyclerView.setLayoutManager(childLayoutManager);

        // Set Adapter for the RecyclerView
        whoseTurnAdapter = new WhoseTurnAdapter(taskManager);
        whoseTurnRecyclerView.setAdapter(whoseTurnAdapter);
    }

    // Method to build the Delete buttons on the RecyclerView items
    public void setDeleteButtons() {
        // Use custom OnClickListener to remove items in RecyclerView
        whoseTurnAdapter.setDeleteButtonClickListener(new WhoseTurnAdapter.OnDeleteButtonClickListener() {
            @Override
            public void editChild(int position) {

            }

            @Override
            public void deleteChild(int position) {
                // Remove the item from TaskManager class and notify RecyclerView that it was removed
                taskManager.removeTask(position);
                whoseTurnAdapter.notifyItemRemoved(position);
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


    // Remove the child at the current position
    public void removeTask(int position) {
    }

    // Edit the child at the current position
    public void editTask(Children children, int position) {
    }

    public void goToAddTasks() {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
    }
}